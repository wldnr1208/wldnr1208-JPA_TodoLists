package com.example.jpatodolists.service;


import com.example.jpatodolists.common.ApiResponse;
import com.example.jpatodolists.config.PassWordEncoder;
import com.example.jpatodolists.dto.user.UpdateUserResponseDto;
import com.example.jpatodolists.dto.user.UserCreateResponseDto;
import com.example.jpatodolists.dto.user.UserResponseDto;
import com.example.jpatodolists.entity.Todo;
import com.example.jpatodolists.entity.User;
import com.example.jpatodolists.exception.validation.DuplicateEmailException;
import com.example.jpatodolists.exception.auth.WrongCredentialsException;
import com.example.jpatodolists.repository.TodoRepository;
import com.example.jpatodolists.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)  // 읽기 작업이 기본이므로 readOnly 설정, 성능 최적화
public class UserService {
    private final UserRepository userRepository;
    private final TodoRepository todoRepository;
    private final PassWordEncoder passwordEncoder;

    /**
     * 회원 가입을 처리하는 메서드
     * 엔티티에 검증 로직을 위임하고 서비스는 순수하게 플로우 제어만 담당
     */
    @Transactional
    public UserCreateResponseDto signUp(String username, String password, String email) {
        validateDuplicateEmail(email);
        User user = createUser(username, password, email);
        User savedUser = userRepository.save(user);
        return UserCreateResponseDto.from(savedUser);
    }

    /**
     * 전체 사용자 목록 조회
     */
    public ApiResponse<List<UserResponseDto>> findAll() {
        List<UserResponseDto> users = userRepository.findAllByIsDeletedFalse()
                .stream()
                .map(UserResponseDto::from)
                .toList();
        return ApiResponse.success("유저 목록 조회 성공", users);
    }

    /**
     * 단일 사용자 상세 조회
     */
    public ApiResponse<UserResponseDto> findById(Long id) {
        User user = findUserById(id);
        return ApiResponse.success("유저 상세 조회 성공", UserResponseDto.from(user));
    }

    /**
     * 사용자 정보 업데이트
     * 인증 검증을 엔티티에 위임
     */
    @Transactional
    public ApiResponse<UpdateUserResponseDto> updateUser(Long id, String oldEmail, String newEmail,
                                                         String oldUsername, String newUsername) {
        // 새로운 이메일이 현재 사용자의 이메일과 다르고, 다른 사용자가 사용 중인 경우 체크
        if (!oldEmail.equals(newEmail) && userRepository.existsByEmail(newEmail)) {
            throw new DuplicateEmailException("이미 사용중인 이메일입니다.");
        }

        User user = findUserById(id);
        user.validateCredentials(oldEmail, oldUsername);
        user.update(newEmail, newUsername);
        return ApiResponse.success("유저 정보 수정 성공", UpdateUserResponseDto.from(user));
    }
    /**
     * 사용자 소프트 삭제
     * 삭제 로직을 엔티티에 위임
     */
    @Transactional
    public ApiResponse<Void> softDelete(Long id) {
        User user = findUserById(id);
        List<Todo> userTodos = todoRepository.findAllByUserId(id);
        // 삭제 로직을 엔티티에 위임
        user.delete(userTodos);
        return ApiResponse.success("유저 삭제 성공");
    }

    // Private helper methods

    /**
     * 이메일 중복 검증
     */
    private void validateDuplicateEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new DuplicateEmailException("이미 사용중인 이메일입니다.");
        }
    }

    /**
     * 사용자 생성
     */
    private User createUser(String username, String password, String email) {
        String encodedPassword = passwordEncoder.encode(password);
        return new User(username, encodedPassword, email);
    }

    /**
     * 사용자 조회
     */
    private User findUserById(Long id) {
        return userRepository.findByIdOrElseThrow(id);
    }

}