package com.rachelbarrett.londonTubeLines

import cats.effect.{ExitCode, IO, IOApp}

object Main extends IOApp {

  def run(args: List[String]): IO[ExitCode] =
    AppComponent
      .resource()
      .use(server => {
        IO.delay(println(s"Server running at ${server.baseUri}"))
        .flatMap(_ => IO.never)
      })
      .map(_ => ExitCode.Success)

}
