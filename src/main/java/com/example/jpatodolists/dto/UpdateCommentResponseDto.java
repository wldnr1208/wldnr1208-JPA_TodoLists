package com.example.jpatodolists.dto;

import com.example.jpatodolists.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateCommentResponseDto {
    private final Long id;
    private final String content;
    private final String username;
    private final LocalDateTime modifiedAt;

    public UpdateCommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.username = comment.getUser().getUsername();
        this.modifiedAt = comment.getModifiedAt();
    }
}
