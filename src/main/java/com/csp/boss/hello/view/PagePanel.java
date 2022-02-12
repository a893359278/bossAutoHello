package com.csp.boss.hello.view;

import javax.swing.*;
import java.awt.*;

public class PagePanel extends JPanel {
    private JTextField startPage;
    private JTextField endPage;

    public PagePanel() {
        setSize(ViewUtil.windowWidth, ViewUtil.panelItemHeight);
        setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel startPageLabel = new JLabel("开始页数(从 1 开始): ");
        startPage = new JTextField(10);
        add(startPageLabel);
        add(startPage);

        JLabel endPageLabel = new JLabel("截至页数: ");
        endPage = new JTextField(10);
        add(endPageLabel);
        add(endPage);
    }
}
