package com.rachelbarrett.londonTubeLines.services

class LineService {

  def getAllLines(): List[String] =
    List(
      "Line1",
      "Line2",
      "Line3"
    )

  def linesPassingThroughStation(line: String): List[String] = getAllLines()

}

