package com.example.jpatodolists.service;

import com.example.jpatodolists.config.PassWordEncoder;
import com.example.jpatodolists.entity.User;
import com.example.jpatodolists.exception.auth.LoginFailedException;
import com.example.jpatodolists.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PassWordEncoder passwordEncoder;

    public Long authenticate(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new LoginFailedException("이메일 또는 비밀번호가 일치하지 않습니다."));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new LoginFailedException("이메일 또는 비밀번호가 일치하지 않습니다.");
        }

        return user.getId();
    }
}