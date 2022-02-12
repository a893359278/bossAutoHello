package com.csp.boss.hello.view;

import java.awt.*;

public class ViewUtil {

    public static final int windowWidth = 800;
    public static final int windowHeight = 600;
    public static final int panelItemHeight = 100;




    public static Point getCenterPoint(int containerWidth, int containerHeight) {
        // 获得显示器大小对象
        Dimension displaySize = Toolkit.getDefaultToolkit().getScreenSize();

        // 窗口的宽度不能大于显示器的宽度
        if (containerWidth > displaySize.width) {
            containerWidth = displaySize.width;
        }

        return new Point((displaySize.width - containerWidth) / 2, (displaySize.height - containerHeight) / 2);
    }
}
