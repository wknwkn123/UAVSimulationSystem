name := "UAVSIM"

version := "0.1"

scalaVersion := "2.12.6"

libraryDependencies ++= Seq("org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.0",
                            "com.typesafe.akka" %% "akka-actor" % "2.5.14",
                            "com.typesafe.akka" %% "akka-http"   % "10.1.5",
                            "com.typesafe.akka" %% "akka-stream" % "2.5.15",
                            "com.typesafe.akka" %% "akka-testkit" % "2.5.14",
                            "org.apache.kafka" % "kafka-clients" % "0.10.1.0",
                            "ch.qos.logback" % "logback-classic" % "1.2.3",
                            "com.typesafe.scala-logging" %% "scala-logging" % "3.9.0",
                            "net.liftweb" %% "lift-json" % "3.1.1",
                            "io.spray" %%  "spray-json" % "1.3.4"

)