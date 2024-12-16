package com.example.jpatodolists.service;


import com.example.jpatodolists.dto.UserCreateResponseDto;
import com.example.jpatodolists.entity.User;
import com.example.jpatodolists.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;


    public UserCreateResponseDto signUp(String password,String email, String username) {
        User user = new User(username,password, email);

        User savedUser = userRepository.save(user);

        return new UserCreateResponseDto(savedUser.getId(), savedUser.getUsername(), savedUser.getEmail(), savedUser.getPassword(), savedUser.getCreatedAt());

    }
}
