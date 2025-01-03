package com.example.jpatodolists.repository;

import com.example.jpatodolists.entity.Todo;
import com.example.jpatodolists.exception.common.TodoNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    /**
     * 특정 사용자의 모든 Todo 목록 조회
     */
    List<Todo> findAllByUserId(Long userId);

    /**
     * ID로 Todo를 조회하거나 없으면 예외를 던짐
     * ResponseStatusException 대신 커스텀 예외 사용
     */
    default Todo findByOrElseThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> new TodoNotFoundException("해당 할 일을 찾을 수 없습니다. id = " + id));
    }

    /**
     * 페이징을 위한 Todo 목록 조회
     * 삭제되지 않은 Todo만 조회하며, N+1 문제 방지를 위해 fetch join 사용
     */
    @Query("""
            SELECT t FROM Todo t
            JOIN FETCH t.user
            WHERE t.isDeleted = false
            AND t.user.id = :userId
            """)
    Page<Todo> findAllByUserIdWithPaging(@Param("userId") Long userId, Pageable pageable);

    /**
     * 특정 Todo의 활성화된 댓글 개수 조회
     */
    @Query("""
            SELECT COUNT(c)
            FROM Comment c
            WHERE c.todo.id = :todoId
            AND c.isDeleted = false
            """)
    Long countCommentsByTodoId(@Param("todoId") Long todoId);
}