package com.example.jpatodolists.exception.common;

import com.example.jpatodolists.exception.ErrorCode;

public class TodoNotFoundException extends BusinessException {
    public TodoNotFoundException() {
        super(ErrorCode.TODO_NOT_FOUND);
    }

    public TodoNotFoundException(String detail) {
        super(ErrorCode.TODO_NOT_FOUND, detail);
    }
}