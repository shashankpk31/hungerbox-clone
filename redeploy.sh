#!/bin/bash
YELLOW='\033[1;33m'
RED='\033[0;31m'
GREEN='\033[0;32m'
NC='\033[0m'

echo -e "${YELLOW}Step 1: Cleaning up containers...${NC}"
docker-compose down

echo -e "${YELLOW}Step 2: Building Microservices...${NC}"
# MATCHED EXACTLY TO YOUR FOLDER LIST
SERVICES=("discovery-server" "config-server" "api-gateway" "identity" "order-service" "vendor-service""menu-service""inventory-service""wallet-service""payment-service")

for SERVICE in "${SERVICES[@]}"; do
    if [ -d "backend/$SERVICE" ]; then
        echo -e "${YELLOW}Building $SERVICE...${NC}"
        cd "backend/$SERVICE" && ./mvnw clean package -DskipTests && cd ../..
    else
        echo -e "${RED}Error: Folder backend/$SERVICE not found! Check spelling.${NC}"
    fi
done

echo -e "${YELLOW}Step 3: Starting Docker...${NC}"
docker-compose up --build -d