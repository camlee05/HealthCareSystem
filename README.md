🏥 Hospital Microservices System

Hệ thống Quản lý Bệnh viện được xây dựng theo kiến trúc Microservices, nhằm hỗ trợ quản lý hồ sơ bệnh nhân, bác sĩ, phòng điều trị, lịch khám và các nghiệp vụ liên quan trong bệnh viện.

Dự án được phát triển phục vụ mục tiêu học tập – nghiên cứu, đồng thời mô phỏng một hệ thống thực tế có khả năng mở rộng, bảo trì và triển khai độc lập từng dịch vụ.

📌 Mục tiêu dự án

Áp dụng kiến trúc Microservices vào bài toán quản lý bệnh viện

Tách biệt rõ business logic giữa các dịch vụ

Dễ dàng mở rộng, bảo trì và triển khai độc lập

Làm quen với Spring Boot, RESTful API, Docker

👥 Đối tượng sử dụng

Quản trị viên (Admin): quản lý hệ thống, cấu hình dữ liệu

Bác sĩ: xem danh sách bệnh nhân, lịch khám

Nhân viên y tế: quản lý hồ sơ bệnh nhân, phòng điều trị

Bệnh nhân (mở rộng): xem lịch khám, thông tin cá nhân

🧱 Kiến trúc hệ thống

Hệ thống được chia thành nhiều dịch vụ độc lập, giao tiếp với nhau thông qua REST API.

Các microservices chính:

patient-service: Quản lý hồ sơ bệnh nhân

doctor-service: Quản lý thông tin bác sĩ

room-service: Quản lý phòng điều trị

appointment-service (mở rộng): Quản lý lịch khám

api-gateway (mở rộng): Cổng giao tiếp tập trung

discovery-server (Eureka) (mở rộng): Phát hiện dịch vụ

🛠️ Công nghệ sử dụng
Thành phần	Công nghệ
Backend	Java, Spring Boot
Giao tiếp	RESTful API
Cơ sở dữ liệu	MySQL / PostgreSQL
ORM	JPA / Hibernate
Build tool	Maven
Container	Docker, Docker Compose
Quản lý dịch vụ	Eureka (optional)

🚀 Hướng dẫn chạy dự án
1️⃣ Clone repository
git clone https://github.com/your-username/hospital-microservices.git
cd hospital-microservices

2️⃣ Chạy từng service (local)
cd patient-service
mvn spring-boot:run

3️⃣ Chạy toàn bộ bằng Docker
docker compose up -d --build

📚 Mục đích học tập

Bài tập / đồ án môn Ứng dụng phân tán / Kiến trúc phần mềm

Nghiên cứu kiến trúc Microservices

Rèn luyện kỹ năng làm việc nhóm và thiết kế hệ thống

