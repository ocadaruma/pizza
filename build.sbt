lazy val commonSettings = Seq(
  scalaVersion := "2.11.7",
  unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" ),
  resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases",
  libraryDependencies ++= Seq(
    "net.databinder.dispatch" %% "dispatch-core" % "0.11.2",
    "org.json4s" %% "json4s-native" % "3.3.0",
    "org.json4s" %% "json4s-jackson" % "3.3.0",
    "org.json4s" %% "json4s-ext" % "3.3.0",
    "com.github.nscala-time" %% "nscala-time" % "2.6.0",
    "commons-codec" % "commons-codec" % "1.10",
    "commons-io" % "commons-io" % "2.4",
    "com.h2database"  %  "h2"                           % "1.4.188", // your jdbc driver here
    "org.scalikejdbc" %% "scalikejdbc"                  % "2.2.8",
    "org.scalikejdbc" %% "scalikejdbc-config"           % "2.2.8",
    "org.scalikejdbc" %% "scalikejdbc-play-initializer" % "2.4.1",
    "mysql" % "mysql-connector-java" % "5.1.36",
    "org.apache.httpcomponents" % "httpclient" % "4.5",
    "org.apache.httpcomponents" % "httpmime" % "4.5",
    "org.apache.httpcomponents" % "fluent-hc" % "4.5",
    "edu.uci.ics" % "crawler4j" % "4.1",
    "org.jsoup" % "jsoup" % "1.8.3"
  )
)

lazy val common = (project in file("common"))
  .settings(commonSettings: _*)

lazy val pizzaCrawler = (project in file("pizza-crawler"))
  .settings(commonSettings: _*)
  .dependsOn(common)

lazy val pizzaWeb = (project in file("pizza-web")).enablePlugins(PlayScala)
  .settings(
    libraryDependencies ++= Seq(
      jdbc,
      cache,
      ws,
      specs2 % Test ))
  .settings(commonSettings: _*)
  .dependsOn(common)
