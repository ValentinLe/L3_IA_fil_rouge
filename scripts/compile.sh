#!bin/sh

cd $(dirname $0)/..
[ -d build ] || mkdir build/ build/datamining/
cp -r src/databases/ build/
javac -d build src/*/*.java
