package com.csp.boss.hello.model.response;

import lombok.Data;

@Data
public class PostHelloModel {
    private String priceId;

    private String targetId;

    private int targetType;

    private int payMode;

    private int view;

    private int chat;

    private String securityId;

    private String configId;

    private String experiencePriceId;

    private int newfriend;

    private String limitTitle;

    private String stateDesc;

    private String stateDes;

    private int status;

    private String greeting;

    private int greetingTip;

    private String ba;

    private int business;

    private String chatToast;

    private String blockPageData;

    private String rightsUseTip;

    private Notice notice;

    private int goldCollarChatPresent;

    @Data
    public static class Notice {
        private boolean need;

        private String msg;

        private String usablePrivilegeMsg;

        private String purchasableMsg;
    }
}
