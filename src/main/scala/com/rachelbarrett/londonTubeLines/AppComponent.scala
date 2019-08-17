package com.rachelbarrett.londonTubeLines

import cats.effect.{ConcurrentEffect, ContextShift, IO, Timer}
import com.rachelbarrett.londonTubeLines.daos.StationLineDao
import com.rachelbarrett.londonTubeLines.routes.{LineRoutes, StationRoutes}
import com.rachelbarrett.londonTubeLines.services.{LineService, StationService}
import doobie.util.transactor.Transactor

object AppComponent {

  object Config {

    def load: IO[Config] = IO.pure(
      Config("jdbc:sqlite:data/london-tube-lines.db")
    )

    case class Config(
      databaseUrl: String
    )

  }

  object EnvironmentHandle {

    import Config._

    def apply(config: Config)(implicit cs: ContextShift[IO]): IO[EnvironmentHandle] = {
      val transactor: Transactor[IO] = Transactor.fromDriverManager.apply[IO](
        "org.sqlite.JDBC", config.databaseUrl, "", ""
      )
      IO.pure(EnvironmentHandle(transactor))
    }

    case class EnvironmentHandle(
      transactor: Transactor[IO]
    )

  }


  object Server {

    import EnvironmentHandle._

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
