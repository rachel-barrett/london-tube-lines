package com.rachelbarrett.londonTubeLines.routes

import cats.effect.IO
import org.http4s.HttpRoutes

object Routes {

  def apply(): List[HttpRoutes[IO]] = List(
    LineRoutes(),
    StationRoutes()
  )

}
