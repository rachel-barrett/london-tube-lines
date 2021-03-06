
lazy val root = (project in file("."))
  .settings(
    organization := "com.rachel-barrett",
    name := "london-tube-lines",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := "2.12.11",
    libraryDependencies ++= Dependencies.all,
    scalacOptions ++= CompilerSettings.scalacOptions,
    Global / cancelable := false
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
