package com.rachelbarrett.londonTubeLines.services

import com.rachelbarrett.londonTubeLines.daos.StationLineDao

class LineService(stationLineDao: StationLineDao) {

  def getAllLines(): List[String] =
    stationLineDao.find().map(_._1).distinct

  def getLinesPassingThroughStation(station: String): List[String] =
    stationLineDao.find().filter(_._2 == station).map(_._1)

}

object LineService {

  def apply(): LineService = new LineService(new StationLineDao)

}

