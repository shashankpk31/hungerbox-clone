FROM eclipse-temurin:17-jdk-alpine
COPY target/*.jar hungerbox_vendor.jar
ENTRYPOINT ["java", "-jar", "/hungerbox_vendor.jar"]