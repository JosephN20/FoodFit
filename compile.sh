#!/bin/bash

OPTS="-q -e" # quiet and produce execution error messages
JFX="-Dprism.order=sw" # specify the software renderer

set -ex
mvn $OPTS clean
mvn $OPTS compile
mvn $OPTS exec:java -Dprism.order=sw -Dexec.mainClass="cs1302.api.FoodFitAppDriver"
