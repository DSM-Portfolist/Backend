package com.example.portfolist.domain.auth.exception;

import com.example.portfolist.global.error.ErrorCode;
import com.example.portfolist.global.error.exception.GlobalException;

public class FieldNotFoundException extends GlobalException {

    public FieldNotFoundException() {
        super(ErrorCode.FIELD_NOT_FOUND);
    }

}
