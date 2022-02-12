package com.csp.boss.hello.view.model;

import lombok.Data;

import java.util.List;

@Data
public class ConditionConfig {
    private String id;
    private String title;
    private boolean multi;
    private List<ButtonConfig> buttons;
}
