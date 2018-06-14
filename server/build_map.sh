#!/usr/bin/env bash
echo "USAGE:   ./build_map.sh [input file] [distance] "
echo "EXAMPLE: ./build_map.sh data/singapore_muiti_store_parking.json 1000"
mvn package

python3 src/main/python/mapbuilder/reduce.py $1 result.json $2
java -cp target/SimulationUAV-1.0-SNAPSHOT.jar mapbuilder.Triangulate result.json
# TODO add edge distance filtering
python3 src/main/python/mapbuilder/show.py result.json