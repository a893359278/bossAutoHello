package com.csp.boss.hello.model.response;

import lombok.Data;

import java.util.List;

@Data
public class ListResponse<T> {
    private int code;
    private String message;
    private List<T> zpData;
}
