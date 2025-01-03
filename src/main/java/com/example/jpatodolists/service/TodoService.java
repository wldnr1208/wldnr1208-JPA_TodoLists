package com.example.jpatodolists.service;

import com.example.jpatodolists.common.ApiResponse;
import com.example.jpatodolists.dto.comment.UpdateCommentRequestDto;
import com.example.jpatodolists.dto.todo.TodoPageResponseDto;
import com.example.jpatodolists.dto.todo.TodoResponseDto;
import com.example.jpatodolists.dto.todo.UpdateTodoResponseDto;
import com.example.jpatodolists.entity.Todo;
import com.example.jpatodolists.entity.User;
import com.example.jpatodolists.exception.common.UserNotFoundException;
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
@Transactional(readOnly = true)  // 읽기 작업이 더 많은 서비스이므로 기본값을 readOnly로 설정하여 성능 최적화
public class TodoService {
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    /**
     * 새로운 Todo를 생성합니다.
     *
     * @param content Todo의 상세 내용
     * @param title Todo의 제목
     * @param userId Todo를 생성하는 사용자의 ID
     * @return 생성된 Todo의 정보를 포함한 응답 객체
     *
     * 프로세스:
     * 1. 사용자 ID로 사용자 존재 여부 확인
     * 2. 새로운 Todo 엔티티 생성
     * 3. DB에 Todo 저장
     * 4. 생성된 Todo 정보를 응답 DTO로 변환하여 반환
     */
    @Transactional  // 쓰기 작업이므로 readOnly = false로 오버라이드
    public ApiResponse<UpdateCommentRequestDto.CreateTodoResponseDto> createTodo(String content, String title, Long userId) {
        User user = findUserById(userId);  // 사용자 조회 및 존재 여부 확인
        Todo todo = createNewTodo(title, content, user);  // 새로운 Todo 엔티티 생성
        Todo savedTodo = todoRepository.save(todo);  // DB에 저장

        // 응답 DTO 생성 및 반환
        return ApiResponse.success("할 일이 생성되었습니다",
                new UpdateCommentRequestDto.CreateTodoResponseDto(
                        savedTodo.getModifiedAt(),
                        savedTodo.getCreatedAt(),
                        savedTodo.getContent(),
                        savedTodo.getTitle(),
                        savedTodo.getId()
                ));
    }

    /**
     * 특정 사용자의 모든 Todo 목록을 조회합니다.
     *
     * @param userId 조회할 사용자의 ID
     * @return 사용자의 Todo 목록과 사용자 정보를 포함한 응답 객체
     *
     * 프로세스:
     * 1. 사용자 존재 여부 확인
     * 2. 사용자의 Todo 목록 조회
     * 3. Todo 엔티티들을 DTO로 변환
     * 4. 응답 데이터 구성 및 반환
     */
    public ApiResponse<Map<String, Object>> findAll(Long userId) {
        User user = findUserById(userId);  // 사용자 조회
        List<TodoResponseDto> todos = getTodoListByUser(userId);  // Todo 목록 조회 및 DTO 변환

        // 응답 데이터 구성
        Map<String, Object> responseData = Map.of(
                "todoList", todos,
                "username", user.getUsername()
        );

        return ApiResponse.success("할 일 목록을 성공적으로 조회했습니다", responseData);
    }

    /**
     * 특정 Todo의 내용을 수정합니다.
     *
     * @param id 수정할 Todo의 ID
     * @param newTitle 새로운 제목 (null 가능)
     * @param newContent 새로운 내용 (null 가능)
     * @return 수정된 Todo 정보를 포함한 응답 객체
     *
     * 프로세스:
     * 1. Todo 존재 여부 확인
     * 2. 제목과 내용 업데이트 (null이나 빈 문자열인 경우 기존 값 유지)
     * 3. 변경 감지(Dirty Checking)를 통한 자동 업데이트
     */
    @Transactional
    public ApiResponse<UpdateTodoResponseDto> updateTodo(Long id, String newTitle, String newContent) {
        Todo todo = findTodoById(id);  // Todo 조회
        updateTodoContent(todo, newTitle, newContent);  // 내용 업데이트
        return ApiResponse.success("할 일이 수정되었습니다",
                new UpdateTodoResponseDto(todo.getId(), todo.getTitle(), todo.getContent(), todo.getModifiedAt()));
    }

