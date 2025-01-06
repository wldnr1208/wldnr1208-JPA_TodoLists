package com.example.jpatodolists.service;

import com.example.jpatodolists.common.ApiResponse;
import com.example.jpatodolists.dto.comment.CreateCommentResponseDto;
import com.example.jpatodolists.dto.comment.GetCommentResponseDto;
import com.example.jpatodolists.dto.comment.UpdateCommentResponseDto;
import com.example.jpatodolists.entity.Comment;
import com.example.jpatodolists.entity.Todo;
import com.example.jpatodolists.entity.User;
import com.example.jpatodolists.exception.auth.UnauthorizedException;
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

    /**
     * 댓글 생성
     * 사용자와 Todo 존재 여부 확인 후 댓글 생성
     */
    @Transactional
    public ApiResponse<CreateCommentResponseDto> createComment(Long todoId, Long userId, String content) {
        User user = userRepository.findByIdOrElseThrow(userId);
        Todo todo = todoRepository.findByOrElseThrow(todoId);

        Comment comment = new Comment(content, user, todo);
        Comment savedComment = commentRepository.save(comment);

        return ApiResponse.success("댓글이 생성되었습니다", new CreateCommentResponseDto(savedComment));
    }

    /**
     * Todo에 달린 모든 댓글 조회
     * 소프트 삭제되지 않은 댓글만 조회
     */
    public ApiResponse<List<GetCommentResponseDto>> findAllComments(Long todoId) {
        // Todo 존재 여부 확인
        todoRepository.findByOrElseThrow(todoId);

        List<GetCommentResponseDto> comments = commentRepository.findAllByTodoIdAndIsDeletedFalse(todoId)
                .stream()
                .map(GetCommentResponseDto::from)
                .toList();

        return ApiResponse.success("댓글 목록을 조회했습니다", comments);
    }

    /**
     * 댓글 수정
     * 댓글 작성자 본인만 수정 가능
     */
    @Transactional
    public ApiResponse<UpdateCommentResponseDto> updateComment(Long commentId, String newContent, Long userId) {
        Comment comment = commentRepository.findByIdOrElseThrow(commentId);

        // 댓글 작성자 검증
        validateCommentOwner(comment, userId);

        comment.update(newContent);
        return ApiResponse.success("댓글이 수정되었습니다", new UpdateCommentResponseDto(comment));
    }

    /**
     * 댓글 삭제
     * 댓글 작성자 본인만 삭제 가능
     */
    @Transactional
    public ApiResponse<Void> deleteComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findByIdOrElseThrow(commentId);

        // 댓글 작성자 검증
        validateCommentOwner(comment, userId);

        comment.setIsDeleted(true);
        return ApiResponse.success("댓글이 삭제되었습니다");
    }

    /**
     * 댓글 작성자 검증
     */
    private void validateCommentOwner(Comment comment, Long userId) {
        if (!comment.getUser().getId().equals(userId)) {
            throw new UnauthorizedException("댓글에 대한 권한이 없습니다.");
        }
    }
}
