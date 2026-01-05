FROM eclipse-temurin:17-jdk-alpine
COPY target/*.jar hungerbox_order.jar
ENTRYPOINT ["java", "-jar", "/hungerbox_order.jar"]