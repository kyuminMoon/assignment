package com.kmmoon.assignment.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ValidException<T> {

    private ErrorStatus errorStatus;

    private T data;

    public ValidException(ErrorStatus errorStatus, T data) {
        this.errorStatus = errorStatus;
        this.data = data;
    }

}
