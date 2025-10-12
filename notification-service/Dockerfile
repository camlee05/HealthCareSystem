# Sử dụng JDK 17
FROM eclipse-temurin:17-jdk

# Tạo thư mục làm việc
WORKDIR /app

# Copy file jar vào container
COPY target/notification-service-0.0.1-SNAPSHOT.jar app.jar

# Chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]
