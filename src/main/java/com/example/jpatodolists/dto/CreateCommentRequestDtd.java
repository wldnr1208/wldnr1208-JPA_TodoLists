package com.example.jpatodolists.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateCommentRequestDtd {
    @NotBlank(message = "댓글 내용은 필수입니다.")
    private String content;
}
