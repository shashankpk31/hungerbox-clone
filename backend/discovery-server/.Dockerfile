FROM eclipse-temurin:17-jdk-alpine
COPY target/*.jar hungerbox_discovery_server.jar
ENTRYPOINT ["java", "-jar", "/hungerbox_discovery_server.jar"]