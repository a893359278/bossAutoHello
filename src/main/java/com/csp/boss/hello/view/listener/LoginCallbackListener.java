package com.csp.boss.hello.view.listener;

import com.csp.boss.hello.service.BossHelloService;
import com.csp.boss.hello.view.LoginFrame;
import com.csp.boss.hello.view.MainFrame;

public class LoginCallbackListener implements LoginCallback {

    private BossHelloService bossHelloService;
    private LoginFrame loginFrame;

    public LoginCallbackListener(BossHelloService bossHelloService, LoginFrame loginFrame) {
        this.bossHelloService = bossHelloService;
        this.loginFrame = loginFrame;
    }

    @Override
    public void callback(String cookie) {
        bossHelloService.setCookie(cookie);

        // 创建主 frame
        MainFrame frame = new MainFrame(bossHelloService);

        loginFrame.dispose();
        // 设置界面可见
        frame.setVisible(true);

    }
}
