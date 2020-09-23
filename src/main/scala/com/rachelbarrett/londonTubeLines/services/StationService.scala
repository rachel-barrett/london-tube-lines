package com.rachelbarrett.londonTubeLines.services

import cats.effect.IO
import com.rachelbarrett.londonTubeLines.daos.LineStationDao

class StationService(stationLineDao: LineStationDao) {

  type Station = String

  def getAllStations(): IO[List[Station]] =
    stationLineDao
      .find()
      .map(list =>
        list
          .map(_.station)
          .distinct
        )

  def getStationsOnLine(line: String): IO[List[Station]] =
    stationLineDao
      .find()
      .map(list =>
        list
          .filter(_.line == line)
          .map(_.station)
        )

}

