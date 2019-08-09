package com.rachelbarrett.londonTubeLines.services

import com.rachelbarrett.londonTubeLines.daos.StationLineDao

class StationService(stationLineDao: StationLineDao) {

  def getAllStations(): List[String] =
    stationLineDao.find().map(_._2).distinct

  def getStationsOnLine(line: String): List[String] =
    stationLineDao.find().filter(_._1 == line).map(_._2)

}

object StationService {

  def apply(): StationService = new StationService(new StationLineDao)

}

