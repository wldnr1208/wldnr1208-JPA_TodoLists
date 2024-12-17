package com.example.jpatodolists.repository;

import com.example.jpatodolists.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findAllByUserId(Long userId); // UserId로 Todo 리스트 조회

    default Todo findByOrElseThrow(Long id){
        return findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,"Does not exist id = " + id));
    }
}