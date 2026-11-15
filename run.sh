#!/usr/bin/env bash
set -eu
cd "$(dirname "$0")"
mkdir -p build
find src -name '*.java' -print0 | xargs -0 javac -encoding UTF-8 -d build
java -cp build edu.oop.library.lab3.app.Main
