#!/bin/sh
version=${project.version}
jetty=9.4.12.v20180830
nexus=${nexus.url}
wget -O recognition-web.war $nexus/repository/mo2christian/com/mo2christian/recognition/recognition-web/$version/recognition-web-$version.war
wget -O jetty-runner.jar $nexus/repository/maven-company/org/eclipse/jetty/jetty-runner/$jetty/jetty-runner-$jetty.jar
java -jar jetty-runner.jar --port 80 recognition-web.war > output.txt