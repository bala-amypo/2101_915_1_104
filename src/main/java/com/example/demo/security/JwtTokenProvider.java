package com.example.demo.security;

import com.example.demo.model.UserAccount;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    private final JwtUtil jwtUtil;

    public JwtTokenProvider(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    public String getUsername(String token) {
        return jwtUtil.getEmailFromToken(token);
    }
}
