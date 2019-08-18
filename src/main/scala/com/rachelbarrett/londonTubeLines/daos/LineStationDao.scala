package com.rachelbarrett.londonTubeLines.daos

import cats.effect.IO
import com.rachelbarrett.londonTubeLines.daos.LineStationDao.LineStation
import doobie.implicits._
import doobie.Transactor

class LineStationDao(transactor: Transactor[IO]) {

  //TODO: Should two services use the same dao?
  
  def find(): IO[List[LineStation]] =
    sql"select line, station from lineStation"
      .query[LineStation]
      .to[List]
      .transact(transactor)

}

object LineStationDao {

  case class LineStation(line: String, station: String)

}
