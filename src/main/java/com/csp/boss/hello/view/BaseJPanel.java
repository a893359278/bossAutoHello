package com.csp.boss.hello.view;

import javax.swing.*;
import java.awt.*;

public class BaseJPanel extends JPanel {
    public BaseJPanel() {
        super();
        setSize(ViewUtil.windowWidth, ViewUtil.panelItemHeight);
        setLayout(new FlowLayout(FlowLayout.LEFT));
    }
}
