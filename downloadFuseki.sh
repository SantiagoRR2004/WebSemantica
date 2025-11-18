#!/bin/bash

# Define variables
APACHE="https://dlcdn.apache.org/jena/binaries/"
FOLDER="apache-jena-fuseki"

# Check if the folder already exists
if [ -d "$FOLDER" ]; then
    echo "$FOLDER already exists."
    exit 0
fi

# Fetch the Apache Jena binaries page and extract the latest Fuseki version
echo "Fetching latest Fuseki version..."
PAGE_CONTENT=$(curl -s "$APACHE")

# Extract the latest apache-jena-fuseki version number from the page
# Look for links like "apache-jena-fuseki-X.Y.Z.tar.gz"
VERSION=$(echo "$PAGE_CONTENT" | grep -oP 'apache-jena-fuseki-\K[0-9]+\.[0-9]+\.[0-9]+' | sort -V | tail -1)

# Check if we found a version
if [ -z "$VERSION" ]; then
  echo "Could not find Fuseki version in Apache downloads."
  exit 1
fi

echo "Found latest version: $VERSION"

# Construct the download URL
FILENAME="apache-jena-fuseki-${VERSION}.tar.gz"
URL="${APACHE}${FILENAME}"

# Download the asset
echo "Downloading $FILENAME..."
curl -L -o "$FILENAME" "$URL"

if [ $? -ne 0 ]; then
  echo "Download failed."
  exit 1
fi

echo "Download complete."

# Extract the archive
echo "Unpacking $FILENAME ..."
tar -xzf "$FILENAME"

# Rename to generic folder name
echo "Cleaning up..."
mv "apache-jena-fuseki-${VERSION}" "$FOLDER"

echo "Downloaded version: $VERSION"
