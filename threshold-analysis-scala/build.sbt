
ThisBuild / scalaVersion := "3.2.2"

libraryDependencies += "org.tensorflow" % "tensorflow-core-platform" % "0.5.0"
libraryDependencies += "org.tensorflow" % "tensorflow-core-api" % "0.5.0"
libraryDependencies += "org.tensorflow" % "ndarray" % "0.4.0"
libraryDependencies += "com.sksamuel.scrimage" % "scrimage-core" % "4.0.34"

assemblyJarName in assembly := "scala-client.jar"
test in assembly := {}
assemblyMergeStrategy in assembly := {
  case PathList(ps @ _*) if ps.last endsWith "pom.properties"   => MergeStrategy.first
  case "META-INF/io.netty.versions.properties"                  => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith "module-info.class" => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".class" => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".tasty" => MergeStrategy.first
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}

