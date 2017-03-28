#!/bin/sh
echo "Emplacement de Java : "
echo $JAVA_HOME
echo "Version de Java : "
java -version
cd jerytodik-root
mvn clean install
