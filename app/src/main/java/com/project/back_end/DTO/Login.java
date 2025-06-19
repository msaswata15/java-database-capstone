package com.project.back_end.dto;

public class Login {

    private String email;
    private String password;

    // ✅ No-args constructor (good practice for Spring deserialization)
    public Login() {}

    // ✅ All-args constructor (optional)
    public Login(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // ✅ Getters & Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
