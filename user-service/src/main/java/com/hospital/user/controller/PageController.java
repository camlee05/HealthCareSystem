package com.hospital.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/login")
    public String login() {
        return "login"; // src/main/resources/templates/login.html
    }

    @GetMapping("/home")
    public String home() {
        return "Benhnhan/home"; // src/main/resources/templates/Benhnhan/home.html
    }

    @GetMapping("/khoa")
    public String khoa() {
        return "Benhnhan/khoa"; // src/main/resources/templates/Benhnhan/khoa.html
    }

    @GetMapping("/ngaygio")
    public String ngaygio() {
        return "Benhnhan/ngaygio";
    }

    @GetMapping("/thongtin")
    public String thongtin() {
        return "Benhnhan/thongtin";
    }

    @GetMapping("/xacnhan")
    public String xacnhan() {
        return "Benhnhan/xacnhan";
    }

    @GetMapping("/admin/home")
    public String adhome() {
        return "admin/admin";
    }

    @GetMapping("/admin/bacsi")
    public String bacsi() {
        return "admin/doctors";
    }

    @GetMapping("/admin/lichhen")
    public String lich() {
        return "admin/appointments";
    }

    @GetMapping("/admin/baocao")
    public String baocao() {
        return "admin/baocao";
    }

    @GetMapping("/admin/benhan")
    public String benhan() {
        return "admin/benhan";
    }

    @GetMapping("/admin/khoa")
    public String qlkhoa() {
        return "admin/departments";
    }

    @GetMapping("/admin/benhnhan")
    public String benhnhan() {
        return "admin/patients";
    }

    @GetMapping("/bacsi/home")
    public String bshome() {
        return "Bacsi/home";
    }

    @GetMapping("/bacsi/addbenhan")
    public String addbenhan() {
        return "Bacsi/ba_moi";
    }

    @GetMapping("/bacsi/lichkham")
    public String lichkham() {
        return "Bacsi/lichkham";
    }

    @GetMapping("/bacsi/thongtin")
    public String thongtinbn() {
        return "Bacsi/thongtin";
    }
}

