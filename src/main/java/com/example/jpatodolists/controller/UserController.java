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
    
    //유저 생성
    @PostMapping("/signup")
    public ResponseEntity<UserCreateResponseDto> signUp(@RequestBody UserCreateRequestDto requestDto) {
        UserCreateResponseDto userResponseDto = userService.signUp(requestDto.getEmail(), requestDto.getUsername(), requestDto.getPassword());
        return new ResponseEntity<>(userResponseDto, HttpStatus.CREATED);
    }
    
    //유저 전체조회
    @GetMapping
    public ResponseEntity<Map<String, Object>> findAll() {
        Map<String, Object> usrAllResponse = userService.findAll();
        return new ResponseEntity<>(usrAllResponse, HttpStatus.OK);
    }
    
    //유저 상세조회
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> findById(@PathVariable Long id) {
        Map<String, Object> response = userService.findById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
