name := "simpleDES"
version := "1.0"
scalaVersion := "2.12.4"
scalacOptions ++= Seq("-unchecked", "-feature")
libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.13.4" % "test"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.4" % "test"