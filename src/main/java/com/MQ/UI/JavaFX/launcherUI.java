package com.MQ.UI.JavaFX;

import com.MQ.launcher;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author XiaoLi8848, 1662423349@qq.com
 * 本类中提供了JavaFX UI平台的{@link #main(String[])}程序入口，以采用JavaFX UI启动程序。同时，本类中的{@link #coverURL}等公开静态（常）量，为{@link launcherUI_Controller}类或者其它UI类提供必要的参数。
 */
public class launcherUI extends Application {
    public static final String coverURL = "http://xiaoli8848.usa3v.vip/JMCCC/";
    public static final String projectURL = "https://github.com/xiaoli8848/MQ";
    public static launcherUI_Controller controller;
    public static Stage primaryStage;
    public static Locale defaultLocale = Locale.getDefault();
    public static ResourceBundle resourceBundle = ResourceBundle.getBundle("UI/JavaFX/properties/UI-javafx", defaultLocale, launcher.class.getClassLoader());
    public static Parent root;
    public static void main(String[] args) {
        launch(args);
    }

    public static void launchGamer() {
        //TODO 替换rootDir和playerName
        controller.getSelectMC().launch(controller.getPlayerName(), false, true, controller.getMinMem(), controller.getMaxMem(), controller.getWidth(), controller.getHeight(), "");
    }

    public static String getResString(String name) {
        return resourceBundle.getString(name);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("MQ");
        //Parent root = FXMLLoader.load(com.MQ.launcher.class.getResource("launcherUI_h5.fxml"));
        FXMLLoader a = new FXMLLoader(getClass().getClassLoader().getResource("UI/JavaFX/launcherUI_javafx.fxml"));
        root = a.load();
        controller = a.getController();
        controller.Init();
        Scene scene = new Scene(root, 1000, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void gotoWebSite(String url) throws IOException{
        java.net.URI uri = java.net.URI.create(url);
        // 获取当前系统桌面扩展
        java.awt.Desktop dp = java.awt.Desktop.getDesktop();
        // 判断系统桌面是否支持要执行的功能
        if (dp.isSupported(java.awt.Desktop.Action.BROWSE)) {
            dp.browse(uri);
            // 获取系统默认浏览器打开链接
        }
    }
}
