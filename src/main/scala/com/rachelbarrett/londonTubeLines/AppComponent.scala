package com.rachelbarrett.londonTubeLines

import cats.effect.{ConcurrentEffect, ContextShift, IO, Resource, Timer}
import com.rachelbarrett.londonTubeLines.daos.LineStationDao
import com.rachelbarrett.londonTubeLines.routes.{HttpApp, LineRoutes, StationRoutes}
import com.rachelbarrett.londonTubeLines.services.{LineService, StationService}
import doobie.hikari.HikariTransactor
import doobie.util.ExecutionContexts
import doobie.util.transactor.Transactor
import org.http4s
import org.http4s.server.Server
import org.http4s.server.blaze.BlazeServerBuilder

object AppComponent {

  def resource()(implicit cs: ContextShift[IO], T: Timer[IO], C: ConcurrentEffect[IO]): Resource[IO, Server[IO]] = for {
    config <- Resource.liftF(Config.load)
    environmentHandle <- EnvironmentHandle.resource(config)
    server <- Server.resource(environmentHandle)
  } yield server


  object Config {

    def load: IO[Config] = IO.pure(
      Config("jdbc:sqlite:data/london-tube-lines.db")
    )

    case class Config(
      databaseUrl: String
    )

  }

  object EnvironmentHandle {

    import Config.Config

    def resource(config: Config)(implicit cs: ContextShift[IO]): Resource[IO, EnvironmentHandle] =
      hikariTransactor(config.databaseUrl).map(EnvironmentHandle(_))

    private def hikariTransactor(databaseUrl: String)(implicit cs: ContextShift[IO]): Resource[IO, HikariTransactor[IO]] = for {
      ce <- ExecutionContexts.fixedThreadPool[IO](32) // our connect EC - await connection here
      te <- ExecutionContexts.cachedThreadPool[IO] // our transaction EC - execute JDBC operations here
      xa <- HikariTransactor.newHikariTransactor[IO](
        driverClassName = "org.sqlite.JDBC",
        url = databaseUrl,
        user = "",
        pass = "",
        ce,
        te
      )
    } yield xa

    case class EnvironmentHandle(
      transactor: Transactor[IO]
    )

  }

  object Server {

    import EnvironmentHandle.EnvironmentHandle

    def resource(environmentHandle: EnvironmentHandle)(implicit T: Timer[IO], C: ConcurrentEffect[IO]): Resource[IO, Server[IO]] = {
      val stationLineDao = new LineStationDao(environmentHandle.transactor)
      val lineService = new LineService(stationLineDao)
      val stationService = new StationService(stationLineDao)
      val lineRoutes = new LineRoutes(lineService)
      val stationRoutes = new StationRoutes(stationService)
      val httpApp = HttpApp.apply(lineRoutes, stationRoutes)
      resource(8080, httpApp)
    }

    private def resource(port:Int, httpApp: http4s.HttpApp[IO])(implicit T: Timer[IO], C: ConcurrentEffect[IO]): Resource[IO, Server[IO]] =
      BlazeServerBuilder[IO]
        .bindHttp(port = port, host = "localhost")
        .withHttpApp(httpApp)
        .resource


  }

}
