FROM eclipse-temurin:17-jdk-alpine
# Use a generic name to make the Dockerfile reusable
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
# Allows passing JVM arguments and Spring profiles at runtime
ENTRYPOINT ["java", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE:-prod}", "-jar", "/app.jar"]