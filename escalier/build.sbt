name := "escalier"

version := "0.5.0"

organization := "net.icorsaro"

homepage :=  Some(new java.net.URL("http://github.com/kydos/escalier"))

scalaVersion := "2.9.2"

resolvers += "Local Ivy2 Repo" at "file://"+Path.userHome.absolutePath+"/.ivy2/local"

libraryDependencies += "org.scala-lang" % "scala-swing" % "2.9.2"

autoCompilerPlugins := true

addCompilerPlugin("org.scala-lang.plugins" % "continuations" % "2.9.2")

scalacOptions += "-deprecation"

scalacOptions += "-optimise"

scalacOptions += "-unchecked"
