Role: You are an expert Cloud Architect and Container related stuff and Spring Boot Developer.

Task: I need you to configure a Spring Boot Microservices project for dual-environment operation (Local and Docker).

Context:

GitHub Repository: (https://github.com/shashankpk31/hungerbox-clone/tree/main)

Project Structure: 

```
hungerbox-clone/
    docker-compose.yml
    dump.md
    features.md
    Readme.md
    setup.sh
    tree.txt
    tree_gen.py
    backend/
        Readme.md
        api-gateway/
            pom.xml
            src/
                main/
                    java/
                        com/
                            hungerbox/
                                api_gateway/
                                    ApiGatewayApplication.java
                                    config/
                                        JwtAuthenticationFilter.java
                    resources/
                        application.yml
                test/
                    java/
                        com/
                            hungerbox/
                                api_gateway/
                                    ApiGatewayApplicationTests.java
        config-server/
            pom.xml
            src/
                main/
                    java/
                        com/
                            hungerbox/
                                config_server/
                                    ConfigServerApplication.java
                    resources/
                        application.yml
                test/
                    java/
                        com/
                            hungerbox/
                                config_server/
                                    ConfigServerApplicationTests.java
        discovery-server/
            pom.xml
            src/
                main/
                    java/
                        com/
                            hungerbox/
                                discovery_server/
                                    DiscoveryServerApplication.java
                    resources/
                        application.yml
                test/
                    java/
                        com/
                            hungerbox/
                                discovery_server/
                                    DiscoveryServerApplicationTests.java
        identity/
            pom.xml
            src/
                main/
                    java/
                        com/
                            hungerbox/
                                identity/
                                    IdentityApplication.java
                                    aspect/
                                        LoggingAspect.java
                                    config/
                                        ApplicationConfig.java
                                        AuditorAwareImpl.java
                                        JpaConfig.java
                                        JwtAuthenticationFilter.java
                                        SecurityConfig.java
                                    constant/
                                        ErrorMessageWarnConstant.java
                                    controller/
                                        AuthController.java
                                        UserController.java
                                    dto/
                                        request/
                                            AuthRequest.java
                                            RegisterRequest.java
                                        response/
                                            ApiResponse.java
                                            EmptyJson.java
                                            LoginResponse.java
                                            UserResponse.java
                                    entity/
                                        AuditLog.java
                                        BaseEntity.java
                                        Role.java
                                        User.java
                                    eventlistener/
                                        CustomAuditListener.java
                                    exception/
                                        GlobalExceptionHandler.java
                                    Mapper/
                                        UserMapper.java
                                    repository/
                                        AuditLogRepository.java
                                        UserRepository.java
                                    service/
                                        AuthService.java
                                        CustomUserDetailsService.java
                                        JwtService.java
                                    util/
                                        BeanUtil.java
                    resources/
                        application.yml
                        bootstrap.yml
                test/
                    java/
                        com/
                            hungerbox/
                                identity/
                                    IdentityApplicationTests.java
        order-service/
            pom.xml
            src/
                main/
                    java/
                        com/
                            hungerbox/
                                order_service/
                                    OrderServiceApplication.java
                    resources/
                        application.yml
                        bootstrap.yml
                test/
                    java/
                        com/
                            hungerbox/
                                order_service/
                                    OrderServiceApplicationTests.java
        vendor-service/
            pom.xml
            src/
                main/
                    java/
                        com/
                            hungerbox/
                                vendor_service/
                                    VendorServiceApplication.java
                    resources/
                        application.yml
                        bootstrap.yml
                test/
                    java/
                        com/
                            hungerbox/
                                vendor_service/
                                    VendorServiceApplicationTests.java
    frontend/
        eslint.config.js
        index.html
        package.json
        postcss.config.js
        README.md
        setup.sh
        tailwind.config.js
        vite.config.js
        public/
            vite.svg
        src/
            App.jsx
            main.jsx
            api/
                axiosInstance.js
            components/
                ui/
                    Button.jsx
                    Input.jsx
                    Modal.jsx
                    PhoneMockup.jsx
                    Toggle/
                        AuthToggle.jsx
            context/
                AuthContext.jsx
            features/
                admin/
                    components/
                        StatsCard.jsx
                    pages/
                        UserManagement/
                            index.jsx
                        VendorOnboarding/
                            index.jsx
                    services/
                        adminService.js
                auth/
                    components/
                        LoginForm.jsx
                        RegisterForm.jsx
                        forms/
                            EmployeeFields.jsx
                            VendorFields.jsx
                    hooks/
                        useAuthStatus.js
                    pages/
                        AdminRegister/
                            index.jsx
                        Login/
                            index.jsx
                        Register/
                            index.jsx
                    services/
                        authService.js
                employee/
                    components/
                        CartOverlay.jsx
                        MenuCard.jsx
                        OrderStepper.jsx
                        VendorList.jsx
                    hooks/
                        useCart.js
                    pages/
                        Cart/
                            index.jsx
                        Home/
                            index.jsx
                        Tracking/
                            index.jsx
                        VendorDetail/
                            index.jsx
                    services/
                        catalogService.js
                        orderService.js
                home/
                    components/
                        HeroContent.jsx
                    pages/
                        LandingPage.jsx
                vendor/
                    components/
                        OrderCard.jsx
                        ToggleAvailability.jsx
                    hooks/
                        useLiveOrders.js
                    pages/
                        Dashboard/
                            index.jsx
                        LiveOrders/
                            index.jsx
                        MenuManagement/
                            index.jsx
                    services/
                        vendorService.js
            layouts/
                MainLayout/
                    index.jsx
                VendorLayout/
                    index.jsx
            routes/
                AppRoutes.jsx
                ProtectedRoute.jsx
            styles/
                index.css
            utils/
                constants.js
                formatCurrency.js
```

Core Requirement: The internal services (identity, order, vendor) must be hidden from the public. Only the api-gateway should be accessible externally via port 8080.

Technology Stack: Java 17, Spring Boot 3.x, Eureka Discovery, Spring Cloud Gateway, Config Server,PostgreSQL.

Input Files Provided:

docker-compose.yml (current version)
version: '3.8'
services:
  postgres:
    image: postgres:15
    environment:
      POSTGRES_USER: user
      POSTGRES_PASSWORD: password
    ports:
      - "5433:5432"

  #  discovery-server:
  #   build: ./discovery-server
  #   ports:
  #     - "8761:8761"
  #   environment:
  #     - EUREKA_CLIENT_REGISTER_WITH_EUREKA=false

  # config-server:
  #   build: ./config-server
  #   ports:
  #     - "8888:8888"
  #   depends_on:
  #     - discovery-server
  #   environment:
  #     - SPRING_PROFILES_ACTIVE=native
  #     - EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://discovery-server:8761/eureka/

  # api-gateway:
  #   build: ./api-gateway
  #   ports:
  #     - "8080:8080"
  #   depends_on:
  #     - config-server
  #   environment:
  #     - SPRING_CONFIG_IMPORT=optional:configserver:http://config-server:8888
  #     - EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://discovery-server:8761/eureka/

  redis:
    image: redis:alpine
    ports:
      - "6379:6379"

  rabbitmq:
    image: rabbitmq:3-management
    ports:
      - "5672:5672"
      - "15672:15672"

  zipkin:
    image: openzipkin/zipkin
    ports:
      - "9411:9411"

application.yml (from each service)

```
server:
  port: 8080

spring:
  application:
    name: api-gateway
  cloud:
    gateway:
      globalcors:
        cors-configurations:
          '[/**]':
            allowedOriginPatterns: "*"
            allowedMethods:
              - GET
              - POST
              - PUT
              - DELETE
              - OPTIONS
            allowedHeaders: "*"
            allowCredentials: true
      routes:
        # Open Route: No filter here
        - id: identity
          uri: lb://IDENTITY
          predicates:
            - Path=/auth/** , /user/**

        # Protected Route: AuthenticationFilter applied
        - id: order-service
          uri: lb://ORDER-SERVICE
          predicates:
            - Path=/orders/**
          filters:
            - JwtAuthenticationFilter

        # Protected Route: AuthenticationFilter applied
        - id: vendor-service
          uri: lb://VENDOR-SERVICE
          predicates:
            - Path=/vendors/**
          filters:
            - JwtAuthenticationFilter

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
```
```
server:
  port: 8888

spring:
  application:
    name: config-server

eureka:
    instance:
      prefer-ip-address: true
    client:
      service-url:
        defaultZone: http://localhost:8761/eureka/
```

```
server:
  port: 8761

spring:
  application:
    name: discovery-server

eureka:
  instance:
    hostname: localhost
  client:
    register-with-eureka: false
    fetch-registry: false
    service-url:
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/
```

```
spring:
  application:
    name: identity
  datasource:
    url: jdbc:postgresql://localhost:5433/hungerbox_identity
    username: user
    password: password
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

server:
  port: 8082
eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
```

```
bootstrap.yml 
spring:
  cloud:
    config:
      uri: http://localhost:8888
      fail-fast: true # Stops the app if config server is down
```

```
spring:
  application:
    name: vendor-service
  datasource:
    url: jdbc:postgresql://localhost:5433/hungerbox_vendor
    username: user
    password: password
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

server:
  port: 8083

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
```

```
spring:
  application:
    name: order-service
  datasource:
    url: jdbc:postgresql://localhost:5433/hungerbox_orders
    username: user
    password: password
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

server:
  port: 8081

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
```

Dockerfile (from each service)

```
FROM eclipse-temurin:17-jdk-alpine
COPY target/*.jar hungerbox_apigateway.jar
ENTRYPOINT ["java", "-jar", "/hungerbox_apigateway.jar"]
```

```
FROM eclipse-temurin:17-jdk-alpine
COPY target/*.jar hungerbox_config_server.jar
ENTRYPOINT ["java", "-jar", "/hungerbox_config_server.jar"]
```

```
FROM eclipse-temurin:17-jdk-alpine
COPY target/*.jar hungerbox_discovery_server.jar
ENTRYPOINT ["java", "-jar", "/hungerbox_discovery_server.jar"]
```
```
FROM eclipse-temurin:17-jdk-alpine
COPY target/*.jar hungerbox_identity.jar
ENTRYPOINT ["java", "-jar", "/hungerbox_identity.jar"]
```
```
FROM eclipse-temurin:17-jdk-alpine
COPY target/*.jar hungerbox_order.jar
ENTRYPOINT ["java", "-jar", "/hungerbox_order.jar"]
```
```
FROM eclipse-temurin:17-jdk-alpine
COPY target/*.jar hungerbox_vendor.jar
ENTRYPOINT ["java", "-jar", "/hungerbox_vendor.jar"]
```

Required Output: Please provide the updated code or text for:

Dockerfiles: Modify them to accept dynamic Spring Profiles at runtime via ENTRYPOINT.

Configuration Files: Create/Update application-dev.yml (for local running) and application-prod.yml (for Docker network running) bootstrap.yml for every service.

Internal URLs: In prod profiles, ensure DB URLs use the Docker service name (e.g., jdbc:postgresql://postgres:5432/...) and Eureka uses the discovery server name.

Docker Compose: Update the services block to pass the SPRING_PROFILES_ACTIVE=prod environment variable and remove host port mappings for internal services to satisfy the "Gateway-only" requirement.

Gateway Routing: Configure the api-gateway routes to use lb:// (Load Balancer) syntax to find internal services via Eureka.

Format: Present the changes file-by-file with clear headings and brief explanations of why each change was made.