#!/bin/bash
echo "Building Order Management Backend..."
mvn spotless:apply clean install
if [ $? -eq 0 ]; then
    echo "Build Successful!"
else
    echo "Build Failed!"
    exit 1
fi