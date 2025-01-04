package com.example.jpatodolists.exception.auth;

import com.example.jpatodolists.exception.ErrorCode;
import com.example.jpatodolists.exception.common.BusinessException;

public class UnauthorizedException extends BusinessException {
    public UnauthorizedException() {
        super(ErrorCode.UNAUTHORIZED_ACCESS);
    }

    public UnauthorizedException(String detail) {
        super(ErrorCode.UNAUTHORIZED_ACCESS, detail);
    }
}