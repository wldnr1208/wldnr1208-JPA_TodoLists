package com.example.jpatodolists.exception;

import com.example.jpatodolists.common.ApiResponse;
import com.example.jpatodolists.exception.auth.ForbiddenException;
import com.example.jpatodolists.exception.auth.LoginFailedException;
import com.example.jpatodolists.exception.auth.UnauthorizedException;
import com.example.jpatodolists.exception.auth.WrongCredentialsException;
import com.example.jpatodolists.exception.common.NotFoundException;
import com.example.jpatodolists.exception.common.TodoNotFoundException;
import com.example.jpatodolists.exception.common.UserNotFoundException;
import com.example.jpatodolists.exception.validation.DuplicateEmailException;
import com.example.jpatodolists.exception.validation.DuplicateUsernameException;
import com.example.jpatodolists.exception.validation.WrongAccessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    //    과정 중 발생하는 예외는 GlobalExceptionHandler가 처리합니다
    //    모든 응답은 ApiResponse 형식으로 통일되어 반환됩니다

    // 사용자명 중복 예외 처리
    @ExceptionHandler(DuplicateUsernameException.class)
    public ResponseEntity<ApiResponse<Void>> handleDuplicateUsername(DuplicateUsernameException e) {
        return ResponseEntity.badRequest()
                .body(ApiResponse.error(e.getMessage()));
    }

    // 이메일 중복 예외 처리
    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ApiResponse<Void>> handleDuplicateEmail(DuplicateEmailException e) {
        return ResponseEntity.badRequest()
                .body(ApiResponse.error(e.getMessage()));
    }

    // 인증이 필요한 접근
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiResponse<Void>> handleUnauthorized(UnauthorizedException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error(e.getMessage()));
    }

    //유저로그인 에러반환
    @ExceptionHandler(LoginFailedException.class)
    public ResponseEntity<ApiResponse<Void>> handleLoginFailed(LoginFailedException e) {
        return ResponseEntity.badRequest()
                .body(ApiResponse.error(e.getMessage()));
    }

    // ForbiddenException 핸들러 추가
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ApiResponse<Void>> handleForbidden(ForbiddenException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ApiResponse.error(e.getMessage()));
    }

    //데이터베이스에서 찾을수 없는 예외
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleUserNotFound(NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(e.getMessage()));
    }

    // 사용자를 찾을 수 없는 예외
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleUserNotFound(UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(e.getMessage()));
    }

    // 잘못된 접근 예외 처리
    @ExceptionHandler(WrongAccessException.class)
    public ResponseEntity<ApiResponse<Void>> handleWrongAccess(WrongAccessException e) {
        return ResponseEntity.badRequest()
                .body(ApiResponse.error(e.getMessage()));
    }

    // 잘못된 인증 정보 예외 처리 추가
    @ExceptionHandler(WrongCredentialsException.class)
    public ResponseEntity<ApiResponse<Void>> handleWrongCredentials(WrongCredentialsException e) {
        return ResponseEntity.badRequest()
                .body(ApiResponse.error(e.getMessage()));
    }

    // 사용자를 찾을 수 없는 예외
    @ExceptionHandler(TodoNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleUserNotFound(TodoNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(e.getMessage()));
    }
}