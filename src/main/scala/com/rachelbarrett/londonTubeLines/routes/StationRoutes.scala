package com.rachelbarrett.londonTubeLines.routes

import cats.Applicative
import cats.effect.IO
import com.rachelbarrett.londonTubeLines.routes.StationRoutes.LineQueryParamMatcher
import com.rachelbarrett.londonTubeLines.services.StationService
import org.http4s.circe.jsonEncoderOf
import org.http4s.dsl.io._
import org.http4s.{EntityEncoder, HttpRoutes}


class StationRoutes(stationService: StationService) {

  import StationRoutes.Encoders._

  def stationRoutes: HttpRoutes[IO] =
    HttpRoutes.of[IO] {
      case GET -> Root / "stations" :? LineQueryParamMatcher(line) => Ok(stationService.getStationsOnLine(line))
      case GET -> Root / "stations" => Ok(stationService.getAllStations())
    }
}

object StationRoutes {

  object LineQueryParamMatcher extends QueryParamDecoderMatcher[String]("onLine")

  object Encoders {

    implicit def listEntityEncoder[F[_]: Applicative]: EntityEncoder[F,List[String]] = jsonEncoderOf

  }

}
