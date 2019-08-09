package com.rachelbarrett.londonTubeLines.daos

import cats.effect.IO
import doobie.implicits._
import doobie.Transactor

class StationLineDao(transactor: Transactor[IO]) {

  //TODO: Should two services use the same dao?

  type Line = String
  type Station = String

  def find(): IO[List[(Line, Station)]] =
    sql"select line, station from stationLine".query[(String, String)].to[List].transact(transactor)

}
