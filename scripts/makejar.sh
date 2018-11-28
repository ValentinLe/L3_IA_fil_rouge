#!bin/sh

cd $(dirname $0)/..

sh scripts/compile.sh
[ -d jar ] || mkdir jar

nameArchive="IA&AD.jar"
jar cf $nameArchive build/ src/ databases/ scripts/
mv $nameArchive jar/
