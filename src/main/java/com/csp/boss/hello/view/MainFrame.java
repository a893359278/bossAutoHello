/*
 * Created by JFormDesigner on Sat Feb 12 10:22:43 CST 2022
 */

package com.csp.boss.hello.view;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.csp.boss.hello.Main;
import com.csp.boss.hello.model.request.EmployeeInfoRequest;
import com.csp.boss.hello.model.request.PostHelloRequest;
import com.csp.boss.hello.model.response.AccountJobInfoModel;
import com.csp.boss.hello.model.response.CommonResponse;
import com.csp.boss.hello.model.response.EmployeeInfoModel;
import com.csp.boss.hello.model.response.PostHelloModel;
import com.csp.boss.hello.service.BossHelloService;
import com.csp.boss.hello.view.model.ConditionConfig;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author unknown
 */
@Slf4j
public class MainFrame extends JFrame {

    private BossHelloService bossHelloService;
    private static final String conditionConfigPath = "conditionConfig.json";

    private List<ChoicePanel> choicePanels = new ArrayList<>();
    private JobPanel jobPanel;
    private JPanel pagePanel;
    private JTextField startPage;
    private JTextField endPage;

    private JTextArea showMessageTextArea;

    private JButton startHelloJB;

    private JButton stopHelloJB;

    private String lineSplit = System.lineSeparator();

