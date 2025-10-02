#!/bin/bash
# This script is the entrypoint for the test runner container.

# Exit immediately if a command exits with a non-zero status.
set -e

echo "--- Pulling required browser images... ---"
# Check if browsers.json exists and is not empty before processing
if [ -s "/config/browsers.json" ]; then
  jq -r '.. | .image? | select(.)' /config/browsers.json | uniq | while read -r IMAGE; do
    echo "Pulling image: ${IMAGE}"
    docker pull "${IMAGE}"
  done
else
  echo "Warning: /config/browsers.json not found or is empty. Skipping image pull."
fi

echo "--- Browser images are up to date. Running tests... ---"
./gradlew tests --no-daemon --rerun-tasks -Dapi.base.uri=http://app:8080 -Dselenide.base.url=http://app:8080 -Dselenide.remote=http://selenoid:4444/wd/hub

echo "--- Generating Allure report... ---"
allure generate build/allure-results --clean -o build/allure-report
