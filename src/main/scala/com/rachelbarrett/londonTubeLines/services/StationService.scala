package com.rachelbarrett.londonTubeLines.services

class StationService {

  def getAllStations(): List[String] =
    List(
      "Station1",
      "Station2",
      "Station3"
    )

  def stationsOnLine(line: String): List[String] = getAllStations()

}

