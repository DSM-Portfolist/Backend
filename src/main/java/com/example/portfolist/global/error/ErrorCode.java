package com.example.portfolist.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    NOT_FOUND_LOCAL_SERVER_IP(500, "Not Found Local Server Ip"),
    INVALID_INPUT_VALUE(400, "Invalid Input Value"),
    WRONG_FILE_EXTENSION(400, "Wrong File Extension"),
    INVALID_TOKEN(401, "Invalid Token"),
    PASSWORD_NOT_MATCHED(401, "Password Not Matched"),

    USER_NOT_FOUND(404, "User Not Found"),
    USER_DUPLICATED(409, "User Duplicated"),
    EMAIL_NOT_AUTHORIZED(401, "Email Not Authorized"),
    FIELD_NOT_FOUND(404, "Field Not Found");

    private final int status;
    private final String message;
}
