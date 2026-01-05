FROM eclipse-temurin:17-jdk-alpine
COPY target/*.jar hungerbox_apigateway.jar
ENTRYPOINT ["java", "-jar", "/hungerbox_apigateway.jar"]