package com.example.jpatodolists.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class TodoResponseDto {
    private Long id;               // 할일 ID
    private String title;          // 할일 제목
    private String content;        // 할일 내용
    private String username;       // 작성자명
    private LocalDateTime createdAt; // 생성일
    private LocalDateTime modifiedAt; // 수정일
}
