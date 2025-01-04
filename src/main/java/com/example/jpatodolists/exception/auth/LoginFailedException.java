package com.example.jpatodolists.exception.auth;

import com.example.jpatodolists.exception.ErrorCode;
import com.example.jpatodolists.exception.common.BusinessException;

public class LoginFailedException extends BusinessException {
    public LoginFailedException() {
        super(ErrorCode.LOGIN_FAILED);
    }

    public LoginFailedException(String detail) {
        super(ErrorCode.LOGIN_FAILED, detail);
    }
}