package com.csp.boss.hello.view;

import com.csp.boss.hello.model.response.AccountJobInfoModel;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JobPanel extends JPanel {

    private List<AccountJobInfoModel> jobInfos;
    private Map<String, AccountJobInfoModel> map = new HashMap<>();
    private JComboBox<String> cmb;

    public JobPanel(List<AccountJobInfoModel> jobInfos) {
        this.jobInfos = jobInfos;
        init();
    }

    private void init() {
        JLabel title = new JLabel("当前自动打招呼职位：");
        add(title);

        setSize(ViewUtil.windowWidth, ViewUtil.panelItemHeight);
        setLayout(new FlowLayout(FlowLayout.LEFT));

        cmb = new JComboBox<>();
        for (AccountJobInfoModel info : jobInfos) {
            String name = info.getJobName() + "_" + info.getSalary();
            cmb.addItem(name);
            map.put(name, info);
        }

        add(cmb);
    }

    public AccountJobInfoModel getCurrentJob() {
        return map.get(String.valueOf(cmb.getSelectedItem()));
    }
}
