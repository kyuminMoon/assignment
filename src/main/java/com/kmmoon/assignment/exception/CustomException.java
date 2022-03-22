package com.kmmoon.assignment.exception;

import lombok.*;

@Getter
public class CustomException extends RuntimeException {

    private final ErrorStatus errorStatus;

    private String detail;

    @Builder
    public CustomException(ErrorStatus errorStatus, String detail) {
        this.errorStatus = errorStatus;
        this.detail = detail;
    }

    @Override
    public String getMessage() {
        return errorStatus.toString();
    }
}
