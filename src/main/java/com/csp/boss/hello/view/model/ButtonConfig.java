package com.csp.boss.hello.view.model;

import lombok.Data;

@Data
public class ButtonConfig {
    private String name;
    private boolean exclusive;
    private Object value;
    private boolean active;

    public boolean getExclusive() {
        return exclusive;
    }

    public void setExclusive(boolean exclusive) {
        this.exclusive = exclusive;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
