package com.example.jpatodolists.dto.comment;

import com.example.jpatodolists.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetCommentResponseDto {
    private final Long id;
    private final String content;
    private final String username;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public GetCommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.username = comment.getUser().getUsername();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }

    public static GetCommentResponseDto from(Comment comment) {
        return new GetCommentResponseDto(comment);
    }
}
