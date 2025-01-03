package com.example.jpatodolists.exception.validation;

public class WrongAccessException extends RuntimeException{
    public WrongAccessException(String message) {
        super(message);
    }
}
