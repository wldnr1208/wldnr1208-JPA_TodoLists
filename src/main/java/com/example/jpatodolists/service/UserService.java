package com.example.jpatodolists.service;


import com.example.jpatodolists.config.PassWordEncoder;
import com.example.jpatodolists.dto.UpdateUserResponseDto;
import com.example.jpatodolists.dto.UserCreateResponseDto;
import com.example.jpatodolists.dto.UserResponseDto;
import com.example.jpatodolists.entity.Todo;
import com.example.jpatodolists.entity.User;
import com.example.jpatodolists.repository.TodoRepository;
import com.example.jpatodolists.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final TodoRepository todoRepository;
    private final PassWordEncoder passwordEncoder; // 추가

    public UserCreateResponseDto signUp(String username, String password, String email) {  // 순서 변경
        // 이메일 중복 체크
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("이미 사용중인 이메일입니다.");
        }

        String encodedPassword = passwordEncoder.encode(password);

        // User 생성자 순서: username, encodedPassword, email
        User user = new User(username, encodedPassword, email);
        User savedUser = userRepository.save(user);

        return new UserCreateResponseDto(
                savedUser.getId(),
                savedUser.getUsername(),  // 유저명
                savedUser.getEmail(),     // 이메일
                "[PROTECTED]",            // 비밀번호
                savedUser.getCreatedAt()
        );
    }
    public Map<String, Object> findAll() {
        // 소프트 딜리트되지 않은 유저만 조회
        List<UserResponseDto> users = userRepository.findAllByIsDeletedFalse()
                .stream()
                .map(UserResponseDto::toUser)
                .toList();

        //"userList"
        Map<String, Object> usrAllResponse = new HashMap<>();
        usrAllResponse.put("code", 200);
        usrAllResponse.put("message", "SUCCESS");
        usrAllResponse.put("userList", users);

        return usrAllResponse;
    }


    public Map<String, Object> findById(Long id) {
        User user = userRepository.findByIdOrElseThrow(id);
        UserResponseDto userResponseDto = new UserResponseDto(user.getUsername(), user.getCreatedAt(), user.getEmail());

        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "SUCCESS");
        response.put("data", Map.of(
                "userDetail", userResponseDto
        ));
        return response;
    }

    @Transactional
    public UpdateUserResponseDto updateUser(Long id, String oldEmail, String newEmail, String oldUsername, String newUsername) {
        // 데이터베이스에서 유저를 찾음
        User foundUser = userRepository.findByIdOrElseThrow(id);

        // 입력된 이전 이메일과 유저명이 실제 데이터와 다를 경우 예외 처리
        if (!foundUser.getEmail().equals(oldEmail) || !foundUser.getUsername().equals(oldUsername)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Required value is missing.");
        }

        // 유저 정보 업데이트
        foundUser.updateUser(newEmail, newUsername);

        // 업데이트된 유저 정보를 리턴하기 위해 DTO로 변환
        return new UpdateUserResponseDto(foundUser.getUsername(), foundUser.getCreatedAt(), foundUser.getEmail());
    }

    @Transactional
    public void softDelete(Long id) {
        // User 조회
        User foundUser = userRepository.findByIdOrElseThrow(id);

        // User 소프트 딜리트 적용
        foundUser.setIsDeleted(true);

        // User와 관련된 Todo의 소프트 딜리트 적용
        List<Todo> userTodos = todoRepository.findAllByUserId(id); // Todo 조회
        for (Todo todo : userTodos) {
            todo.setIsDeleted(true);
        }
    }
}
