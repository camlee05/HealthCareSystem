# Dùng JDK 17 chính thức
FROM eclipse-temurin:17-jdk

# Thư mục làm việc trong container
WORKDIR /app

# Copy file jar đã build vào container
COPY target/diagnosis-service-0.0.1-SNAPSHOT.jar app.jar

# Mở port của service (ví dụ chạy ở 8083)
EXPOSE 8083

# Lệnh chạy ứng dụng Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]
