name := """taskService"""
organization := "com.mackhartley"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.6"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-slick" % "5.0.0",
  "com.typesafe.slick" %% "slick-codegen" % "3.3.3",
  "org.postgresql" % "postgresql" % "42.2.20",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.3.2",
  "org.mindrot" % "jbcrypt" % "0.4"
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.mackhartley.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.mackhartley.binders._"
