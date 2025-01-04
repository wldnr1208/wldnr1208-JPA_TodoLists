package com.example.jpatodolists.exception.auth;

import com.example.jpatodolists.exception.ErrorCode;
import com.example.jpatodolists.exception.common.BusinessException;

public class WrongCredentialsException extends BusinessException {
    public WrongCredentialsException() {
        super(ErrorCode.WRONG_CREDENTIALS);
    }

    public WrongCredentialsException(String detail) {
        super(ErrorCode.WRONG_CREDENTIALS, detail);
    }
}