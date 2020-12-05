package com.MQ.UI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.MQ.launcher;

import java.io.IOException;

public class launcherUI extends Application {
    private static Stage primaryStage;
    public static launcherUI_Controller controller;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException{
        this.primaryStage = primaryStage;
        primaryStage.setTitle("MQ");
        //Parent root = FXMLLoader.load(com.MQ.launcher.class.getResource("launcherUI_main.fxml"));
        FXMLLoader a = new FXMLLoader(launcher.class.getClassLoader().getResource("launcherUI_main.fxml"));
        Parent root = a.load();
        controller = a.getController();
        controller.Init();
        Scene scene = new Scene(root,1000,800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void launchGamer(){
        launcher.launch_offline(controller.getSelctMC().path,controller.getPlayerName(),true,true,0,0,500,500,"");
    }
}
