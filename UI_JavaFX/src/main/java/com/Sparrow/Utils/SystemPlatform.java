/*
 * Copyright (c) 2021 Sparrow All Rights Reserved.
 * FileName: SystemPlatform.java
 * @author: 1662423349@qq.com
 * @date: 2021/6/12 下午9:17
 * @version: 1.0
 */

package com.Sparrow.Utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class SystemPlatform {
    public static void displayTray(String toolTip, String caption, String text) throws AWTException {
        //Obtain only one instance of the SystemTray object
        SystemTray tray = SystemTray.getSystemTray();

        //If the icon is a file
        Image image = null;
        try {
            image = ImageIO.read(SystemPlatform.class.getClassLoader().getResource("com/Sparrow/UI/JavaFX/imgs/icon.png"));
        } catch (IOException e) {
            image = Toolkit.getDefaultToolkit().createImage("icon.png");
        }
        //Alternative (if the icon is on the classpath):
        //Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("icon.png"));

        TrayIcon trayIcon = new TrayIcon(image, toolTip);
        //Let the system resize the image if needed
        trayIcon.setImageAutoSize(true);
        //Set tooltip text for the tray icon
        trayIcon.setToolTip(toolTip);
        tray.add(trayIcon);
        trayIcon.displayMessage(caption, text, TrayIcon.MessageType.INFO);
    }

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