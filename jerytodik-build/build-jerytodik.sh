#!/bin/sh
#export current_location=`pwd`
#export jerytodik_home="$current_location/jerytodik-root/jerytodik"
cd jerytodik-root
mvn -DskipTests=true clean install
