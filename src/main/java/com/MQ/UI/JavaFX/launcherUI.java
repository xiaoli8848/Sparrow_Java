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
    public static launcherUI_Controller controller;
    public static Stage primaryStage;
    public static Locale defaultLocale = Locale.getDefault();
    public static ResourceBundle resourceBundle = ResourceBundle.getBundle("UI/JavaFX/properties/UI-javafx", defaultLocale, launcher.class.getClassLoader());

    public static void main(String[] args) {
        launch(args);
    }

    public static void launchGamer() {
        //TODO 替换rootDir和playerName
        launcher.launch_offline("D:/Minecraft1.12.2/.minecraft", "XiaoLi8848", true, true, 0, 0, 500, 500, "");
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
        Parent root = a.load();
        controller = a.getController();
        controller.Init();
        Scene scene = new Scene(root, 1000, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
