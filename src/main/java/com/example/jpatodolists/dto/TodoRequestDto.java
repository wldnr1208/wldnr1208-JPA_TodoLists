package com.example.jpatodolists.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TodoRequestDto {
    private String title;     // 할일 제목
    private String content;   // 할일 내용
    private Long userId;
}
