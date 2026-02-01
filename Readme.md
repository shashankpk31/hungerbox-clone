# HungerBox Identity & Access Management (IAM) 🍱

**Production-grade authentication & authorization service** for the HungerBox food-tech ecosystem.  
Manages user lifecycle across roles: Super Admin, Organization Admin, Vendor, Employee — with secure JWT, OTP verification (Redis), event-driven notifications (RabbitMQ), and a smooth single-page onboarding experience.

Date: February 2026

## 🌟 Key Features

- Single-entry SPA (no separate `/login` or `/register` routes)
- State-machine style **LandingPage** (Home → Login → Register → OTP Verify) with Framer Motion animations
- **Initialization guard** → no white-screen flash during auth restore
- Recursive **ProtectedRoute** + **RBAC** (role-based nested layouts)
- **Verified-only** access enforcement (email/phone OTP check)
- Axios interceptor bridges `USER_NOT_VERIFIED` → auto OTP screen
- Redis for fast OTP storage with TTL
- RabbitMQ for decoupled email/SMS notification delivery
- Glassmorphism UI + branded toast notifications

## 🛠 Tech Stack (2026 editions)

### Backend
- **Java** — 17 (LTS) or 21 (newer LTS recommended)
- **Spring Boot** — 3.3+ (or 3.4 if available)
- **Spring Security** — 6.3+ (JWT stateless authentication)
- **Spring Data JPA** + Hibernate
- **Spring Boot Starter Web**
- **Redis** — 7.x (OTP cache & TTL)
- **RabbitMQ** — 3.12+ / 4.x (AMQP message broker)
- **PostgreSQL** — 16+ or **MySQL** — 8.4+ (persistent storage)
- **Maven** — 3.9+ (build tool)

### Frontend
- **React** — 18.3+
- **Vite** — 5.4+ or 6.x (build tool & dev server)
- **TypeScript** — 5.5+ (strongly recommended)
- **Tailwind CSS** — 3.4+ (with PostCSS & Autoprefixer)
- **react-router-dom** — 6.26+
- **@tanstack/react-query** — 5.x (data fetching & caching)
- **Framer Motion** — 11.x + AnimatePresence
- **Axios** — 1.7+ (with interceptors)
- **react-hot-toast** — 2.4+
- **Lucide React** — 0.4x (icons)

### Infrastructure / Dev Tools
- **Docker** — 27+ (highly recommended for Redis, RabbitMQ, DB)
- **docker-compose** — v2.29+ (multi-container local setup)
- **Git** — any recent version
- **Tortoise** - help ui based git process

## 📋 Software & Tools Required (One Developer Workstation)

| Category              | Software / Runtime                  | Version (2026)       | Why Needed                              | Download / Install Command                          |
|-----------------------|-------------------------------------|----------------------|------------------------------------------|------------------------------------------------------|
| Java Runtime & JDK    | OpenJDK / Eclipse Temurin / Oracle JDK | 17 or 21            | Compile & run Spring Boot                | sdkman, brew, choco, or https://adoptium.net        |
| Build Tool (BE)       | Maven                               | 3.9+                | Dependency management & packaging        | https://maven.apache.org / sdk install maven        |
| Node.js + npm / pnpm  | Node.js                             | 20.x or 22.x LTS    | Run Vite, install frontend deps          | https://nodejs.org / nvm / volta                    |
| Package Manager (FE)  | npm / pnpm / yarn                   | latest              | Faster & better than npm in many cases   | corepack enable pnpm (recommended)                  |
| Code Editor / IDE     | Spring Tool Suite  | 2025.x / 2026.x     | Best Spring Boot + Java support          | https://spring.io/blog/2018/04/16/spring-tool-suite-3-9-4-released                      |
| Code Editor (alt)     | Visual Studio Code                  | latest              | Excellent for React / TS / Tailwind      | https://code.visualstudio.com + extensions          |
| Database (local)      | PostgreSQL or MySQL                 | 16+ / 8.4+          | Persistent user/org data                 | Docker or native installer                          |
| Cache                 | Redis                               | 7.x                 | OTP storage with TTL                     | Docker or https://redis.io                          |
| Message Broker        | RabbitMQ                            | 3.13+ / 4.x         | Async notifications                      | Docker or https://www.rabbitmq.com                  |
| Containerization      | Docker + Docker Compose             | latest              | Easy local infra (DB + Redis + RabbitMQ) | https://www.docker.com                              |
| API Testing           | Postman / Insomnia / Bruno          | latest              | Test auth & user endpoints               | https://www.postman.com / https://usebruno.com      |
| Browser               | Chrome / Firefox / Edge             | latest              | DevTools, debugging                      | —                                                   |

**Minimal absolute requirements (no Docker)**  
Java 17+, Maven, Node.js 20+, one database (PostgreSQL/MySQL), Redis & RabbitMQ installed natively.

**Recommended (much easier)**  
Docker + docker-compose → spin up Redis, RabbitMQ, PostgreSQL in seconds.

## 🚀 Quick Start (Local Development – Docker Recommended)

### 1. Clone repository

```bash

git clone https://github.com/shashankpk31/hungerbox-clone.git
cd hungerbox-clone

```

### 2. Start infrastructure (Redis, RabbitMQ, PostgreSQL)

```bash
# Option A – Docker (strongly recommended)
docker compose up -d

# Option B – install natively & start services manually
```

### 3. Backend setup

```bash
cd backend
# Copy & edit application-dev.properties / application.yml
cp src/main/resources/application-example.yml src/main/resources/application-dev.yml

# Install dependencies & run
mvn clean install
mvn spring-boot:run -Dspring.profiles.active=dev
# or open in IntelliJ → Run HungerBoxApplication
```

### 4. Frontend setup

```bash
cd ../frontend
cp .env.example .env
# Edit VITE_API_BASE_URL=http://localhost:8089 (or your backend port)

pnpm install          # or npm install / yarn
pnpm dev              # or npm run dev
```

### 🧪 Testing the Flow

1. Open http://localhost:5173
2. Register → should send OTP via RabbitMQ (check console / logs)
3. Enter OTP → account becomes verified
4. Login → lands on role-based dashboard


