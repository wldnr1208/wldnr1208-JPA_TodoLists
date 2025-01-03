package com.example.jpatodolists.dto.todo;

import com.example.jpatodolists.entity.Todo;
import lombok.Getter;

@Getter
public class TodoResponseDto {
    private String title;
    private String content;


    public TodoResponseDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public static TodoResponseDto toTodo(Todo todo) {
        /*User 엔티티에서 username 가져오기 todo.getUser().getUsername()*/
        return new TodoResponseDto(todo.getTitle(), todo.getContent());
    }
}
