package com.example.jpatodolists.controller;

import com.example.jpatodolists.dto.UserCreateRequestDto;
import com.example.jpatodolists.dto.UserCreateResponseDto;
import com.example.jpatodolists.dto.UserResponseDto;
import com.example.jpatodolists.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserCreateResponseDto> signUp(@RequestBody UserCreateRequestDto requestDto) {
        UserCreateResponseDto userResponseDto = userService.signUp(requestDto.getEmail(), requestDto.getUsername(), requestDto.getPassword());
        return new ResponseEntity<>(userResponseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> findAll() {
        Map<String, Object> response = userService.findAll();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
