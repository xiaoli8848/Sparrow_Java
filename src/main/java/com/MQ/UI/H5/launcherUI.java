package com.MQ.UI.H5;

import com.MQ.launcher;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class launcherUI extends Application {
    public static final String UI_H5 = "http://xiaoli8848.usa3v.vip/JMCCC/";
    public static launcherUI_Controller controller;
    private static Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    public static void launchGamer() {
        //TODO 替换rootDir和playerName
        launcher.launch_offline("D:/Minecraft1.12.2/.minecraft", "XiaoLi8848", true, true, 0, 0, 500, 500, "");
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("MQ");
        //Parent root = FXMLLoader.load(com.MQ.launcher.class.getResource("launcherUI_h5.fxml"));
        FXMLLoader a = new FXMLLoader(launcher.class.getClassLoader().getResource("launcherUI_h5.fxml"));
        Parent root = a.load();
        controller = a.getController();
        controller.Init();
        Scene scene = new Scene(root, 1000, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
