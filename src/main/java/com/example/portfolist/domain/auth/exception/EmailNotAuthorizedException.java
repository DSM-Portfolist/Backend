package com.example.portfolist.domain.auth.exception;

import com.example.portfolist.global.error.ErrorCode;
import com.example.portfolist.global.error.exception.GlobalException;

public class EmailNotAuthorizedException extends GlobalException {

    public EmailNotAuthorizedException() {
        super(ErrorCode.EMAIL_NOT_AUTHORIZED);
    }

}
