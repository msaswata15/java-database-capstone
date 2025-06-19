package com.project.back_end.controller;

import com.project.back_end.models.Admin;
import com.project.back_end.service.ServiceClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("${api.path}admin")
public class AdminController {

    private final ServiceClass service;

    @Autowired
    public AdminController(ServiceClass service) {
        this.service = service;
    }

    /**
     * ✅ Admin Login Endpoint
     * @param admin Admin credentials from request body
     * @return ResponseEntity with JWT token if valid, error message otherwise
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> adminLogin(@RequestBody Admin admin) {
        return service.validateAdmin(admin);
    }

}
