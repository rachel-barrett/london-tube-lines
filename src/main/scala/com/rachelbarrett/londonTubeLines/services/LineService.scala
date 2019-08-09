package com.rachelbarrett.londonTubeLines.services

import cats.effect.IO
import com.rachelbarrett.londonTubeLines.daos.StationLineDao

class LineService(stationLineDao: StationLineDao) {

  def getAllLines(): IO[List[String]] =
    stationLineDao.find().map(list => list.map(_._1).distinct)

  def getLinesPassingThroughStation(station: String): IO[List[String]] =
    stationLineDao.find().map(list => list.filter(_._2 == station)).map(list => list.map(_._1))

}

