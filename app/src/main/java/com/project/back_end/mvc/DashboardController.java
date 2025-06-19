package com.project.back_end.mvc;

import com.project.back_end.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class DashboardController {

    @Autowired
    private TokenService tokenValidationService;

    @GetMapping("/adminDashboard/{token}")
    public String adminDashboard(@PathVariable String token) {
        if (tokenValidationService.validateToken(token, "admin")) {
            return "admin/adminDashboard"; // Thymeleaf will resolve to templates/admin/adminDashboard.html
        } else {
            return "redirect:/"; // Redirects to index.html or your login page
        }
    }

    @GetMapping("/doctorDashboard/{token}")
    public String doctorDashboard(@PathVariable String token) {
        if (tokenValidationService.validateToken(token, "doctor")) {
            return "doctor/doctorDashboard"; // templates/doctor/doctorDashboard.html
        } else {
            return "redirect:/";
        }
    }

}
