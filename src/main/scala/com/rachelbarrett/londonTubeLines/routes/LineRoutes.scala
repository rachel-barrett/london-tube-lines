package com.rachelbarrett.londonTubeLines.routes

import cats.Applicative
import cats.effect.IO
import com.rachelbarrett.londonTubeLines.routes.LineRoutes.StationQueryParamMatcher
import com.rachelbarrett.londonTubeLines.services.LineService
import org.http4s.circe.jsonEncoderOf
import org.http4s.dsl.io._
import org.http4s.{EntityEncoder, HttpRoutes}

class LineRoutes(lineService: LineService) {

  import LineRoutes.Encoders._

  def lineRoutes: HttpRoutes[IO] = HttpRoutes.of[IO] {
    case GET -> Root / "lines" :? StationQueryParamMatcher(station) => Ok(lineService.getLinesPassingThroughStation(station))
    case GET -> Root / "lines" => Ok(lineService.getAllLines())
  }

}

object LineRoutes {

  object StationQueryParamMatcher extends QueryParamDecoderMatcher[String]("passingThroughStation")

  object Encoders {

    implicit def listEntityEncoder[F[_]: Applicative]: EntityEncoder[F,List[String]] = jsonEncoderOf

  }

}
