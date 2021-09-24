package com.example.portfolist.domain.auth.exception;

import lombok.Getter;

@Getter
public class OauthServerException extends RuntimeException {

    private final int status;
    private final String message;

    public OauthServerException(int status, String message) {
        this.status = status;
        this.message = message;
    }

}
