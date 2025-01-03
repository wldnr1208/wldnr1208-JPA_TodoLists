package com.example.jpatodolists.exception.validation;

import lombok.Getter;

@Getter
public class DuplicateUsernameException extends RuntimeException {
    public DuplicateUsernameException(String message) {
        super(message);
    }
}