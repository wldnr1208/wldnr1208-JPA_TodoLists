package com.example.jpatodolists.exception.validation;

import com.example.jpatodolists.exception.ErrorCode;
import com.example.jpatodolists.exception.common.BusinessException;

public class WrongAccessException extends BusinessException {
    public WrongAccessException() {
        super(ErrorCode.INVALID_ACCESS);
    }

    public WrongAccessException(String detail) {
        super(ErrorCode.INVALID_ACCESS, detail);
    }
}