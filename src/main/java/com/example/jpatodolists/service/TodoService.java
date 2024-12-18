package com.example.jpatodolists.service;

import com.example.jpatodolists.dto.CreateTodoResponseDto;
import com.example.jpatodolists.dto.TodoPageResponseDto;
import com.example.jpatodolists.dto.TodoResponseDto;
import com.example.jpatodolists.dto.UpdateTodoResponseDto;
import com.example.jpatodolists.entity.Todo;
import com.example.jpatodolists.entity.User;
import com.example.jpatodolists.repository.TodoRepository;
import com.example.jpatodolists.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    //페이징용 getService
    public Map<String, Object> findAllWithPaging(Long userId, int page, int size) {
        // 페이지 요청 객체 생성 (수정일 기준 내림차순)
        Pageable pageable = PageRequest.of(page, size, Sort.by("modifiedAt").descending());

        // 유저 존재 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));

        // 페이징된 할일 목록 조회
        Page<Todo> todoPage = todoRepository.findAllByUserIdWithPaging(userId, pageable);

        // TodoPageResponseDto로 변환
        Page<TodoPageResponseDto> responseDtoPage = todoPage.map(todo -> {
            Long commentCount = todoRepository.countCommentsByTodoId(todo.getId());
            return new TodoPageResponseDto(todo, commentCount);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "SUCCESS");
        response.put("data", Map.of(
                "content", responseDtoPage.getContent(),
                "currentPage", responseDtoPage.getNumber(),
                "totalPages", responseDtoPage.getTotalPages(),
                "totalElements", responseDtoPage.getTotalElements(),
                "size", responseDtoPage.getSize()
        ));

        return response;
    }
}
