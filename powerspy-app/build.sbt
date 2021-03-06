name := "powerspy-app"

// Logging
libraryDependencies ++= Seq(
  "org.apache.logging.log4j" % "log4j-api" % "2.1",
  "org.apache.logging.log4j" % "log4j-core" % "2.1"
)

// CLI
libraryDependencies ++= Seq(
  "org.rogach" %% "scallop" % "0.9.5"
)

lazy val downloadBluecoveApp = taskKey[File]("download-bluecove-app")
lazy val downloadBluecoveGplApp = taskKey[File]("download-bluecove-gpl-app")

downloadBluecoveApp := {
  val locationBluecove = baseDirectory.value / "lib" / "bluecove-2.1.0.jar"
  if(!locationBluecove.getParentFile.exists()) locationBluecove.getParentFile.mkdirs()
  if(!locationBluecove.exists()) IO.download(url("https://bluecove.googlecode.com/files/bluecove-2.1.0.jar"), locationBluecove)
  locationBluecove
}

downloadBluecoveGplApp := {
  val locationBluecoveGpl = baseDirectory.value / "lib" / "bluecove-gpl-2.1.0.jar"
  if(!locationBluecoveGpl.getParentFile.exists()) locationBluecoveGpl.getParentFile.mkdirs()
  if(!locationBluecoveGpl.exists()) IO.download(url("https://bluecove.googlecode.com/files/bluecove-gpl-2.1.0.jar"), locationBluecoveGpl)
  locationBluecoveGpl
}

mappings in Universal += downloadBluecoveApp.value -> s"lib/${downloadBluecoveApp.value.name}"

mappings in Universal += downloadBluecoveGplApp.value -> s"lib/${downloadBluecoveGplApp.value.name}"

mappings in Universal ++= {
  ((file("../") * "README*").get map {
    readmeFile: File =>
      readmeFile -> readmeFile.getName
  }) ++
  ((file("../") * "LICENSE*").get map {
    licenseFile: File =>
      licenseFile -> licenseFile.getName
  })
}

// To use for configuring log4j2.
scriptClasspath ++= Seq("../conf")
