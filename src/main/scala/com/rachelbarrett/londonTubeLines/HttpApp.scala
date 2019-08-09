package com.rachelbarrett.londonTubeLines

import cats.effect.IO
import cats.implicits._
import org.http4s.implicits._
import org.http4s.{HttpRoutes, HttpApp => http4sHttpApp}

object HttpApp {

  def apply(routes: List[HttpRoutes[IO]]): http4sHttpApp[IO] = {
    routes
      .fold(HttpRoutes.of[IO] {PartialFunction.empty})(_ <+> _)
      .orNotFound
  }

}
