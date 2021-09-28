package com.example.portfolist.global.error.exception;

import com.example.portfolist.global.error.ErrorCode;

public class NotFoundLocalServerIpException extends GlobalException {

    public NotFoundLocalServerIpException() {
        super(ErrorCode.NOT_FOUND_LOCAL_SERVER_IP);
    }

}
