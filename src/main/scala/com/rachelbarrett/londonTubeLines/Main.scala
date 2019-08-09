package com.rachelbarrett.londonTubeLines

import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._
import com.rachelbarrett.londonTubeLines.daos.StationLineDao
import com.rachelbarrett.londonTubeLines.routes.{LineRoutes, StationRoutes}
import com.rachelbarrett.londonTubeLines.services.{LineService, StationService}
import doobie.Transactor

object Main extends IOApp {

  def run(args: List[String]): IO[ExitCode] = {
    val transactor: Transactor[IO] = Transactor.fromDriverManager.apply[IO](
      "org.sqlite.JDBC", "jdbc:sqlite:data/london-tube-lines.db", "", ""
    ) //TODO: Is there any reason this can't be in the Dao object?
    val stationLineDao = new StationLineDao(transactor)
    val lineService = new LineService(stationLineDao)
    val stationService = new StationService(stationLineDao)
    val lineRoutes = new LineRoutes(lineService)
    val stationRoutes = new StationRoutes(stationService)
    val routes = List(lineRoutes.lineRoutes, stationRoutes.stationRoutes)
    val httpApp = HttpApp.apply(routes)
    val server = new Server(8080, httpApp)
    server.start
      .as(ExitCode.Success)
  }

}
