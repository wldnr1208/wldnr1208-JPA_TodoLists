package com.example.jpatodolists.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CreateTodoResponseDto {
    private Long id;               // 할일 ID
    private String title;          // 할일 제목
    private String content;        // 할일 내용
    private LocalDateTime createdAt; // 생성일
    private LocalDateTime modifiedAt; // 수정일

    public CreateTodoResponseDto(LocalDateTime modifiedAt, LocalDateTime createdAt, String content, String title, Long id) {
        this.modifiedAt = modifiedAt;
        this.createdAt = createdAt;
        this.content = content;
        this.title = title;
        this.id = id;
    }

}
