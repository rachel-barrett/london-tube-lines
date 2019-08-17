package com.rachelbarrett.londonTubeLines

import cats.effect.{ConcurrentEffect, ContextShift, IO, Resource, Timer}
import com.rachelbarrett.londonTubeLines.daos.StationLineDao
import com.rachelbarrett.londonTubeLines.routes.{LineRoutes, StationRoutes}
import com.rachelbarrett.londonTubeLines.services.{LineService, StationService}
import doobie.util.transactor.Transactor

object AppComponent {

  def run()(implicit cs: ContextShift[IO], T: Timer[IO], C: ConcurrentEffect[IO]): IO[Unit] = for {
    config <- Config.load
    environmentHandle <- EnvironmentHandle.apply(config)
    _ <- environmentHandle.use { environmentHandle =>
      val server = Server.apply(environmentHandle)
      server.start
    }
  } yield ()

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

    def apply(config: Config)(implicit cs: ContextShift[IO]): IO[Resource[IO, EnvironmentHandle]] = {
      val transactor: Transactor[IO] = Transactor.fromDriverManager.apply[IO](
        "org.sqlite.JDBC", config.databaseUrl, "", ""
      )
      IO.pure(Resource.pure(EnvironmentHandle(transactor)))
    }

    case class EnvironmentHandle(
      transactor: Transactor[IO]
    )

  }


  object Server {

    import EnvironmentHandle.EnvironmentHandle

    def apply(environmentHandle: EnvironmentHandle)(implicit T: Timer[IO], C: ConcurrentEffect[IO]): Server = {
      val stationLineDao = new StationLineDao(environmentHandle.transactor)
      val lineService = new LineService(stationLineDao)
      val stationService = new StationService(stationLineDao)
      val lineRoutes = new LineRoutes(lineService)
      val stationRoutes = new StationRoutes(stationService)
      val routes = List(lineRoutes.lineRoutes, stationRoutes.stationRoutes)
      val httpApp = HttpApp.apply(routes)
      new Server(8080, httpApp)
    }


  }





}
