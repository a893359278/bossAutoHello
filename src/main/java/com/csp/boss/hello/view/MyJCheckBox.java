package com.csp.boss.hello.view;

import com.csp.boss.hello.view.model.ButtonConfig;

import javax.swing.*;

public class MyJCheckBox extends JCheckBox {

    private ButtonConfig config;

    public MyJCheckBox(ButtonConfig config, boolean active) {
        super(config.getName(), active);
        this.config = config;
    }

    public MyJCheckBox(ButtonConfig config) {
        this(config, false);
    }

    public void init() {
    }

    public ButtonConfig getConfig() {
        return config;
    }

    public void setConfig(ButtonConfig config) {
        this.config = config;
    }
}
