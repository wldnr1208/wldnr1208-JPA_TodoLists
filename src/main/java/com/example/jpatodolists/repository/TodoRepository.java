package com.example.jpatodolists.repository;

import com.example.jpatodolists.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findAllByUserId(Long userId); // UserId로 Todo 리스트 조회

}