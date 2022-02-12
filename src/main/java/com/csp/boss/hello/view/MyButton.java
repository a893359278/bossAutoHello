package com.csp.boss.hello.view;

import com.csp.boss.hello.view.model.ButtonConfig;

import javax.swing.*;
import java.awt.*;

public class MyButton extends JCheckBox {

    private final ButtonConfig buttonConfig;

    private int width = 60;
    private int height = 30;

    private Color activeColor = Color.GREEN;

    private Color unActiveColor = Color.WHITE;

    public MyButton(ButtonConfig buttonConfig) {
        super(buttonConfig.getName());
        this.buttonConfig = buttonConfig;
        init();
    }

    private void init() {
//        setSize(width, height);
//        setBackground(unActiveColor);

    }
}
