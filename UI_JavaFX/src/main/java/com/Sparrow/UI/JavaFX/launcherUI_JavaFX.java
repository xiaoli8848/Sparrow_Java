package com.Sparrow.UI.JavaFX;

import com.Sparrow.UI.JavaFX.controller.launcherUI_JavaFX_Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.text.SimpleDateFormat;

public class launcherUI_JavaFX extends Application {
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final String VERSION_UI = "Sparrow Alpha-V0.4.0";
    public static final Font FONT_COTITLE = Font.font("DengXian", FontWeight.BOLD, 16);
    public static final String ROOTDIR = System.getProperty("user.dir") + File.separator;
    public static Stage primaryStage;
    public static launcherUI_JavaFX_Controller controller;

    /*protected static final Color COLOR_MAIN_BLUE = new Color(207,241,255,100);
    protected static final Color COLOR_MAIN_BLUE_DARK = new Color(136,166,179,70);
    protected static final Color COLOR_CO_GREEN = new Color(207,255,215,100);
    protected static final Color COLOR_CO_GREEN_DARK = new Color(136,179,143,70);
    protected static final Color COLOR_CO_PINK = new Color(255,228,220,100);*/

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
