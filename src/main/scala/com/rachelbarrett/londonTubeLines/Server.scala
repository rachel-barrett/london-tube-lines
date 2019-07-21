package com.rachelbarrett.londonTubeLines

import cats.effect.{ConcurrentEffect, IO, Timer}
import org.http4s.HttpApp
import org.http4s.server.blaze.BlazeServerBuilder

class Server(
  port: Int,
  httpApp: HttpApp[IO]
)(implicit T: Timer[IO], C: ConcurrentEffect[IO]) {

  def start(): IO[Unit] = BlazeServerBuilder[IO]
    .bindHttp(port = port, host = "0.0.0.0")
    .withHttpApp(httpApp)
    .serve
    .compile
    .drain

}

object Server {

  def apply()(implicit T: Timer[IO], C: ConcurrentEffect[IO]): Server =
    new Server(
      port = 8080,
      httpApp = HttpApp()
    )(T, C)

}
