package com.hospital.appointment_service.model;

public enum Status {
    REGISTERED,   // 🟢 Bệnh nhân đã đăng ký khám
    SCHEDULED, 
    ONGOING,      // 🟠 Đang được khám / trong tiến trình khám
    COMPLETED,    // 🔵 Đã khám xong
    CANCELED      // 🔴 Đã hủy lịch hẹn
}
