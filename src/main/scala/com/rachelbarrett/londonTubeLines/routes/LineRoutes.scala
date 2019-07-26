package com.rachelbarrett.londonTubeLines.routes

import cats.effect.IO
import org.http4s.dsl.io._
import org.http4s.{HttpRoutes}

object LineRoutes {

  def lineRoutes: HttpRoutes[IO] =
    HttpRoutes.of[IO] {
      case GET -> Root / "lines" => Ok("lines")
    }

}
