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
            log.info("postHello ???????????????");
        }
    });

    public MainFrame(BossHelloService bossHelloService) {
        super("boss ??????????????????????????????");
        this.bossHelloService = bossHelloService;
        initComponents();
    }

    private void initComponents() {

        setLayout(new FlowLayout(FlowLayout.LEFT));
        setSize(ViewUtil.windowWidth, ViewUtil.windowHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ????????????????????????
        Dimension frameSize = getSize();

        Point point = ViewUtil.getCenterPoint(frameSize.width, frameSize.height);
        // ?????????????????????????????????
        setLocation(point.x, point.y);

        List<AccountJobInfoModel> jobInfos = bossHelloService.getJobInfos(System.currentTimeMillis());

        // ????????????
        String conditionContent = IoUtil.read(Main.class.getClassLoader().getResourceAsStream(conditionConfigPath), CharsetUtil.CHARSET_UTF_8);
        List<ConditionConfig> conditionConfigs = JSON.parseArray(conditionContent, ConditionConfig.class);

        // ???????????????????????????
        jobPanel = new JobPanel(jobInfos);
        getContentPane().add(jobPanel);

        // ????????????????????????
        for (ConditionConfig config : conditionConfigs) {
            ChoicePanel panel = new ChoicePanel(config);
            choicePanels.add(panel);
            getContentPane().add(panel);
        }

        pagePanel = new JPanel();
        pagePanel.setSize(ViewUtil.windowWidth, ViewUtil.panelItemHeight);
        pagePanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel startPageLabel = new JLabel("????????????(??? 1 ??????): ");
        startPage = new JTextField(10);
        startPage.setText("1");
        pagePanel.add(startPageLabel);
        pagePanel.add(startPage);

        JLabel endPageLabel = new JLabel("????????????: ");
        endPage = new JTextField(10);
        endPage.setText("10");
        pagePanel.add(endPageLabel);
        pagePanel.add(endPage);
        getContentPane().add(pagePanel);

        JPanel startHelloPanel = new BaseJPanel();
        startHelloJB = new JButton("???????????????");
        startHelloPanel.add(startHelloJB);
        getContentPane().add(startHelloPanel);


        // ??????????????????
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
        stopHelloJB = new JButton("???????????????");
        stopHelloJB.setEnabled(false);
        stopHelloJB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopHello();
                showMessageTextArea.insert("????????????????????????" + lineSplit, 0);
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
        showMessageTextArea.setForeground(Color.BLACK);    //????????????????????????
        showMessageTextArea.setFont(new Font("??????",Font.BOLD,16));    //??????????????????
        showMessageTextArea.setBackground(Color.YELLOW);    //?????????????????????
        showMessageTextArea.setSize(600, 10);
        showMessageTextArea.setRows(10);
        JScrollPane jsp = new JScrollPane(showMessageTextArea);    //??????????????????????????????
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
            AccountJobInfoModel currentJob = jobPanel.getCurrentJob();

            while (currentPage <= stopPage && !stop.get()) {
                EmployeeInfoRequest request = new EmployeeInfoRequest();

                request.setDegree(getChoiceValue(ChoiceValueEnum.degree));
                request.setSalary(getChoiceValue(ChoiceValueEnum.salary));
                request.setIntention(getChoiceValue(ChoiceValueEnum.intention));
                request.setExperience(getChoiceValue(ChoiceValueEnum.experience));
                request.setJobId(currentJob.getJid());

                request.setPage(currentPage);

                CommonResponse<EmployeeInfoModel> employeeInfosResponse = bossHelloService.getEmployeeInfos(request);
                if (employeeInfosResponse.getCode() != 0) {
                    JOptionPane.showConfirmDialog(pagePanel, employeeInfosResponse.getMessage(), "???????????????", -1);
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
                        postHelloRequest.setGid(item.getEncryptGeekId());
                        postHelloRequest.setLid(item.getGeekCard().getLid());

                        showMessageTextArea.insert("???????????????" + total + "??????, ?????????" + "???" + card.getGeekName() + "???" + "?????????" + lineSplit, 0);
                        CommonResponse<PostHelloModel> hello = bossHelloService.postHello(postHelloRequest);
                        if (hello.getCode() == 0) {
                            log.info("???" + card.getGeekName() + "???" + "???????????????");
                            showMessageTextArea.insert("???" + card.getGeekName() + "???" + "???????????????" + lineSplit, 0);
                        } else {
                            showMessageTextArea.insert("???" + card.getGeekName() + "???" + "???????????????" + lineSplit, 0);
                            log.info("???" + card.getGeekName() + "???" + "???????????????");
                        }
                        showMessageTextArea.paintImmediately(showMessageTextArea.getBounds());

                        // ???????????????
                        if (stop.get()) {
                            return;
                        }

                        int endInt = Integer.parseInt(System.getProperty("boss.hello.sleepEnd", "20"));
                        int startInt = Integer.parseInt(System.getProperty("boss.hello.sleepStart", "10"));
                        int sleepSecond = RandomUtil.randomInt(startInt, endInt);
                        showMessageTextArea.insert("?????????" + sleepSecond + "??????" + lineSplit, 0);
                        showMessageTextArea.paintImmediately(showMessageTextArea.getBounds());
                        ThreadUtil.sleep(sleepSecond, TimeUnit.SECONDS);

                        total++;
                    }
                }

                currentPage++;
                int sleepPage = Integer.parseInt(System.getProperty("boss.hello.sleepPage", "20"));
                showMessageTextArea.insert("?????????" + 20 +"??????,????????????" + (currentPage - 1) + "??????????????????,????????????????????????" + currentPage + "???"  + lineSplit, 0);
                ThreadUtil.sleep(sleepPage, TimeUnit.SECONDS);
                showMessageTextArea.paintImmediately(showMessageTextArea.getBounds());
            }

            showMessageTextArea.insert("?????????????????????, ??????????????????" + total + "??????"  + lineSplit, 0);
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
