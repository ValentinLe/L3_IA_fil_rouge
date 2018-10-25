#!bin/sh

cd $(dirname $0)/..

sh scripts/compile.sh
[ -d jar ] || mkdir jar

nameArchive="Archive.jar"
jar cf $nameArchive build/ src/
mv $nameArchive jar/
