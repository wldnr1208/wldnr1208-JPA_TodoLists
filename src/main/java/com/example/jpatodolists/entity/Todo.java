package com.example.jpatodolists.entity;

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

    public Todo(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }
    public void update(String newTitle, String newContent) {
        this.title = newTitle;
        this.content = newContent;
    }

    private boolean isDeleted = false; // 소프트 딜리트 플래그
}
