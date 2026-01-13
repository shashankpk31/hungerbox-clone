#!/bin/bash

# HungerBox Clone - Full Cloud Automation Script
# Usage: ./cloud-deploy.sh accesskey secret reason

if [ -z "$1" ] || [ -z "$2" ]; then
    echo "❌ Error: Missing credentials."
    echo "Usage: ./cloud-deploy.sh <KEY> <SECRET> <REGION>"
    exit 1
fi

# --- Configuration ---
export AWS_ACCESS_KEY_ID=$1
export AWS_SECRET_ACCESS_KEY=$2
export AWS_DEFAULT_REGION=${3:-"us-east-1"}
DB_PASSWORD="password" 
ACCOUNT_ID=$(aws sts get-caller-identity --query Account --output text)
ECR_URL="$ACCOUNT_ID.dkr.ecr.$AWS_DEFAULT_REGION.amazonaws.com"

echo "🚀 Starting Full HungerBox Cloud Infrastructure Deployment..."

# 1. AWS Configuration
aws configure set aws_access_key_id $AWS_ACCESS_KEY_ID
aws configure set aws_secret_access_key $AWS_SECRET_ACCESS_KEY
aws configure set region $AWS_DEFAULT_REGION

# 2. Networking: Master Security Group
VPC_ID=$(aws ec2 describe-vpcs --filters "Name=isDefault,Values=true" --query 'Vpcs[0].VpcId' --output text)
echo "🛡️ Setting up Security Group in VPC: $VPC_ID..."

SG_ID=$(aws ec2 create-security-group \
    --group-name hungerbox-sg-$(date +%s) \
    --description "HungerBox Microservices Network" \
    --vpc-id $VPC_ID --query 'GroupId' --output text)

# Open ports: 80(Web), 8080(Gateway), 8761(Eureka), 8888(Config), 5432(DB), 5672(Rabbit), 6379(Redis), 9411(Zipkin)
PORTS=(80 8080 8761 8888 5432 5672 15672 6379 9411)
for PORT in "${PORTS[@]}"; do
    aws ec2 authorize-security-group-ingress --group-id $SG_ID --protocol tcp --port $PORT --cidr 0.0.0.0/0
done

# 3. Sidecar Services: EC2 (Redis, RabbitMQ, Zipkin)
echo "☁️ Launching Sidecar EC2 Instance..."
USER_DATA=$(cat <<EOF
#!/bin/bash
yum update -y
amazon-linux-extras install docker -y
service docker start
usermod -a -G docker ec2-user
curl -L "https://github.com/docker/compose/releases/latest/download/docker-compose-\$(uname -s)-\$(uname -m)" -o /usr/local/bin/docker-compose
chmod +x /usr/local/bin/docker-compose
mkdir /home/ec2-user/infra
cat <<COMPOSE > /home/ec2-user/infra/docker-compose.yml
version: '3.8'
services:
  redis:
    image: redis:alpine
    ports: ["6379:6379"]
  rabbitmq:
    image: rabbitmq:3-management
    ports: ["5672:5672", "15672:15672"]
  zipkin:
    image: openzipkin/zipkin
    ports: ["9411:9411"]
COMPOSE
cd /home/ec2-user/infra && /usr/local/bin/docker-compose up -d
EOF
)

INFRA_IP=$(aws ec2 run-instances \
    --image-id ami-0440d3b780d96b29d \
    --count 1 \
    --instance-type t3.micro \
    --security-group-ids $SG_ID \
    --user-data "$USER_DATA" \
    --query 'Instances[0].PublicIpAddress' --output text)

# 4. Database: RDS Provisioning & Init
echo "🐘 Provisioning RDS PostgreSQL..."
aws rds create-db-instance \
    --db-instance-identifier hungerbox-db \
    --db-name postgres \
    --engine postgres \
    --db-instance-class db.t3.micro \
    --allocated-storage 20 \
    --master-username hbadmin \
    --master-user-password $DB_PASSWORD \
    --vpc-security-group-ids $SG_ID \
    --publicly-accessible || true

