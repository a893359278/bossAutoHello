package com.csp.boss.hello.view;

import cn.hutool.core.util.StrUtil;
import com.csp.boss.hello.service.BossHelloService;
import com.csp.boss.hello.view.listener.LoginCallback;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class LoginFrame extends JFrame {
    private int width = 400;
    private int height = 200;

    private JTextArea cookieValue;

    private JButton confirmBtn;

    private List<LoginCallback> callbackList = new ArrayList<>();

    public LoginFrame() {
        super("登录页面");
        init();
    }

    private void init() {
        setSize(width, height);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // 获得窗口大小对象
        Dimension frameSize = getSize();
        Point point = ViewUtil.getCenterPoint(frameSize.width, frameSize.height);
        // 设置窗口居中显示器显示
        setLocation(point.x, point.y);

        JPanel jPanel = new JPanel();

        cookieValue = new JTextArea("请输入Cookie", 7, 30);
        cookieValue.setLineWrap(true);    //设置文本域中的文本为自动换行
        cookieValue.setForeground(Color.BLACK);    //设置组件的背景色
        cookieValue.setFont(new Font("楷体", Font.BOLD, 16));    //修改字体样式
        cookieValue.setBackground(Color.YELLOW);    //设置按钮背景色
        JScrollPane jsp = new JScrollPane(cookieValue);    //将文本域放入滚动窗口
        Dimension size = cookieValue.getPreferredSize();    //获得文本域的首选大小
        jsp.setBounds(110, 90, size.width, size.height);
        jPanel.add(jsp);    //将JScrollPane添加到JPanel容器中


        confirmBtn = new JButton("登录");

        jPanel.add(confirmBtn);
        
        confirmBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = cookieValue.getText();
                if (StrUtil.isBlank(text)) {
                    JOptionPane.showConfirmDialog(jPanel, "请输入 Cookie", "请输入 Cookie", -1);
                    return;
                }

                for (LoginCallback callback : callbackList) {
                    callback.callback(text.trim());
                }

            }
        });

        add(jPanel);
    }


    public void addLoginCallback(LoginCallback loginCallback) {
        callbackList.add(loginCallback);
    }

}
