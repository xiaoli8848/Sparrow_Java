package com.MQ.Tools;

import java.awt.*;

public class SystemPlatform {
    public static void displayTray(String toolTip, String caption, String text) throws AWTException {
        //Obtain only one instance of the SystemTray object
        SystemTray tray = SystemTray.getSystemTray();

        //If the icon is a file
        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
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
}