#!/bin/bash

SONAR_TOKEN="squ_6e5c4d5d5f1fa9fc6ddcda3985c914040ca4a27b"
SONAR_URL="http://localhost:9000"

SERVICES=(
  discovery-server
  config-server
  api-gateway
  identity
  order-service
  vendor-service
  menu-service
  inventory-service
  wallet-service
  payment-service
)

for service in "${SERVICES[@]}"; do
  echo "Scanning backend: $service"
  cd backend/$service || exit 1

  mvn clean verify sonar:sonar \
    -Dsonar.projectKey=hb-$service \
    -Dsonar.host.url=$SONAR_URL \
    -Dsonar.login=$SONAR_TOKEN \
    -Dsonar.exclusions=**/target/**,**/generated/** \
    -Dsonar.verbose=true

  cd - > /dev/null
done

# --- SCAN FRONTEND ---
echo "Running Frontend Tests..."
cd frontend
npm install
npm run test:coverage  # This generates coverage/lcov.info
cd ..

docker run --rm \
  --network host \
  -e SONAR_HOST_URL=http://localhost:9000 \
  -e SONAR_TOKEN=$SONAR_TOKEN \
  -e SONAR_SCANNER_OPTS="
    -Dsonar.projectKey=hb-frontend
    -Dsonar.sources=src
    -Dsonar.exclusions=**/node_modules/**,**/*.test.js
    -Dsonar.javascript.lcov.reportPaths=coverage/lcov.info
    -Dsonar.verbose=true" \
  -v "$(pwd):/usr/src" \
  sonarsource/sonar-scanner-cli
