#!/bin/bash

echo "start dev sh"

echo $@

echo $ENTRYPOINT_PATH

echo $TEST_ENV

python3 hello.py
