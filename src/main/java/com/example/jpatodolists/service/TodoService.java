package com.example.jpatodolists.service;

import com.example.jpatodolists.dto.CreateTodoResponseDto;
import com.example.jpatodolists.dto.TodoResponseDto;
import com.example.jpatodolists.dto.UpdateTodoRequestDto;
import com.example.jpatodolists.dto.UpdateTodoResponseDto;
import com.example.jpatodolists.entity.Todo;
import com.example.jpatodolists.entity.User;
import com.example.jpatodolists.repository.TodoRepository;
import com.example.jpatodolists.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

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

    @Transactional
    public UpdateTodoResponseDto updateTodo(Long id, String newTitle, String newContent) {
        // TodoRepository의 findByOrElseThrow 메서드를 호출하여 Todo 조회
        Todo foundTodo = todoRepository.findByOrElseThrow(id);

        // 입력값이 없는 경우 기존 값 유지
        String updatedTitle = (newTitle != null && !newTitle.isBlank()) ? newTitle : foundTodo.getTitle();
        String updatedContent = (newContent != null && !newContent.isBlank()) ? newContent : foundTodo.getContent();

        // 업데이트 수행
        foundTodo.update(updatedTitle, updatedContent);

        // 응답 DTO 반환
        return new UpdateTodoResponseDto(
                foundTodo.getId(),
                foundTodo.getTitle(),
                foundTodo.getContent(),
                foundTodo.getModifiedAt()
        );
    }

    public void delete(Long id) {
        Todo foundTodoId = todoRepository.findByOrElseThrow(id);
        todoRepository.delete(foundTodoId);
    }
}
