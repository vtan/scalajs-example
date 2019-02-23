ThisBuild / version := "0.1-SNAPSHOT"
ThisBuild / scalaVersion := "2.12.8"

val akkaHttpVersion = "10.1.7"
val akkaHttpCirceVersion = "1.24.3"
val akkaStreamVersion = "2.5.21"
val circeVersion = "0.10.0"
val scalaJsDomVersion = "0.9.2"

lazy val root =
  (project in file("."))
    .aggregate(server, client, common, commonJs)

lazy val server =
  (project in file("server"))
    .dependsOn(common)
    .settings(
      libraryDependencies ++= Seq(
        "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
        "com.typesafe.akka" %% "akka-stream" % akkaStreamVersion,
        "de.heikoseeberger" %% "akka-http-circe" % akkaHttpCirceVersion,
        "io.circe" %%% "circe-parser" % circeVersion
      )
    )

lazy val client =
  (project in file("client"))
    .enablePlugins(ScalaJSPlugin)
    .dependsOn(commonJs)
    .settings(
      scalaJSUseMainModuleInitializer := true,
      libraryDependencies ++= Seq(
        "io.circe" %%% "circe-parser" % circeVersion,
        "org.scala-js" %%% "scalajs-dom" % scalaJsDomVersion
      )
    )

lazy val common =
  (project in file("common"))
    .settings(
      libraryDependencies ++= Seq(
        "io.circe" %%% "circe-core" % circeVersion,
        "io.circe" %%% "circe-generic" % circeVersion
      )
    )

lazy val commonJs =
  common.enablePlugins(ScalaJSPlugin)
