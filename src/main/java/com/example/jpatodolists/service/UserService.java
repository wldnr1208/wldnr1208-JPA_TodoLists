package com.example.jpatodolists.service;


import com.example.jpatodolists.dto.UpdateUserResponseDto;
import com.example.jpatodolists.dto.UserCreateResponseDto;
import com.example.jpatodolists.dto.UserResponseDto;
import com.example.jpatodolists.entity.User;
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

    public UserCreateResponseDto signUp(String password,String email, String username) {
        User user = new User(username,password, email);
        User savedUser = userRepository.save(user);
        return new UserCreateResponseDto(savedUser.getId(), savedUser.getUsername(), savedUser.getEmail(), savedUser.getPassword(), savedUser.getCreatedAt());

    }

    public Map<String, Object> findAll() {
        List<UserResponseDto> users = userRepository.findAll()
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

    public void delete(Long id) {
        User foundUser = userRepository.findByIdOrElseThrow(id);
        userRepository.delete(foundUser);
    }
}
