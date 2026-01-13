#!/bin/bash
YELLOW='\033[1;33m'
RED='\033[0;31m'
GREEN='\033[0;32m'
NC='\033[0m'

# List of your coding services (excluding databases/infrastructure)
SERVICES=("discovery-server" "config-server" "api-gateway" "identity" "order-service" "vendor-service")

echo -e "${YELLOW}Step 1: Building Java Binaries...${NC}"
for SERVICE in "${SERVICES[@]}"; do
    if [ -d "backend/$SERVICE" ]; then
        echo -e "${YELLOW}--- Building $SERVICE ---${NC}"
        # Navigate, Build, and return
        (cd "backend/$SERVICE" && ./mvnw clean package -DskipTests)
    else
        echo -e "${RED}Error: Folder backend/$SERVICE not found!${NC}"
    fi
done

echo -e "${YELLOW}Step 2: Updating Containers (Preserving Databases)...${NC}"

# --build: Rebuilds the image if the .jar has changed
# --no-deps: Don't restart linked services (like postgres)
# -d: Run in background
docker-compose up -d --build --no-deps "${SERVICES[@]}"

echo -e "${GREEN}Step 3: Cleaning up old images...${NC}"
# Removes 'dangling' images left over from the build process to save disk space
docker image prune -f

echo -e "${GREEN}SUCCESS: New code deployed. Database was not interrupted.${NC}"