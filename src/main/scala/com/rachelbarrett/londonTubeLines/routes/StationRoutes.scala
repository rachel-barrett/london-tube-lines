package com.rachelbarrett.londonTubeLines.routes

import cats.effect.IO
import org.http4s.HttpRoutes
import org.http4s.dsl.io._

object StationRoutes {

  def stationRoutes: HttpRoutes[IO] =
    HttpRoutes.of[IO] {
      case GET -> Root / "stations" => Ok("stations")
    }

}
