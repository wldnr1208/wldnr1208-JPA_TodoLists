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
     * SRP(단일 책임 원칙)에 따라 검증, 생성, 저장 로직을 분리
     */
    @Transactional  // 쓰기 작업이므로 readOnly = false로 오버라이드
    public UserCreateResponseDto signUp(String username, String password, String email) {
        validateEmail(email);  // 이메일 중복 검증 분리
        User user = createUser(username, password, email);  // 사용자 생성 로직 분리
        User savedUser = userRepository.save(user);
        return UserCreateResponseDto.from(savedUser);  // DTO 변환 책임을 DTO 클래스에 위임
    }

    /**
     * 전체 사용자 목록 조회
     * 스트림 API를 사용하여 선언적 프로그래밍 구현
     */
    public ApiResponse<List<UserResponseDto>> findAll() {
        List<UserResponseDto> users = userRepository.findAllByIsDeletedFalse()
                .stream()
                .map(UserResponseDto::from)  // 메서드 레퍼런스를 사용하여 가독성 향상
                .toList();
        return ApiResponse.success("유저 목록 조회 성공", users);
    }

    /**
     * 단일 사용자 상세 조회
     * 결과값 없음을 대비한 예외 처리를 포함
     */
    public ApiResponse<UserResponseDto> findById(Long id) {
        User user = findUserById(id);  // 공통 조회 로직 재사용
        return ApiResponse.success("유저 상세 조회 성공", UserResponseDto.from(user));
    }

    /**
     * 사용자 정보 업데이트
     * 트랜잭션 안에서 영속성 컨텍스트의 변경 감지 기능 활용
     */
    @Transactional
    public ApiResponse<UpdateUserResponseDto> updateUser(Long id, String oldEmail, String newEmail,
                                                         String oldUsername, String newUsername) {
        User user = findUserById(id);
        validateUserCredentials(user, oldEmail, oldUsername);
        user.update(newEmail, newUsername);  // 도메인 로직을 엔티티로 위임
        return ApiResponse.success("유저 정보 수정 성공", UpdateUserResponseDto.from(user));
    }

    /**
     * 사용자 소프트 삭제
     * 연관된 Todo 항목들도 함께 처리
     */
    @Transactional
    public ApiResponse<Void> softDelete(Long id) {
        User user = findUserById(id);
        List<Todo> userTodos = todoRepository.findAllByUserId(id);
        user.delete(userTodos);  // 도메인 로직을 엔티티로 위임
        return ApiResponse.success("유저 삭제 성공");
    }
    // Private helper methods - 내부 구현을 캡슐화하고 코드 재사용성 향상

    /**
     * 이메일 중복 검증
     * 명확한 커스텀 예외를 발생시켜 의미 전달 향상
     */
    private void validateEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new DuplicateEmailException("이미 사용중인 이메일입니다.");
        }
    }

    /**
     * 사용자 생성 로직
     * 패스워드 암호화 등 생성 관련 로직을 한 곳에서 처리
     */
    private User createUser(String username, String password, String email) {
        String encodedPassword = passwordEncoder.encode(password);
        return new User(username, encodedPassword, email);
    }

    /**
     * 공통 사용자 조회 로직
     * DRY 원칙을 적용하여 중복 제거
     */
    private User findUserById(Long id) {
        return userRepository.findByIdOrElseThrow(id);
    }

    /**
     * 사용자 인증 정보 검증
     * 보안 관련 로직을 분리하여 관리
     */
    private void validateUserCredentials(User user, String email, String username) {
        if (!user.getEmail().equals(email) || !user.getUsername().equals(username)) {
            throw new WrongCredentialsException("인증 정보가 일치하지 않습니다.");
        }
    }

}