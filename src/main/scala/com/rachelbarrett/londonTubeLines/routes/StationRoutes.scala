package com.rachelbarrett.londonTubeLines.routes

import cats.Applicative
import cats.effect.IO
import com.rachelbarrett.londonTubeLines.services.StationService
import org.http4s.{EntityEncoder, HttpRoutes}
import org.http4s.dsl.io._
import org.http4s.circe.jsonEncoderOf

object StationRoutes {

  import Encoders._

  def apply(stationService: StationService): HttpRoutes[IO] =
    HttpRoutes.of[IO] {
      case GET -> Root / "stations" :? LineQueryParamMatcher(line) => Ok(stationService.getStationsOnLine(line))
      case GET -> Root / "stations" => Ok(stationService.getAllStations())
    }

  def apply(): HttpRoutes[IO] = apply(StationService())

  object LineQueryParamMatcher extends QueryParamDecoderMatcher[String]("onLine")

  object Encoders {

    implicit def listEntityEncoder[F[_]: Applicative]: EntityEncoder[F,List[String]] = jsonEncoderOf

  }

}
