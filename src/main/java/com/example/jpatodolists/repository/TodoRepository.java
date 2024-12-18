package com.example.jpatodolists.repository;

import com.example.jpatodolists.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
    // 페이징을 위한 새로운 메서드 추가
    @Query("SELECT t FROM Todo t " +
            "JOIN FETCH t.user " +
            "WHERE t.isDeleted = false " +
            "AND t.user.id = :userId")
    Page<Todo> findAllByUserIdWithPaging(@Param("userId") Long userId, Pageable pageable);

    // 댓글 개수 조회 메서드 추가
    @Query("SELECT COUNT(c) FROM Comment c WHERE c.todo.id = :todoId AND c.isDeleted = false")
    Long countCommentsByTodoId(@Param("todoId") Long todoId);
}