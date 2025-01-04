package com.example.jpatodolists.entity;

import com.example.jpatodolists.exception.auth.UnauthorizedException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "todo")
public class Todo extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "longtext")
    private String content;

    //단방향으로 사용하기 위해 ManyToOne
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)

    private User user; // 연관관계 매핑

    private boolean isDeleted = false; // 소프트 딜리트 플래그

    public Todo(String title, String content, User user) {
        validateCreate(title, content, user);
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public void update(String newTitle, String newContent, User requestUser) {
        validateUpdate(newTitle, newContent, requestUser);
        this.title = newTitle;
        this.content = newContent;
    }

    public void markAsDeleted() {
        this.isDeleted = true;
    }

    private void validateCreate(String title, String content, User user) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("제목은 필수값입니다.");
        }
        if (user == null) {
            throw new IllegalArgumentException("사용자 정보는 필수값입니다.");
        }
    }

    private void validateUpdate(String newTitle, String newContent, User requestUser) {
        validateOwnership(requestUser);
        if (newTitle == null || newTitle.isBlank()) {
            throw new IllegalArgumentException("제목은 필수값입니다.");
        }
    }

    private void validateOwnership(User requestUser) {
        if (!user.getId().equals(requestUser.getId())) {
            throw new UnauthorizedException("해당 작업을 수행할 권한이 없습니다.");
        }
    }
}
