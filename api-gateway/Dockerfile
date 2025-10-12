FROM eclipse-temurin:17-jdk
WORKDIR /app

COPY target/api-gateway-0.0.1-SNAPSHOT.jar app.jar

# ⚡ Ép Spring Boot đọc file mount ngoài
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.config.location=file:/config/application.properties"]
