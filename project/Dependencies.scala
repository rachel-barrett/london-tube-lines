import sbt._

object Dependencies {
  
  val http4sVersion = "0.21.5"
  val circeVersion = "0.11.1"
  val scalaTestVersion = "3.0.8"
  val sqlLiteVersion = "3.32.3"
  val doobieVersion = "0.9.0"
  
  
  lazy val http4s = Seq(
    "org.http4s"      %% "http4s-blaze-server" % http4sVersion,
//    "org.http4s"      %% "http4s-blaze-client" % http4sVersion,
    "org.http4s"      %% "http4s-circe"        % http4sVersion,
    "org.http4s"      %% "http4s-dsl"          % http4sVersion,
  )
  
  lazy val circe = Seq(
    "io.circe"        %% "circe-generic"       % circeVersion,
  )
  
  
  lazy val scalaTest = Seq(
    "org.scalatest" %% "scalatest" % scalaTestVersion % Test
  )

  lazy val sqlLite = Seq(
    "org.xerial" % "sqlite-jdbc" % sqlLiteVersion
  )

  lazy val doobie = Seq(
    "org.tpolecat" %% "doobie-core" % doobieVersion,
    "org.tpolecat" %% "doobie-hikari" % doobieVersion
  )
  
  lazy val all = http4s ++ circe ++ scalaTest ++ sqlLite ++ doobie
  
}
