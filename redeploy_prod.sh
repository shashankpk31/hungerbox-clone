## This script deploy or run every thing on docker

#!/bin/bash
YELLOW='\033[1;33m'
RED='\033[0;31m'
GREEN='\033[0;32m'
NC='\033[0m'

# CONFIGURATION
COMPOSE_FILE="docker-compose-hb.yml"
PROJECT_NAME="hb-admin"

echo -e "${YELLOW}Step 1: Cleaning up $PROJECT_NAME containers...${NC}"
docker compose -p $PROJECT_NAME -f $COMPOSE_FILE down -v
## docker compose -p hb-admin -f docker-compose-hb.yml  down -v

echo -e "${YELLOW}Step 2: Building Microservices...${NC}"
# ADDED: notification-service to this list
SERVICES=("discovery-server" "config-server" "api-gateway" "identity" "order-service" "vendor-service" "menu-service" "inventory-service" "wallet-service" "payment-service" "notification-service")

for SERVICE in "${SERVICES[@]}"; do
    if [ -d "backend/$SERVICE" ]; then
        echo -e "${YELLOW}Building $SERVICE...${NC}"
        # Navigate, build, and return safely
        (cd "backend/$SERVICE" && ./mvnw clean package -DskipTests -q)
        
        if [ $? -ne 0 ]; then
            echo -e "${RED}Build failed for $SERVICE! Exiting...${NC}"
            exit 1
        fi
    else
        echo -e "${RED}Error: Folder backend/$SERVICE not found!${NC}"
    fi
done

echo -e "${YELLOW}Step 3: Starting $PROJECT_NAME Stack...${NC}"
docker compose -p $PROJECT_NAME -f $COMPOSE_FILE up --build -d
## docker compose -p hb-admin -f docker-compose-hb.yml  up --build -d
echo -e "${GREEN}Hungerbox Admin Stack is starting up!${NC}"