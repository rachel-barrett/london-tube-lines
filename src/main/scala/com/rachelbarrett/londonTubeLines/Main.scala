package com.rachelbarrett.londonTubeLines

import cats.effect.{ExitCode, IO, IOApp}

object Main extends IOApp {

  def run(args: List[String]): IO[ExitCode] =
    AppComponent.run().map(_ => ExitCode.Success)

}
