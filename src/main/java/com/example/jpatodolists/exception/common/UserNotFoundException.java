package com.example.jpatodolists.exception.common;

import com.example.jpatodolists.exception.ErrorCode;

public class UserNotFoundException extends BusinessException {
    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }

    public UserNotFoundException(String detail) {
        super(ErrorCode.USER_NOT_FOUND, detail);
    }
}