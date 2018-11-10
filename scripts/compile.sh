#!bin/sh

cd $(dirname $0)/..
[ -d build ] || mkdir build/ build/datamining/
cp src/datamining/example_db.csv build/datamining/
javac -d build src/*/*.java
