package com.rachelbarrett.londonTubeLines.routes

import cats.effect.IO
import org.http4s.server.Router
import org.http4s
import org.http4s.implicits._

object HttpApp {

  def apply(
    lineRoutes: LineRoutes,
    stationRoutes: StationRoutes
  ): http4s.HttpApp[IO] =
    Router.apply(
      "/lines" -> lineRoutes.lineRoutes,
      "/stations" -> stationRoutes.stationRoutes
    ).orNotFound

}
