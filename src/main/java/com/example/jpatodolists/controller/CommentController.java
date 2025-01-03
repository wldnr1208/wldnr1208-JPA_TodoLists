package com.example.jpatodolists.controller;

import com.example.jpatodolists.dto.comment.CreateCommentRequestDtd;
import com.example.jpatodolists.dto.comment.CreateCommentResponseDto;
import com.example.jpatodolists.dto.comment.UpdateCommentRequestDto;
import com.example.jpatodolists.dto.comment.UpdateCommentResponseDto;
import com.example.jpatodolists.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/todo/{todoId}")
    public ResponseEntity<CreateCommentResponseDto> createComment(
            @PathVariable Long todoId,
            @RequestParam Long userId,
            @RequestBody CreateCommentRequestDtd requestDto) {
        CreateCommentResponseDto responseDto = commentService.createComment(
                todoId,
                userId,
                requestDto.getContent()
        );
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping("/todo/{todoId}")
    public ResponseEntity<Map<String, Object>> getAllComments(@PathVariable Long todoId) {
        Map<String, Object> response = commentService.findAllComments(todoId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<UpdateCommentResponseDto> updateComment(
            @PathVariable Long commentId,
            @RequestBody UpdateCommentRequestDto requestDto) {
        UpdateCommentResponseDto responseDto = commentService.updateComment(
                commentId,
                requestDto.getContent()
        );
        // 성공 메시지와 함께 업데이트된 유저 정보를 리스폰스에 담아 반환
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "SUCCESS");
        response.put("data", Map.of("updatedComment", responseDto));

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
