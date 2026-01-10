#!/bin/bash

# Colors for better visibility
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo -e "${YELLOW}Step 1: Cleaning up old containers and volumes...${NC}"
docker-compose down -v

echo -e "${YELLOW}Step 2: Packaging Backend Services with Maven...${NC}"
# Navigate to backend and build all jars, skipping tests for speed
cd backend
# Note: Ensure you have Maven installed locally
./mvnw clean package -DskipTests 
if [ $? -ne 0 ]; then
    echo "Backend build failed! Exiting..."
    exit 1
fi
cd ..

echo -e "${YELLOW}Step 3: Building and Starting Docker Containers...${NC}"
# --build ensures it picks up the fresh JARs and Nginx configs
docker-compose up --build -d

echo -e "${GREEN}SUCCESS: HungerBox system is starting up!${NC}"
echo -e "${GREEN}Frontend: http://localhost:3000${NC}"
echo -e "${GREEN}Gateway:  http://localhost:8080${NC}"
echo -e "${GREEN}Eureka:   http://localhost:8761${NC}"

# Show logs to monitor startup
echo -e "${YELLOW}Monitoring logs (Ctrl+C to stop monitoring, services will keep running)...${NC}"
docker-compose logs -f