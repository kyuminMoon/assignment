package com.kmmoon.assignment.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@RequiredArgsConstructor
public enum ErrorStatus {

    RESOURCE_NOT_FOUND(-201, "ResourceNotFound", HttpStatus.BAD_REQUEST),
    RESOURCE_ALREADY_EXISTS(-202, "ResourceAlreadyExists", HttpStatus.BAD_REQUEST),

    USER_NOT_FOUND(-301, "UserNotFound", HttpStatus.BAD_REQUEST),
    USER_ALREADY_EXISTS(-302, "UserAlreadyExists", HttpStatus.BAD_REQUEST),

    INVALID_VALUE(-401, "InvalidValue", HttpStatus.BAD_REQUEST),
    INVALID_USER(-402, "InvalidUser", HttpStatus.UNAUTHORIZED),
    INVALID_ACCESS(-403, "InvalidAccess", HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN(-404, "InvalidToken", HttpStatus.UNAUTHORIZED),
    OVER_COUNT(-400, "OverCount", HttpStatus.BAD_REQUEST),

    ACCESS_DENIED(-901, "AccessDenied", HttpStatus.FORBIDDEN);

    private final int code;
    private final String msg;
    @JsonIgnore
    private final HttpStatus httpStatus;

}
