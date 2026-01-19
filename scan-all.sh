SONAR_TOKEN="YOUR_GENERATED_TOKEN"
SONAR_URL="http://host.docker.internal:9000"

# --- SCAN BACKEND SERVICES ---
SERVICES=("discovery-server" "config-server" "api-gateway" "identity" "order-service" "vendor-service""menu-service""inventory-service""wallet-service""payment-service")

for service in "${SERVICES[@]}"
do
  echo "Scanning Backend: $service..."
  docker run --rm \
    -e SONAR_HOST_URL=$SONAR_URL \
    -e SONAR_SCANNER_OPTS="-Dsonar.projectKey=hb-$service" \
    -e SONAR_TOKEN=$SONAR_TOKEN \
    -v "$(pwd)/backend/$service:/usr/src" \
    sonarsource/sonar-scanner-cli
done

# --- SCAN FRONTEND ---
echo "Scanning Frontend..."
docker run --rm \
  -e SONAR_HOST_URL=$SONAR_URL \
  -e SONAR_SCANNER_OPTS="-Dsonar.projectKey=hb-frontend -Dsonar.javascript.lcov.reportPath=coverage/lcov.info" \
  -e SONAR_TOKEN=$SONAR_TOKEN \
  -v "$(pwd)/frontend:/usr/src" \
  sonarsource/sonar-scanner-cli