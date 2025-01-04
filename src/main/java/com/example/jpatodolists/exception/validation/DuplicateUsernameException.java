package com.example.jpatodolists.exception.validation;

import com.example.jpatodolists.exception.ErrorCode;
import com.example.jpatodolists.exception.common.BusinessException;
import lombok.Getter;

@Getter
public class DuplicateUsernameException extends BusinessException {
    public DuplicateUsernameException() {
        super(ErrorCode.DUPLICATE_USERNAME);
    }

    public DuplicateUsernameException(String detail) {
        super(ErrorCode.DUPLICATE_USERNAME, detail);
    }
}