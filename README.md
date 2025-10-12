http://localhost:8080/home
http://localhost:8080/khoa
http://localhost:8080/xacnhan
http://localhost:8080/login

http://localhost:8080/appointment-service/api/appointments

http://localhost:8080/users/departments
http://localhost:8080/user-service/api/users
http://localhost:8080/user-service/api/patients
http://localhost:8080/users/auth/signup
http://localhost:8080/users/auth/login
http://localhost:8080/user-service/api/doctors

docker exec -it postgres psql -U postgres

mvn clean package -DskipTests
docker compose down
docker compose build --no-cache
docker compose up -d
docker compose up -d --build


CREATE DATABASE diagnosis;
CREATE DATABASE notification;

