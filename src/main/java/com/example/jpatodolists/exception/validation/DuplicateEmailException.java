package com.example.jpatodolists.exception.validation;

import lombok.Getter;

@Getter
public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String message) {
        super(message);
    }
}
