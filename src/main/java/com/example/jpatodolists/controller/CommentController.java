package com.example.jpatodolists.controller;

import com.example.jpatodolists.common.ApiResponse;
import com.example.jpatodolists.dto.comment.*;
import com.example.jpatodolists.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    /**
     * 댓글 생성 API
     */
    @PostMapping("/todo/{todoId}")
    public ResponseEntity<ApiResponse<CreateCommentResponseDto>> createComment(
            @PathVariable Long todoId,
            @RequestParam Long userId,
            @RequestBody CreateCommentRequestDtd requestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commentService.createComment(todoId, userId, requestDto.getContent()));
    }

    /**
     * 특정 Todo의 모든 댓글 조회 API
     */
    @GetMapping("/todo/{todoId}")
    public ResponseEntity<ApiResponse<List<GetCommentResponseDto>>> getAllComments(
            @PathVariable Long todoId) {
        return ResponseEntity.ok(commentService.findAllComments(todoId));
    }

    /**
     * 댓글 수정 API
     * 댓글 작성자만 수정 가능
     */
    @PatchMapping("/{commentId}")
    public ResponseEntity<ApiResponse<UpdateCommentResponseDto>> updateComment(
            @PathVariable Long commentId,
            @RequestParam Long userId,
            @RequestBody UpdateCommentRequestDto requestDto) {
        return ResponseEntity.ok(commentService.updateComment(
                commentId,
                requestDto.getContent(),
                userId));
    }

    /**
     * 댓글 삭제 API
     * 댓글 작성자만 삭제 가능
     */
    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(
            @PathVariable Long commentId,
            @RequestParam Long userId) {
        return ResponseEntity.ok(commentService.deleteComment(commentId, userId));
    }
}
