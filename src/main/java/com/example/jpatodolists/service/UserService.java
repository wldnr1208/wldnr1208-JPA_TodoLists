package com.example.jpatodolists.service;


import com.example.jpatodolists.dto.UserCreateResponseDto;
import com.example.jpatodolists.dto.UserResponseDto;
import com.example.jpatodolists.entity.User;
import com.example.jpatodolists.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

        // Wrap in a map with "userList" key
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
}