    private AtomicBoolean stop = new AtomicBoolean(false);

    ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(1, 2, 60, TimeUnit.SECONDS, new SynchronousQueue<>(), new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            log.info("postHello 任务被丢弃");
        }
    });

    public MainFrame(BossHelloService bossHelloService) {
        super("boss 直聘自动向牛人打招呼");
        this.bossHelloService = bossHelloService;
        initComponents();
    }

    private void initComponents() {

        setLayout(new FlowLayout(FlowLayout.LEFT));
        setSize(ViewUtil.windowWidth, ViewUtil.windowHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 获得窗口大小对象
        Dimension frameSize = getSize();

        Point point = ViewUtil.getCenterPoint(frameSize.width, frameSize.height);
        // 设置窗口居中显示器显示
        setLocation(point.x, point.y);

        List<AccountJobInfoModel> jobInfos = bossHelloService.getJobInfos(System.currentTimeMillis());

        // 解析配置
        String conditionContent = IoUtil.read(Main.class.getClassLoader().getResourceAsStream(conditionConfigPath), CharsetUtil.CHARSET_UTF_8);
        List<ConditionConfig> conditionConfigs = JSON.parseArray(conditionContent, ConditionConfig.class);

        // 渲染当前可沟通职位
        jobPanel = new JobPanel(jobInfos);
        getContentPane().add(jobPanel);

        // 根据配置创建面板
        for (ConditionConfig config : conditionConfigs) {
            ChoicePanel panel = new ChoicePanel(config);
            choicePanels.add(panel);
            getContentPane().add(panel);
        }

        pagePanel = new JPanel();
        pagePanel.setSize(ViewUtil.windowWidth, ViewUtil.panelItemHeight);
        pagePanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel startPageLabel = new JLabel("开始页数(从 1 开始): ");
        startPage = new JTextField(10);
        startPage.setText("1");
        pagePanel.add(startPageLabel);
        pagePanel.add(startPage);

        JLabel endPageLabel = new JLabel("截至页数: ");
        endPage = new JTextField(10);
        endPage.setText("10");
        pagePanel.add(endPageLabel);
        pagePanel.add(endPage);
        getContentPane().add(pagePanel);

        JPanel startHelloPanel = new BaseJPanel();
        startHelloJB = new JButton("开始打招呼");
        startHelloPanel.add(startHelloJB);
        getContentPane().add(startHelloPanel);


        // 注册监听事件
        startHelloJB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startHello();

                String text = startPage.getText();
                int currentPage = Integer.parseInt(text.trim());
                int stopPage = Integer.MAX_VALUE;
                if (!StrUtil.isBlank(endPage.getText())) {
                    stopPage = Integer.parseInt(endPage.getText().trim());
                }

                poolExecutor.execute(new HelloRunnable(currentPage, stopPage));
            }
        });

        JPanel stopPanel = new BaseJPanel();
        stopHelloJB = new JButton("停止打招呼");
        stopHelloJB.setEnabled(false);
        stopHelloJB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopHello();
                showMessageTextArea.insert("已停止自动打招呼" + lineSplit, 0);
                showMessageTextArea.paintImmediately(showMessageTextArea.getBounds());
            }
        });
        stopPanel.add(stopHelloJB);
        getContentPane().add(stopPanel);

        JPanel showMessagePanel = new JPanel();
        showMessagePanel.setSize(ViewUtil.windowWidth, ViewUtil.panelItemHeight);
        showMessagePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        showMessageTextArea = new JTextArea();
        showMessageTextArea.setLineWrap(true);
        showMessageTextArea.setForeground(Color.BLACK);    //设置组件的背景色
        showMessageTextArea.setFont(new Font("楷体",Font.BOLD,16));    //修改字体样式
        showMessageTextArea.setBackground(Color.YELLOW);    //设置按钮背景色
        showMessageTextArea.setSize(600, 10);
        showMessageTextArea.setRows(10);
        JScrollPane jsp = new JScrollPane(showMessageTextArea);    //将文本域放入滚动窗口
        jsp.getVerticalScrollBar().setMaximum(1000);
        jsp.setBounds(0, 0, 600, 100);
        showMessagePanel.add(jsp);
        getContentPane().add(showMessagePanel);
    }

    public String getChoiceValue(ChoiceValueEnum valueEnum) {
        for (ChoicePanel panel : choicePanels) {
            if (panel.getConditionConfig().getId().equals(valueEnum.name())) {
                return panel.getChoiceValue();
            }
        }
        return null;
    }

    public enum ChoiceValueEnum {
        salary,intention,degree,experience,
    }

    public class HelloRunnable implements Runnable {

        private int stopPage;
        private int currentPage;
        private int total = 0;

        public HelloRunnable(int currentPage, int stopPage) {
            this.stopPage = stopPage;
            this.currentPage = currentPage;
        }

        @Override
        public void run() {
            while (currentPage <= stopPage && !stop.get()) {
                EmployeeInfoRequest request = new EmployeeInfoRequest();

                request.setDegree(getChoiceValue(ChoiceValueEnum.degree));
                request.setSalary(getChoiceValue(ChoiceValueEnum.salary));
                request.setIntention(getChoiceValue(ChoiceValueEnum.intention));
                request.setExperience(getChoiceValue(ChoiceValueEnum.experience));

                request.setPage(currentPage);

                CommonResponse<EmployeeInfoModel> employeeInfosResponse = bossHelloService.getEmployeeInfos(request);
                if (employeeInfosResponse.getCode() != 0) {
                    JOptionPane.showConfirmDialog(pagePanel, employeeInfosResponse.getMessage(), "出现异常拉", -1);
                }

                EmployeeInfoModel employeeInfos = employeeInfosResponse.getZpData();
                List<EmployeeInfoModel.GeekList> geekList = employeeInfos.getGeekList();

                String jid = employeeInfos.getEncryptJobId();
                if (!CollectionUtil.isEmpty(geekList)) {
                    PostHelloRequest postHelloRequest = new PostHelloRequest();
                    postHelloRequest.setJid(jid);

                    for (EmployeeInfoModel.GeekList item : geekList) {
                        if (stop.get()) {
                            return;
                        }
                        EmployeeInfoModel.GeekCard card = item.getGeekCard();
                        long expectId = card.getExpectId();
                        String securityId = card.getSecurityId();
                        postHelloRequest.setExpectId(String.valueOf(expectId));
                        postHelloRequest.setSecurityId(securityId);

                        showMessageTextArea.insert("已打招呼【" + total + "】个, 正在向" + "【" + card.getGeekName() + "】" + "打招呼" + lineSplit, 0);
                        CommonResponse<PostHelloModel> hello = bossHelloService.postHello(postHelloRequest);
                        if (hello.getCode() == 0) {
                            log.info("【" + card.getGeekName() + "】" + "打招呼成功");
                            showMessageTextArea.insert("【" + card.getGeekName() + "】" + "打招呼成功" + lineSplit, 0);
                        } else {
                            showMessageTextArea.insert("【" + card.getGeekName() + "】" + "打招呼失败" + lineSplit, 0);
                            log.info("【" + card.getGeekName() + "】" + "打招呼失败");
                        }
                        showMessageTextArea.paintImmediately(showMessageTextArea.getBounds());

                        // 睡眠前返回
                        if (stop.get()) {
                            return;
                        }

                        int endInt = Integer.parseInt(System.getProperty("boss.hello.sleepEnd", "40"));
                        int startInt = Integer.parseInt(System.getProperty("boss.hello.sleepStart", "20"));
                        int sleepSecond = RandomUtil.randomInt(startInt, endInt);
                        showMessageTextArea.insert("休息【" + sleepSecond + "】秒" + lineSplit, 0);
                        showMessageTextArea.paintImmediately(showMessageTextArea.getBounds());
                        ThreadUtil.sleep(sleepSecond, TimeUnit.SECONDS);

                        total++;
                    }
                }

                currentPage++;
                int sleepPage = Integer.parseInt(System.getProperty("boss.hello.sleepPage", "60"));
                showMessageTextArea.insert("休息【" + 60 +"】秒,当前页【" + (currentPage - 1) + "】打招呼结束,准备前往下一页【" + currentPage + "】"  + lineSplit, 0);
                ThreadUtil.sleep(sleepPage, TimeUnit.SECONDS);
                showMessageTextArea.paintImmediately(showMessageTextArea.getBounds());
            }

            showMessageTextArea.insert("自动打招呼结束, 总共打招呼【" + total + "】个"  + lineSplit, 0);
            showMessageTextArea.paintImmediately(showMessageTextArea.getBounds());

            stopHello();
        }
    }

    private void stopHello() {
        stopHelloJB.setEnabled(false);
        startHelloJB.setEnabled(true);
        stop.set(true);
    }

    private void startHello() {
        startHelloJB.setEnabled(false);
        stopHelloJB.setEnabled(true);
        stop.set(false);
    }

}
