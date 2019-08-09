package com.rachelbarrett.londonTubeLines.services

import cats.effect.IO
import com.rachelbarrett.londonTubeLines.daos.StationLineDao

class StationService(stationLineDao: StationLineDao) {

  def getAllStations(): IO[List[String]] =
    stationLineDao.find().map(list => list.map(_._2).distinct)

  def getStationsOnLine(line: String): IO[List[String]] =
    stationLineDao.find().map(list => list.filter(_._1 == line)).map(list => list.map(_._2))

}

