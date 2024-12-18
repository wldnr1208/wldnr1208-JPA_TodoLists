package com.example.jpatodolists.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateCommentRequestDto {
    @NotBlank(message = "수정할 댓글 내용은 필수입니다.")
    private String content;
}
