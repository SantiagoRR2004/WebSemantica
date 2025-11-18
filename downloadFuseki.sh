#!/bin/bash

# Define variables
URL="https://dlcdn.apache.org/jena/binaries/apache-jena-fuseki-5.6.0.tar.gz"
FILENAME="apache-jena-fuseki.tar.gz"
FOLDER="apache-jena-fuseki"

# Check if the folder already exists
if [ -d "$FOLDER" ]; then
    echo "$FOLDER already exists."
    exit 0
else
    echo "Downloading $FILENAME ..."
    curl -L -e "https://figshare.com" -A "Mozilla/5.0" -o "$FILENAME" "$URL"
    echo "Unpacking $FILENAME ..."
    tar -xzf "$FILENAME"
fi
