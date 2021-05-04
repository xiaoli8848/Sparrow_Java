package com.Sparrow.UI.JavaFX;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.text.SimpleDateFormat;

public class launcherUI_JavaFX extends Application {
    public static Stage primaryStage;
    public static launcherUI_JavaFX_Controller controller;
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final String VERSION_UI = "Sparrow Alpha-V0.4.0";

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getClassLoader().getResource("com/Sparrow/UI/JavaFX/mainFrame.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        scene.setFill(null);
        primaryStage.getIcons().add(new Image(getClass().getClassLoader().getResource("com/Sparrow/UI/JavaFX/imgs/icon.png").toString()));
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        launcherUI_JavaFX.primaryStage = primaryStage;
        controller = fxmlLoader.getController();
        primaryStage.show();
        controller.install();
    }
}
