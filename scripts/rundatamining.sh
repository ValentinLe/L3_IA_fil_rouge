#!bin/sh

cd $(dirname $0)/..
if sh scripts/compile.sh
then
cd build/
java datamining.Main $1 $2 $3
fi
