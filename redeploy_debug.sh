## This script run code on local and db , other service on docker
#!/bin/bash
YELLOW='\033[1;33m'
RED='\033[0;31m'
GREEN='\033[0;32m'
NC='\033[0m'

# CONFIGURATION
COMPOSE_FILE="docker-compose-dev.yml"
PROJECT_NAME="hb-dev"

echo -e "${YELLOW}Step 1: Cleaning up $PROJECT_NAME containers...${NC}"
docker compose -p $PROJECT_NAME -f $COMPOSE_FILE down -v
## docker compose -p hb-dev -f docker-compose-dev  down -v

echo -e "${YELLOW}Step 3: Starting $PROJECT_NAME Stack...${NC}"
docker compose -p $PROJECT_NAME -f $COMPOSE_FILE up --build -d
## docker compose -p hb-dev -f docker-compose-dev  up --build -d
echo -e "${GREEN}Hungerbox Admin Stack is starting up!${NC}"