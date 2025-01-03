package com.example.jpatodolists.service;

import com.example.jpatodolists.dto.comment.CreateCommentResponseDto;
import com.example.jpatodolists.dto.comment.GetCommentResponseDto;
import com.example.jpatodolists.dto.comment.UpdateCommentResponseDto;
import com.example.jpatodolists.entity.Comment;
import com.example.jpatodolists.entity.Todo;
import com.example.jpatodolists.entity.User;
import com.example.jpatodolists.repository.CommentRepository;
import com.example.jpatodolists.repository.TodoRepository;
import com.example.jpatodolists.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final TodoRepository todoRepository;

    public CreateCommentResponseDto createComment(Long todoId, Long userId, String content) {
        User user = userRepository.findByIdOrElseThrow(userId);
        Todo todo = todoRepository.findByOrElseThrow(todoId);

        Comment comment = new Comment(content, user, todo);
        Comment savedComment = commentRepository.save(comment);

        return new CreateCommentResponseDto(savedComment);
    }

    public Map<String, Object> findAllComments(Long todoId) {
        List<GetCommentResponseDto> comments = commentRepository.findAllByTodoIdAndIsDeletedFalse(todoId)
                .stream()
                .map(GetCommentResponseDto::from)
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "SUCCESS");
        response.put("comments", comments);

        return response;
    }

    @Transactional
    public UpdateCommentResponseDto updateComment(Long commentId, String newContent) {
        Comment comment = commentRepository.findByIdOrElseThrow(commentId);
        comment.update(newContent);
        return new UpdateCommentResponseDto(comment);
    }

    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findByIdOrElseThrow(commentId);
        comment.setIsDeleted(true);
    }
}
