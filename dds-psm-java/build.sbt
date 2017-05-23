name := "dds-psm-java"

version := "1.0"

scalaVersion := "2.10.0-RC3"

organization := "org.omg"

libraryDependencies += "junit" % "junit" % "4.8" % "test"

homepage :=  Some(new java.net.URL("http://github/kydos/dds-psm-java"))

publishTo := Some(Resolver.file("file",  new File("/Users/angelo/Labs/mvn/maven/releases")))

autoCompilerPlugins := true

scalacOptions += "-deprecation"

scalacOptions += "-unchecked"

scalacOptions += "-optimise"

scalacOptions += "-feature"

scalacOptions += "-language:postfixOps"


