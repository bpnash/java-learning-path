#!/usr/bin/env bash

set -euo pipefail

CSV_FILE="names-techstack.csv"
ENDPOINT_URL="http://localhost:8080/api/v1/software-engineers"

# Skip header row
tail -n +2 "$CSV_FILE" | while IFS=',' read -r name techstack; do
  name="$(echo "$name" | xargs)"
  techstack="$(echo "$techstack" | xargs)"

  payload=$(jq -n \
    --arg name "$name" \
    --arg techStack "$techstack" \
    '{name: $name, techStack: $techStack}')

  response=$(curl -sS -X POST "$ENDPOINT_URL" \
    -H "Content-Type: application/json" \
    -d "$payload")

  echo "Sent: $name"
  echo "Response: $response"
  echo
done