    /**
     * Todo를 삭제합니다.
     *
     * @param id 삭제할 Todo의 ID
     * @return 삭제 성공 메시지를 포함한 응답 객체
     */
    @Transactional
    public ApiResponse<Void> delete(Long id) {
        Todo todo = findTodoById(id);
        todoRepository.delete(todo);
        return ApiResponse.success("할 일이 삭제되었습니다");
    }

    /**
     * 페이징 처리된 Todo 목록을 조회합니다.
     *
     * @param userId 조회할 사용자의 ID
     * @param page 요청할 페이지 번호 (0부터 시작)
     * @param size 한 페이지당 항목 수
     * @return 페이징된 Todo 목록과 페이징 메타데이터를 포함한 응답 객체
     *
     * 프로세스:
     * 1. 사용자 존재 여부 확인
     * 2. 페이징 요청 객체 생성
     * 3. 페이징된 Todo 목록 조회
     * 4. Todo 엔티티를 DTO로 변환
     * 5. 페이징 메타데이터 구성
     */
    public ApiResponse<Map<String, Object>> findAllWithPaging(Long userId, int page, int size) {
        findUserById(userId);  // 사용자 존재 확인
        Page<Todo> todoPage = getTodoPageByUser(userId, createPageRequest(page, size));
        Page<TodoPageResponseDto> responseDtoPage = convertToResponseDtoPage(todoPage);

        Map<String, Object> pageData = createPageMetadata(responseDtoPage);
        return ApiResponse.success("페이징된 할 일 목록을 조회했습니다", pageData);
    }

    // ===== Private Helper Methods =====

    /**
     * 사용자 ID로 페이징된 Todo 목록을 조회합니다.
     */
    private Page<Todo> getTodoPageByUser(Long userId, Pageable pageable) {
        return todoRepository.findAllByUserIdWithPaging(userId, pageable);
    }

    /**
     * 사용자 ID로 사용자를 조회합니다.
     * 존재하지 않는 경우 UserNotFoundException 발생
     */
    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));
    }

    /**
     * Todo ID로 Todo를 조회합니다.
     * 존재하지 않는 경우 TodoNotFoundException 발생
     */
    private Todo findTodoById(Long id) {
        return todoRepository.findByOrElseThrow(id);
    }

    /**
     * 새로운 Todo 엔티티를 생성합니다.
     */
    private Todo createNewTodo(String title, String content, User user) {
        return new Todo(title, content, user);
    }

    /**
     * 특정 사용자의 모든 Todo 목록을 조회하여 DTO로 변환합니다.
     */
    private List<TodoResponseDto> getTodoListByUser(Long userId) {
        return todoRepository.findAllByUserId(userId)
                .stream()
                .map(TodoResponseDto::toTodo)  // 메서드 레퍼런스를 사용하여 가독성 향상
                .toList();
    }

    /**
     * Todo의 제목과 내용을 업데이트합니다.
     * null이거나 빈 문자열인 경우 기존 값을 유지합니다.
     */
    private void updateTodoContent(Todo todo, String newTitle, String newContent) {
        String updatedTitle = (newTitle != null && !newTitle.isBlank()) ? newTitle : todo.getTitle();
        String updatedContent = (newContent != null && !newContent.isBlank()) ? newContent : todo.getContent();
        todo.update(updatedTitle, updatedContent);
    }

    /**
     * 페이징 요청 객체를 생성합니다.
     * 수정일 기준 내림차순으로 정렬합니다.
     */
    private Pageable createPageRequest(int page, int size) {
        return PageRequest.of(page, size, Sort.by("modifiedAt").descending());
    }

    /**
     * Todo 엔티티 페이지를 DTO 페이지로 변환합니다.
     * 각 Todo에 대한 댓글 수를 포함합니다.
     */
    private Page<TodoPageResponseDto> convertToResponseDtoPage(Page<Todo> todoPage) {
        return todoPage.map(todo -> {
            Long commentCount = todoRepository.countCommentsByTodoId(todo.getId());
            return new TodoPageResponseDto(todo, commentCount);
        });
    }

    /**
     * 페이징 메타데이터를 생성합니다.
     * 현재 페이지, 전체 페이지 수, 전체 항목 수 등의 정보를 포함합니다.
     */
    private Map<String, Object> createPageMetadata(Page<TodoPageResponseDto> page) {
        return Map.of(
                "content", page.getContent(),          // 현재 페이지의 데이터
                "currentPage", page.getNumber(),       // 현재 페이지 번호 (0부터 시작)
                "totalPages", page.getTotalPages(),    // 전체 페이지 수
                "totalElements", page.getTotalElements(), // 전체 데이터 수
                "size", page.getSize()                // 페이지당 데이터 수
        );
    }
}