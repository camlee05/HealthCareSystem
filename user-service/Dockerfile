# --- Base image ---
FROM eclipse-temurin:17-jdk

# --- Làm việc trong thư mục /app ---
WORKDIR /app

# --- Copy file JAR build sẵn ---
COPY target/user-service-0.0.1-SNAPSHOT.jar app.jar

# --- Copy thư mục templates và static để có thể ghi đè khi mount ---
RUN mkdir -p /app/templates /app/static

# --- Expose port ---
EXPOSE 8081

# --- Run Spring Boot app ---
ENTRYPOINT ["java", "-jar", "app.jar"]
