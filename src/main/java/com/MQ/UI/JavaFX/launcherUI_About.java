package com.MQ.UI.JavaFX;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.MQ.UI.JavaFX.launcherUI_About_Controller;
import java.io.IOException;

public class launcherUI_About extends Application {
    public Stage primaryStage;
    private launcherUI_About_Controller controller;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("MQ - About");
        FXMLLoader a = new FXMLLoader(getClass().getClassLoader().getResource("UI/JavaFX/launcherUI_javafx_about.fxml"));
        Parent root = a.load();
        controller = a.getController();
        controller.Init();
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
