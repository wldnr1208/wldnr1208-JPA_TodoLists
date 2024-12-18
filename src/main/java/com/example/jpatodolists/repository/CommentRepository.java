package com.example.jpatodolists.repository;

import com.example.jpatodolists.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByTodoIdAndIsDeletedFalse(Long todoId);
    default Comment findByIdOrElseThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));
    }
}
