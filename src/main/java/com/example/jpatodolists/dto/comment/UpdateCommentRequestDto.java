package com.example.jpatodolists.dto.comment;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class UpdateCommentRequestDto {
    @NotBlank(message = "수정할 댓글 내용은 필수입니다.")
    private String content;

    @Getter
    @NoArgsConstructor
    public static class CreateTodoResponseDto {
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
}
