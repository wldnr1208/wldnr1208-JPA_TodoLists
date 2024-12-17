package com.example.jpatodolists.controller;


import com.example.jpatodolists.dto.LoginRequestDto;
import com.example.jpatodolists.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequestDto requestDto,
                                                     HttpServletRequest request) {
        // Authenticate user
        Long userId = authService.authenticate(requestDto.getEmail(), requestDto.getPassword());

        // Create session
        HttpSession session = request.getSession(true);
        session.setAttribute("userId", userId);

        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "Login successful");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
