# Sử dụng JDK 17
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copy đúng tên file jar từ target
COPY target/discovery-server-0.0.1-SNAPSHOT.jar app.jar

# Chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]
