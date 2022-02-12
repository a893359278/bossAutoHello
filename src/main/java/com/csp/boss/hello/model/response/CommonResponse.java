package com.csp.boss.hello.model.response;

import lombok.Data;

@Data
public class CommonResponse<T> {
    private int code;
    private String message;
    private T zpData;
}