echo "⏳ Waiting for RDS to be available (takes ~5-7 mins)..."
aws rds wait db-instance-available --db-instance-identifier hungerbox-db
DB_ENDPOINT=$(aws rds describe-db-instances --db-instance-identifier hungerbox-db --query 'DBInstances[0].Endpoint.Address' --output text)

echo "🛠 Initializing Database Schema..."
PGPASSWORD=$DB_PASSWORD psql -h $DB_ENDPOINT -U user -d postgres -f ./init-db.sh

# 5. Build & Push Images to ECR
aws ecr get-login-password --region $AWS_DEFAULT_REGION | docker login --username AWS --password-stdin $ECR_URL

SERVICES=("discovery-server" "config-server" "api-gateway" "identity" "order-service" "vendor-service")
for SERVICE in "${SERVICES[@]}"; do
    echo "📦 Processing $SERVICE..."
    aws ecr create-repository --repository-name $SERVICE > /dev/null 2>&1 || true
    (cd ./backend/$SERVICE && ./mvnw clean package -DskipTests)
    docker build -t $SERVICE ./backend/$SERVICE
    docker tag $SERVICE:latest $ECR_URL/$SERVICE:latest
    docker push $ECR_URL/$SERVICE:latest
done

# Frontend Push
aws ecr create-repository --repository-name hungerbox-frontend > /dev/null 2>&1 || true
docker build -t hungerbox-frontend --build-arg VITE_API_BASE_URL=http://localhost:8080 ./frontend
docker tag hungerbox-frontend:latest $ECR_URL/hungerbox-frontend:latest
docker push $ECR_URL/hungerbox-frontend:latest

# 6. Deploy Discovery Server to ECS Fargate
echo "🏗️ Deploying Discovery Server to ECS..."
aws ecs create-cluster --cluster-name hungerbox-cluster

cat <<EOF > discovery-task.json
{
  "family": "discovery-server",
  "networkMode": "awsvpc",
  "containerDefinitions": [{
      "name": "discovery-server",
      "image": "$ECR_URL/discovery-server:latest",
      "portMappings": [{ "containerPort": 8761, "hostPort": 8761 }],
      "environment": [{ "name": "SPRING_PROFILES_ACTIVE", "value": "prod" }],
      "essential": true
  }],
  "requiresCompatibilities": ["FARGATE"],
  "cpu": "256", "memory": "512"
}
EOF

aws ecs register-task-definition --cli-input-json file://discovery-task.json
SUBNET_ID=$(aws ec2 describe-subnets --filters "Name=default-for-az,Values=true" --query 'Subnets[0].SubnetId' --output text)

aws ecs create-service \
    --cluster hungerbox-cluster \
    --service-name discovery-service \
    --task-definition discovery-server \
    --launch-type FARGATE \
    --desired-count 1 \
    --network-configuration "awsvpcConfiguration={subnets=[$SUBNET_ID],securityGroups=[$SG_ID],assignPublicIp=ENABLED}"

# 7. Final Summary & Discovery IP Finder
echo "------------------------------------------------"
echo "✅ INFRASTRUCTURE DEPLOYED SUCCESSFULLY"
echo "------------------------------------------------"
echo "RDS Endpoint:   $DB_ENDPOINT"
echo "Sidecar IP:     $INFRA_IP (Redis/Rabbit/Zipkin)"

echo "🔍 Finding Discovery Server IP..."
sleep 10
TASK_ARN=$(aws ecs list-tasks --cluster hungerbox-cluster --service-name discovery-service --query 'taskArns[0]' --output text)
ENI_ID=$(aws ecs describe-tasks --cluster hungerbox-cluster --tasks $TASK_ARN --query 'tasks[0].attachments[0].details[?name==`networkInterfaceId`].value' --output text)
DISCOVERY_IP=$(aws ec2 describe-network-interfaces --network-interface-ids $ENI_ID --query 'NetworkInterfaces[0].Association.PublicIp' --output text)

echo "Eureka Server:  http://$DISCOVERY_IP:8761"
echo "------------------------------------------------"
echo "Use 'http://$DISCOVERY_IP:8761/eureka' as the EUREKA_URL for all other services."