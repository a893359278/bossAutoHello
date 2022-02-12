package com.csp.boss.hello.model.request;

import lombok.Data;

@Data
public class PostHelloRequest {
    private String jid;
    private String expectId;
    private String securityId;
}
