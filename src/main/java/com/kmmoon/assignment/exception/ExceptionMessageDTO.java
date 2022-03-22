package com.kmmoon.assignment.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExceptionMessageDTO {
    private int code;
    private String msg;
    private String detail;

    public ExceptionMessageDTO(CustomException customException) {
        this.code = customException.getErrorStatus().getCode();
        this.msg = customException.getErrorStatus().getMsg();
        this.detail = customException.getDetail();
    }
}
