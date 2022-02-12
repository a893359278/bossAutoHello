package com.csp.boss.hello;

import com.csp.boss.hello.service.BossHelloService;
import com.csp.boss.hello.service.impl.HutoolBossHelloService;
import com.csp.boss.hello.view.LoginFrame;
import com.csp.boss.hello.view.listener.LoginCallbackListener;

import javax.swing.*;

public class Main {


    public static void main(String[] args) {

        BossHelloService bossHelloService = new HutoolBossHelloService();

        LoginFrame loginFrame = new LoginFrame();

        loginFrame.addLoginCallback(new LoginCallbackListener(bossHelloService, loginFrame));

        loginFrame.setVisible(true);

    }



    private static void placeComponents(JPanel panel) {

        /* 布局部分我们这边不多做介绍
         * 这边设置布局为 null
         */
        panel.setLayout(null);

        // 创建 JLabel
        JLabel userLabel = new JLabel("User:");
        /* 这个方法定义了组件的位置。
         * setBounds(x, y, width, height)
         * x 和 y 指定左上角的新位置，由 width 和 height 指定新的大小。
         */
        userLabel.setBounds(10,20,80,25);
        panel.add(userLabel);

        /*
         * 创建文本域用于用户输入
         */
        JTextField userText = new JTextField(20);
        userText.setBounds(100,20,165,25);
        panel.add(userText);

        // 输入密码的文本域
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10,50,80,25);
        panel.add(passwordLabel);

        /*
         *这个类似用于输入的文本域
         * 但是输入的信息会以点号代替，用于包含密码的安全性
         */
        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(100,50,165,25);
        panel.add(passwordText);

        // 创建登录按钮
        JButton loginButton = new JButton("login");
        loginButton.setBounds(10, 80, 80, 25);
        panel.add(loginButton);
    }
}
