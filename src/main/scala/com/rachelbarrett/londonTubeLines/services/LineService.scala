package com.rachelbarrett.londonTubeLines.services

import cats.effect.IO
import com.rachelbarrett.londonTubeLines.daos.LineStationDao

class LineService(stationLineDao: LineStationDao) {

  type Line = String

  def getAllLines(): IO[List[Line]] =
    stationLineDao.find().map(list => list.map(_.line).distinct)

  def getLinesPassingThroughStation(station: String): IO[List[Line]] =
    stationLineDao.find().map(list => list.filter(_.station == station)).map(list => list.map(_.line))

}

