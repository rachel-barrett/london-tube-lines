package com.rachelbarrett.londonTubeLines

import cats.effect.{ConcurrentEffect, ContextShift, IO, Resource, Timer}
import com.rachelbarrett.londonTubeLines.daos.LineStationDao
import com.rachelbarrett.londonTubeLines.routes.{HttpApp, LineRoutes, StationRoutes}
import com.rachelbarrett.londonTubeLines.services.{LineService, StationService}
import doobie.hikari.HikariTransactor
import doobie.util.ExecutionContexts
import doobie.util.transactor.Transactor
import org.http4s
import org.http4s.server.{Server => http4sServer}
import org.http4s.server.blaze.BlazeServerBuilder
import cats.effect.Blocker
import scala.concurrent.ExecutionContext

object AppComponent {

  object Server {

    def resource()(implicit cs: ContextShift[IO], T: Timer[IO], C: ConcurrentEffect[IO]): Resource[IO, Server] =
      for {
        httpApp <- HttpApp.resource
        port <- Resource.liftF(Config.load.map(_.port))
        server <- resource(port, httpApp)
      } yield server

    private def resource(port:Int, httpApp: http4s.HttpApp[IO])(implicit T: Timer[IO], C: ConcurrentEffect[IO]): Resource[IO, Server] =
      for {
        serverEc <- ExecutionContexts.cachedThreadPool[IO]
        server <- BlazeServerBuilder[IO](serverEc)
          .bindHttp(port = port, host = "localhost")
          .withHttpApp(httpApp)
          .resource
          .map(new Server(_))
      } yield server

    class Server(server: http4sServer[IO]) {

      def runIndefinitely(): IO[Unit] = for {
        _ <- IO.delay(println(s"Server running at ${server.baseUri}"))
        _ <- IO.never
      } yield ()

    }

  }

  object HttpApp {

    def resource()(implicit cs: ContextShift[IO]): Resource[IO, http4s.HttpApp[IO]] =
      for {
        environmentHandle <- EnvironmentHandle.resource
        httpApp = apply(environmentHandle)
      } yield httpApp

    private def apply(environmentHandle: EnvironmentHandle.EnvironmentHandle): http4s.HttpApp[IO] =
      {
        val stationLineDao = new LineStationDao(environmentHandle.transactor)
        val lineService = new LineService(stationLineDao)
        val stationService = new StationService(stationLineDao)
        val lineRoutes = new LineRoutes(lineService)
        val stationRoutes = new StationRoutes(stationService)
        val httpApp = routes.HttpApp.apply(lineRoutes, stationRoutes)
        httpApp
      }

  }

  object EnvironmentHandle {

    def resource()(implicit cs: ContextShift[IO]): Resource[IO, EnvironmentHandle] =
     for {
       config <- Resource.liftF(Config.load)
       environmentHandle <- resource(config)
     } yield environmentHandle

    private def resource(config: Config.Config)(implicit cs: ContextShift[IO]): Resource[IO, EnvironmentHandle] =
      for {
        hikariTransactor <- hikariTransactor(config.databaseUrl)
        environmentHandle = EnvironmentHandle(
          transactor = hikariTransactor
        )
      } yield environmentHandle

    private def hikariTransactor(databaseUrl: String)(implicit cs: ContextShift[IO]): Resource[IO, HikariTransactor[IO]] = for {
      ce <- ExecutionContexts.fixedThreadPool[IO](32) // our connect EC - await connection here
      be <- Blocker[IO] // our blocking EC - execute JDBC operations here
      xa <- HikariTransactor.newHikariTransactor[IO](
        driverClassName = "org.sqlite.JDBC",
        url = databaseUrl,
        user = "",
        pass = "",
        connectEC = ce,
        blocker = be
      )
    } yield xa

    case class EnvironmentHandle(
      transactor: Transactor[IO]
    )

  }

  object Config {

    def load(): IO[Config] = IO.pure(default)

    val default =
      Config(
        port = 8080,
        databaseUrl = "jdbc:sqlite:data/london-tube-lines.db"
      )

    case class Config(
      port: Int,
      databaseUrl: String
    )

  }

}
