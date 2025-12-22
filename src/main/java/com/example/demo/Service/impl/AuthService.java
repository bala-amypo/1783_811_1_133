package com.example.demo.service;

import com.example.demo.dto.*;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public void register(RegisterRequestDto dto) {
        
    }

    public AuthResponseDto login(AuthRequestDto dto) {
        AuthResponseDto res = new AuthResponseDto();
        res.token = "dummy-token";
        res.expiresAt = System.currentTimeMillis() + 3600000;
        return res;
    }
}
