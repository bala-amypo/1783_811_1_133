package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
    private String role;

    public User() {}

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {   // ✅ REQUIRED
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {        // ✅ REQUIRED
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
