import sbt._

object Dependencies {
  
  val http4sVersion = "0.20.6"
  val circeVersion = "0.11.1"
  val scalaTestVersion = "3.0.8"
  
  
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
  
  lazy val all = http4s ++ circe ++ scalaTest
  
}
