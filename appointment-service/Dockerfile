# Sử dụng JDK 17
FROM eclipse-temurin:17-jdk

# Thư mục làm việc trong container
WORKDIR /app

# Copy file jar đã build vào container
COPY target/appointment-service-0.0.1-SNAPSHOT.jar app.jar

# Chạy service
ENTRYPOINT ["java", "-jar", "app.jar"]
