package com.MQ.UI.H5;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class launcherUI_JavaFX extends Application {
    public static launcherUI_Controller_JavaFX controller;
    private static Stage primaryStage;

    public static void main(String[] args) {
        Application.launch(launcherUI_JavaFX.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        launcherUI_JavaFX.primaryStage = primaryStage;
        primaryStage.setTitle("MQ");
        //Parent root = FXMLLoader.load(com.MQ.launcher.class.getResource("launcherUI_h5.fxml"));
        FXMLLoader a = new FXMLLoader(getClass().getClassLoader().getResource("UI/H5/launcherUI_h5.fxml"));
        Parent root = a.load();
        controller = a.getController();
        Scene scene = new Scene(root, 1000, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
