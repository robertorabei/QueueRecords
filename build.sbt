val scala3Version = "3.10.0"
val scalaTestVersion = "3.2.20"
val csvHandlerVersion = "1.3.10"

lazy val root = project
    .in(file("."))
    .settings(
        name := "Scala 3 Project Template",
        version := "0.1.0-SNAPSHOT",

        scalaVersion := scala3Version,

        libraryDependencies ++= Seq(
            // CSV Handler
            "com.github.tototoshi" %% "scala-csv" % csvHandlerVersion,

            // Testing
            "org.scalatest" %% "scalatest" % scalaTestVersion,
            "org.scalatest" %% "scalatest" % scalaTestVersion % "test"
        )
    )