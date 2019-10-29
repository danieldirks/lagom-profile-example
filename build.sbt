name := "profile"
version := "0.0.1"

scalaVersion := "2.12.10"

lazy val common = (project in file("common"))
  .settings(libraryDependencies += "com.typesafe.play" %% "play-json" % "2.7.4")

lazy val profileApi = (project in file("profile-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi,
      "com.google.code.gson" % "gson" % "2.8.6"
    )
  )
  .dependsOn(common)

lazy val root = (project in file("."))
    .aggregate(profileApi)
