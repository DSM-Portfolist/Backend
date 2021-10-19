package com.example.portfolist.global.error.exception;

import com.example.portfolist.global.error.ErrorCode;

public class PermissionDeniedException extends GlobalException{

    public PermissionDeniedException() {
        super(ErrorCode.PERMISSION_DENIED);
    }
}
