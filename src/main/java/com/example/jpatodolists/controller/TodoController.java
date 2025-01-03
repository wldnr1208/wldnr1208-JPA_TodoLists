package com.example.jpatodolists.controller;

import com.example.jpatodolists.common.ApiResponse;
import com.example.jpatodolists.dto.todo.CreateTodoRequestDto;
import com.example.jpatodolists.dto.comment.UpdateCommentRequestDto;
import com.example.jpatodolists.dto.todo.UpdateTodoRequestDto;
import com.example.jpatodolists.dto.todo.UpdateTodoResponseDto;
import com.example.jpatodolists.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@RestController
@RequiredArgsConstructor
@RequestMapping("/todo")
public class TodoController {
    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<ApiResponse<UpdateCommentRequestDto.CreateTodoResponseDto>> createTodo(
            @RequestBody CreateTodoRequestDto requestDto,
            @RequestParam Long userId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(todoService.createTodo(requestDto.getContent(), requestDto.getTitle(), userId));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Map<String, Object>>> findAll(@RequestParam Long userId) {
        return ResponseEntity.ok(todoService.findAll(userId));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<UpdateTodoResponseDto>> updateTodo(
            @PathVariable Long id,
            @RequestBody UpdateTodoRequestDto requestDto) {
        return ResponseEntity.ok(todoService.updateTodo(
                id,
                requestDto.getNewTitle(),
                requestDto.getNewContent()
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(todoService.delete(id));
    }

    @GetMapping("/paging")
    public ResponseEntity<ApiResponse<Map<String, Object>>> findAllWithPaging(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(todoService.findAllWithPaging(userId, page, size));
    }
}
