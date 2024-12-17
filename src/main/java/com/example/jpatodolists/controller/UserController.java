package com.example.jpatodolists.controller;

import com.example.jpatodolists.dto.*;
import com.example.jpatodolists.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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

    //유저 이메일, 유저명 업데이트
    @PatchMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateUser(@PathVariable Long id, @RequestBody UpdateUserRequestDto requestDto) {
        // 업데이트된 유저 정보를 반환받음
        UpdateUserResponseDto updatedUser = userService.updateUser(
                id,
                requestDto.getOldEmail(),
                requestDto.getNewEmail(),
                requestDto.getOldUsername(),
                requestDto.getNewUsername()
        );
        // 성공 메시지와 함께 업데이트된 유저 정보를 리스폰스에 담아 반환
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "SUCCESS");
        response.put("data", Map.of("updatedUser", updatedUser));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
