package com.example.jpatodolists.exception.validation;

import com.example.jpatodolists.exception.ErrorCode;
import com.example.jpatodolists.exception.common.BusinessException;
import lombok.Getter;

@Getter
public class DuplicateEmailException extends BusinessException {
    public DuplicateEmailException() {
        super(ErrorCode.DUPLICATE_EMAIL);
    }

    public DuplicateEmailException(String detail) {
        super(ErrorCode.DUPLICATE_EMAIL, detail);
    }
}