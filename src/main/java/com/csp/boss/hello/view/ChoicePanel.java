/*
 * Created by JFormDesigner on Sat Feb 12 10:37:33 CST 2022
 */

package com.csp.boss.hello.view;

import javax.swing.*;

import com.csp.boss.hello.view.model.ButtonConfig;
import com.csp.boss.hello.view.model.ConditionConfig;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.*;
import java.util.List;

/**
 * @author unknown
 */
public class ChoicePanel extends JPanel {

    private final ConditionConfig conditionConfig;

    private Map<String, MyJCheckBox> checkBoxMap = new HashMap<>();

    private List<MyJCheckBox> checkBoxList = new ArrayList<>();
    
    public String getChoiceValue() {
        StringBuilder sb = new StringBuilder();
        for (MyJCheckBox box : checkBoxList) {
            if (box.isSelected()) {
                String o = box.getConfig().getValue().toString();
                sb.append(o);
                sb.append(",");
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
    
    public ChoicePanel(ConditionConfig conditionConfig) {
        this.conditionConfig = conditionConfig;
        initComponents();
    }

    private void initComponents() {

        setSize(ViewUtil.windowWidth, ViewUtil.panelItemHeight);
        setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel title = new JLabel(conditionConfig.getTitle());
        add(title);

        for (ButtonConfig config : conditionConfig.getButtons()) {
            MyJCheckBox box = new MyJCheckBox(config, config.getActive());
            checkBoxMap.put(config.getName(), box);
            checkBoxList.add(box);
            add(box);
        }

        for (MyJCheckBox checkBox : checkBoxList) {
            checkBox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    MyJCheckBox item = (MyJCheckBox) e.getItem();
                    int change = e.getStateChange();
                    String text = item.getText();
                    MyJCheckBox box = checkBoxMap.get(text);
                    if (change == ItemEvent.SELECTED) {
                        // 单选
                        if (!conditionConfig.isMulti()) {
                            for (MyJCheckBox myJCheckBox : checkBoxList) {
                                if (!myJCheckBox.getConfig().getName().equals(text)) {
                                    myJCheckBox.setSelected(false);
                                }
                            }
                        } else {
                            // 多选
                            ButtonConfig config = box.getConfig();
                            // 独占
                            if (config.getExclusive()) {
                                for (MyJCheckBox myJCheckBox : checkBoxList) {
                                    if (!myJCheckBox.getConfig().getName().equals(text)) {
                                        myJCheckBox.setSelected(false);
                                    }
                                }
                            } else {
                                for (MyJCheckBox myJCheckBox : checkBoxList) {
                                    if (myJCheckBox.getConfig().getExclusive()) {
                                        myJCheckBox.setSelected(false);
                                    }
                                }
                            }
                        }
                    }
                }
            });
        }
    }

    public ConditionConfig getConditionConfig() {
        return conditionConfig;
    }
}
