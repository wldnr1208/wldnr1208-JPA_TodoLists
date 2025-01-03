package com.example.jpatodolists.controller;


import com.example.jpatodolists.common.ApiResponse;
import com.example.jpatodolists.dto.user.LoginRequestDto;
import com.example.jpatodolists.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@Tag(name = "User", description = "Auth 관련 API")
@RequestMapping("/user")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Void>> login(@RequestBody LoginRequestDto requestDto,
                                                   HttpServletRequest request) {
        Long userId = authService.authenticate(requestDto.getEmail(), requestDto.getPassword());

        HttpSession session = request.getSession(true);
        session.setAttribute("userId", userId);

        return ResponseEntity.ok(ApiResponse.success("로그인에 성공했습니다."));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity.ok(ApiResponse.success("로그아웃에 성공했습니다."));
    }
}
