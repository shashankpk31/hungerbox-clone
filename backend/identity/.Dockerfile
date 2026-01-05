FROM eclipse-temurin:17-jdk-alpine
COPY target/*.jar hungerbox_identity.jar
ENTRYPOINT ["java", "-jar", "/hungerbox_identity.jar"]