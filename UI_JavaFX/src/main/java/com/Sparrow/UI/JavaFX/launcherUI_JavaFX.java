package com.Sparrow.UI.JavaFX;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.text.SimpleDateFormat;

public class launcherUI_JavaFX extends Application {
    public static Stage primaryStage;
    public static launcherUI_JavaFX_Controller controller;
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getClassLoader().getResource("com/Sparrow/UI/JavaFX/mainFrame.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        scene.setFill(null);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        launcherUI_JavaFX.primaryStage = primaryStage;
        controller = fxmlLoader.getController();
        controller.install();
        primaryStage.show();
    }
}
