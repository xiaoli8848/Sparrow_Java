package com.Sparrow.UI.JavaFX.controller;

import com.Sparrow.UI.JavaFX.launcherUI_JavaFX;
import com.Sparrow.Utils.user.libUser;
import com.Sparrow.Utils.user.offlineUser;
import com.Sparrow.Utils.user.onlineUser;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;

public class launcherUI_JavaFX_userCreatorTab_Controller {

    @FXML
    private Tab UserCreatorTab;

    @FXML
    private ToggleButton offlineUserType;

    @FXML
    private ToggleGroup userTypeChooser;

    @FXML
    private ToggleButton onlineUserType;

    @FXML
    private Pane offlineUserCreator;

    @FXML
    private TextField offlineUserCreator_Name;

    @FXML
    private JFXToggleButton offlineUserCreator_Texture;

    @FXML
    private Pane onlineUserCreator;

    @FXML
    private TextField onlineUserCreator_Email;

    @FXML
    private PasswordField onlineUserCreator_Password;

    @FXML
    private TextField onlineUserCreator_Server;

    @FXML
    private JFXButton addUserButton;

    public void install(){
        onlineUserCreator.setVisible(false);
        userTypeChooser.selectedToggleProperty().addListener((observableValue, toggle, t1) -> {
            if (userTypeChooser.getSelectedToggle() == onlineUserType) {
                offlineUserCreator.setVisible(false);
                onlineUserCreator.setVisible(true);
            } else {
                onlineUserCreator.setVisible(false);
                offlineUserCreator.setVisible(true);
            }
        });
    }

    @FXML
    void addUser(ActionEvent event) {
        if (userTypeChooser.getSelectedToggle() == offlineUserType) {
            if (!offlineUserCreator_Name.getText().isEmpty()) {
                try {
                    offlineUser temp;
                    if (offlineUserCreator_Texture.isArmed()) {
                        temp = new offlineUser(offlineUserCreator_Name.getText(), offlineUser.textureType.ALEX);
                    } else {
                        temp = new offlineUser(offlineUserCreator_Name.getText(), offlineUser.textureType.ALEX);
                    }
                    launcherUI_JavaFX.controller.controller_control.addUser(temp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                //TODO 离线用户 用户名为空
            }
        } else {
            if (!onlineUserCreator_Email.getText().isEmpty()) {
                if (!onlineUserCreator_Password.getText().isEmpty()) {
                    if (onlineUserCreator_Server.getText().isEmpty()) {
                        try {
                            onlineUser temp = new onlineUser(onlineUserCreator_Email.getText(), onlineUserCreator_Password.getText());
                            temp.getInfo();
                            launcherUI_JavaFX.controller.controller_control.addUser(temp);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            libUser temp = new libUser(onlineUserCreator_Email.getText(), onlineUserCreator_Password.getText(), onlineUserCreator_Server.getText());
                            temp.getInfo();
                            launcherUI_JavaFX.controller.controller_control.addUser(temp);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    //TODO 在线用户 密码为空
                }
            } else {
                //TODO 在线用户 用户名为空
            }
        }
    }

}
