package com.rachelbarrett.londonTubeLines

import cats.effect.{ExitCode, IO, IOApp}

object Main extends IOApp {

  def run(args: List[String]): IO[ExitCode] = for {
    config <- AppComponent.Config.load
    environmentHandle <- AppComponent.EnvironmentHandle.apply(config)
    server = AppComponent.Server.apply(environmentHandle)
    _ <- server.start
  } yield ExitCode.Success

}
