package com.example.jpatodolists.repository;

import com.example.jpatodolists.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    //소프트 딜리트 때 12번 줄 추가
    List<User> findAllByIsDeletedFalse();

    default User findByIdOrElseThrow(Long id) {
        return findById(id)
                .filter(user -> !user.isDeleted()) // 삭제된 데이터 제외
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));
    }

}
