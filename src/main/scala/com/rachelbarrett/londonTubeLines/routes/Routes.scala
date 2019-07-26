package com.rachelbarrett.londonTubeLines.routes

import cats.effect.IO
import org.http4s.HttpRoutes
import LineRoutes.lineRoutes
import StationRoutes.stationRoutes

object Routes {

  def apply(): List[HttpRoutes[IO]] = List(
    lineRoutes,
    stationRoutes
  )

}
