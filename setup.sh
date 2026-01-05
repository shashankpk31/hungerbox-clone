#!/bin/bash

# Configuration
GROUP_ID="com.hungerbox.clone"

generate_service() {
    ARTIFACT_ID=$1
    DEPENDENCIES=$2
    
    echo "------------------------------------------------"
    echo "Generating $ARTIFACT_ID..."
    
    OUT_FILE="${ARTIFACT_ID}.tar.gz"
    
    # Using the most updated dependency IDs
    URL="https://start.spring.io/starter.tgz?type=maven-project&language=java&baseDir=${ARTIFACT_ID}&groupId=${GROUP_ID}&artifactId=${ARTIFACT_ID}&name=${ARTIFACT_ID}&dependencies=${DEPENDENCIES}&javaVersion=17"

    curl -L "$URL" -o "$OUT_FILE"

    if [ -f "$OUT_FILE" ]; then
        if file "$OUT_FILE" | grep -q "gzip compressed data"; then
            tar -xzvf "$OUT_FILE"
            rm "$OUT_FILE"
            echo "✅ Successfully generated $ARTIFACT_ID"
        else
            echo "❌ Error downloading $ARTIFACT_ID"
            cat "$OUT_FILE"
            rm "$OUT_FILE"
        fi
    fi
}

# Clean start for failed services (keeps the ones that worked)
mkdir -p backend
cd backend

# 1. Infrastructure (Discovery and Config already worked, but re-running ensures consistency)
generate_service "config-server" "cloud-config-server,actuator"
generate_service "discovery-server" "cloud-eureka-server,actuator"
generate_service "api-gateway" "cloud-gateway,cloud-eureka-discovery,cloud-resilience4j,actuator"

# 2. Business Services
# Note: cloud-eureka-client -> cloud-eureka-discovery
# Note: rabbitmq -> amqp
generate_service "identity-service" "web,data-jpa,postgresql,security,cloud-eureka-discovery,lombok,actuator"
generate_service "order-service" "web,data-jpa,postgresql,amqp,cloud-eureka-discovery,lombok,actuator"
generate_service "vendor-service" "web,data-jpa,postgresql,cloud-eureka-discovery,lombok,actuator"

echo "------------------------------------------------"
echo "Check the 'backend' folder. All services should be there now."