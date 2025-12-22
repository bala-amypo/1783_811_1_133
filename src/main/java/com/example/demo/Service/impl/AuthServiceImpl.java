package com.example.demo.service.impl;

import com.example.demo.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Override
    public boolean authenticate(String username, String password) {
        // Basic dummy implementation
        return "admin".equals(username) && "password".equals(password);
    }
}
