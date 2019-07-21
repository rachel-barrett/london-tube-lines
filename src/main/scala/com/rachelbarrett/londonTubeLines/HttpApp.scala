package com.rachelbarrett.londonTubeLines

import cats.effect.IO
import org.http4s.{Response, HttpApp => http4sHttpApp}

object HttpApp {

  def apply(): http4sHttpApp[IO] = http4sHttpApp.pure(Response())

}
