#!bin/bash

cd src/
javac */*.java
java representations/Main
rm -f */*.class
