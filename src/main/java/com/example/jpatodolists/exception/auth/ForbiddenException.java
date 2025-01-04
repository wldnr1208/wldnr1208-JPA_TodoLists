package com.example.jpatodolists.exception.auth;

import com.example.jpatodolists.exception.ErrorCode;
import com.example.jpatodolists.exception.common.BusinessException;

public class ForbiddenException extends BusinessException {
    public ForbiddenException() {
        super(ErrorCode.FORBIDDEN_ACCESS);
    }

    public ForbiddenException(String detail) {
        super(ErrorCode.FORBIDDEN_ACCESS, detail);
    }
}