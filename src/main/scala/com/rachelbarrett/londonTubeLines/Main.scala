package com.rachelbarrett.londonTubeLines

import cats.effect.{ExitCode, IO, IOApp}

object Main extends IOApp {

  def run(args: List[String]): IO[ExitCode] = {

    AppComponent.Server.resource
      .use { server => 
        server.runIndefinitely()
      }

  }.map(_ => ExitCode.Success)

}
