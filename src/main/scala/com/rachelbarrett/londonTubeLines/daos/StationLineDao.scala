package com.rachelbarrett.londonTubeLines.daos

class StationLineDao {

  //TODO: Should two services use the same dao?

  type Line = String
  type Station = String

  def find(): List[(Line, Station)] = List(
    ("Line1", "Station1"),
    ("Line1", "Station2"),
    ("Line1", "Station3"),
    ("Line2", "Station3"),
    ("Line2", "Station4"),
    ("Line3", "Station4"),
    ("Line3", "Station5")
  )


}
