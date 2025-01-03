package com.example.jpatodolists.controller;

import com.example.jpatodolists.common.ApiResponse;
import com.example.jpatodolists.dto.user.*;
import com.example.jpatodolists.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    /**
     * 회원 가입 API
     * 201 Created 상태코드 반환으로 리소스 생성을 명확히 표현
     */
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<UserCreateResponseDto>> signUp(@RequestBody UserCreateRequestDto requestDto) {
        UserCreateResponseDto response = userService.signUp(
                requestDto.getUsername(),
                requestDto.getPassword(),
                requestDto.getEmail()
        );
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("회원가입 성공", response));
    }

    /**
     * 전체 사용자 조회 API
     * 컨트롤러는 요청/응답 변환과 전달만 담당
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponseDto>>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    /**
     * 단일 사용자 조회 API
     * 경로 변수를 통해 RESTful한 URL 구조 구현
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDto>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    /**
     * 사용자 정보 수정 API
     * PATCH 메서드로 부분 수정을 명시적으로 표현
     */
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<UpdateUserResponseDto>> updateUser(
            @PathVariable Long id,
            @RequestBody UpdateUserRequestDto requestDto) {
        return ResponseEntity.ok(userService.updateUser(
                id,
                requestDto.getOldEmail(),
                requestDto.getNewEmail(),
                requestDto.getOldUsername(),
                requestDto.getNewUsername()
        ));
    }

    /**
     * 사용자 삭제 API
     * 실제로는 소프트 삭제를 수행하지만 클라이언트에게는 추상화된 인터페이스 제공
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(userService.softDelete(id));
    }
}