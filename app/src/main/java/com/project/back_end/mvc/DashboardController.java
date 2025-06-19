package com.project.back_end.controller;

import com.project.back_end.service.TokenValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@Controller
public class DashboardController {

    @Autowired
    private TokenValidationService tokenValidationService;

    @GetMapping("/adminDashboard/{token}")
    public String adminDashboard(@PathVariable String token) {
        Map<String, Object> validationResult = tokenValidationService.validateToken(token, "admin");
        if (validationResult.isEmpty()) {
            return "admin/adminDashboard"; // Thymeleaf will resolve to templates/admin/adminDashboard.html
        } else {
            return "redirect:/"; // Redirects to index.html or your login page
        }
    }

    @GetMapping("/doctorDashboard/{token}")
    public String doctorDashboard(@PathVariable String token) {
        Map<String, Object> validationResult = tokenValidationService.validateToken(token, "doctor");
        if (validationResult.isEmpty()) {
            return "doctor/doctorDashboard"; // templates/doctor/doctorDashboard.html
        } else {
            return "redirect:/";
        }
    }

}
