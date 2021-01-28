package com.MQ.Tools;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class IO {
    public static void browseFile(String filePath) throws IOException {
        Desktop desktop = Desktop.getDesktop();
        desktop.browse(new File(filePath).toURI());
    }

    public static void gotoWebSite(String url) throws IOException {
        java.net.URI uri = java.net.URI.create(url);
        // 获取当前系统桌面扩展
        Desktop dp = Desktop.getDesktop();
        // 判断系统桌面是否支持要执行的功能
        if (dp.isSupported(Desktop.Action.BROWSE)) {
            dp.browse(uri);
            // 获取系统默认浏览器打开链接
        }
    }
}
