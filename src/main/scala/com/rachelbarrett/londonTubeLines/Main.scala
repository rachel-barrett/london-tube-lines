package com.rachelbarrett.londonTubeLines

import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._

object Main extends IOApp {

  def run(args: List[String]): IO[ExitCode] = {
    Server().start
      .as(ExitCode.Success)
  }

}
