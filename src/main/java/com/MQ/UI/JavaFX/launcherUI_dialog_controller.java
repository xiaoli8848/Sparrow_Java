package com.MQ.UI.JavaFX;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

import static com.MQ.UI.JavaFX.launcherUI_dialog.close;

public class launcherUI_dialog_controller {
    @FXML
    private ImageView icon;

    @FXML
    private Label text;

    @FXML
    private Label title;

    protected void init(dialogType type, String title, String text){
        switch (type){
            case INFO_OK:
                icon.setImage(new Image(String.valueOf(getClass().getClassLoader().getResource("UI/JavaFX/imgs/info.png"))));
                this.text.setText(text);
                this.title.setText(title);
        }
    }

    @FXML
    void OK(){
        close();
    }
}
