package com.example.jpatodolists.controller;

import com.example.jpatodolists.dto.CreateTodoRequestDto;
import com.example.jpatodolists.dto.CreateTodoResponseDto;
import com.example.jpatodolists.dto.UpdateTodoRequestDto;
import com.example.jpatodolists.dto.UpdateTodoResponseDto;
import com.example.jpatodolists.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/todo")
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<CreateTodoResponseDto> createTodo(@RequestBody CreateTodoRequestDto requestDto,
                                                            @RequestParam Long userId) {
        CreateTodoResponseDto responseDto =
                todoService.createTodo(requestDto.getContent(), requestDto.getTitle(), userId);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<Map<String, Object>> findAll(@RequestParam Long userId) {
        Map<String, Object> todoAllResponse = todoService.findAll(userId);
        return new ResponseEntity<>(todoAllResponse, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateTodo(@PathVariable Long id, @RequestBody UpdateTodoRequestDto requestDto) {

        UpdateTodoResponseDto updateTodo = todoService.updateTodo(
                id,
                requestDto.getNewTitle(),
                requestDto.getNewContent()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "SUCCESS");
        response.put("data", Map.of("updatedUser", updateTodo));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        todoService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
