package com.MQ.UI.JavaFX;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class launcherUI_dialog extends Application {

    private static Stage primaryStage;

    public static void main(String[] args){
        launch(args);
    }
    public static void showDialog(dialogType type, String title, String text){
        primaryStage = new Stage();
        primaryStage.setTitle(title);
        FXMLLoader a = new FXMLLoader(launcherUI_dialog.class.getClassLoader().getResource("UI/JavaFX/launcherUI_javafx_dialog.fxml"));
        Parent root = null;
        try {
            root = a.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        launcherUI_dialog_controller controller;
        controller = a.getController();
        controller.init(type,title,text);
        Scene scene = new Scene(root, 450, 200);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(null);
        primaryStage.show();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        showDialog(dialogType.INFO_OK,"欢迎","欢迎使用MQ。");
    }

    protected static void close(){
        primaryStage.close();
    }
}
