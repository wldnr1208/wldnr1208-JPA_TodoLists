package com.example.jpatodolists.service;

import com.example.jpatodolists.dto.CreateTodoResponseDto;
import com.example.jpatodolists.dto.TodoResponseDto;
import com.example.jpatodolists.entity.Todo;
import com.example.jpatodolists.entity.User;
import com.example.jpatodolists.repository.TodoRepository;
import com.example.jpatodolists.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository; // UserRepository 필요


    public CreateTodoResponseDto createTodo(String content, String title, Long userId) {
        // User 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));

        Todo todo = new Todo(title, content, user);
        Todo savedTodo = todoRepository.save(todo);

        // Response DTO 반환
        return new CreateTodoResponseDto(
                savedTodo.getModifiedAt(),
                savedTodo.getCreatedAt(),
                savedTodo.getContent(),
                savedTodo.getTitle(),
                savedTodo.getId()
        );
    }

    public Map<String, Object> findAll(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));

        List<TodoResponseDto> todos = todoRepository.findAllByUserId(userId)
                .stream()
                .map(TodoResponseDto::toTodo)
                .toList();

        Map<String, Object> toDoAllResponse = new HashMap<>();
        toDoAllResponse.put("code", 200);
        toDoAllResponse.put("message", "SUCCESS");
        toDoAllResponse.put("todoList", todos);
        toDoAllResponse.put("username", user.getUsername()); // username 추가

        return toDoAllResponse;
    }
}
