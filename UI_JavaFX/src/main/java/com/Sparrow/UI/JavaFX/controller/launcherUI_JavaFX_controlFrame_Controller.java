/*
 * Copyright (c) 2021 Sparrow All Rights Reserved.
 * FileName: launcherUI_JavaFX_controlFrame_Controller.java
 * @author: 1662423349@qq.com
 * @date: 2021/6/12 下午9:17
 * @version: 1.0
 */

package com.Sparrow.UI.JavaFX.controller;

import com.Sparrow.UI.JavaFX.launcherUI_JavaFX;
import com.Sparrow.Utils.Minecraft;
import com.Sparrow.Utils.user.offlineUser;
import com.Sparrow.Utils.user.onlineUser;
import com.Sparrow.Utils.user.user;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListCell;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.List;

public class launcherUI_JavaFX_controlFrame_Controller {
    private static launcherUI_JavaFX_Controller CONTROLLER = launcherUI_JavaFX.controller;
    @FXML
    private Label gameVersion;

    @FXML
    private ImageView gameIcon;

    @FXML
    private Label gameCotitle;

    @FXML
    public ComboBox<Minecraft> versionList;

    @FXML
    private ImageView headTexture;

    @FXML
    private ComboBox<user> characterChooser;

    protected void install() {
        characterChooser.setCellFactory(param -> new userCell());
        versionList.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Minecraft> observable, Minecraft oldValue, Minecraft newValue) -> {
            gameVersion.setText(newValue.getVersion().getVersion());
            gameCotitle.setText(newValue.getVersion().getType());
        });
    }

    protected user getSelectedItem() {
        return characterChooser.getSelectionModel().getSelectedItem();
    }

    protected void addItem(user user) {
        characterChooser.getItems().add(user);
    }

    protected void addItems(List<? extends user> users) {
        characterChooser.getItems().addAll(users);
    }

    protected boolean isEmpty() {
        return characterChooser.getSelectionModel().isEmpty();
    }

    @FXML
    void flushImage(){
        headTexture.setImage(characterChooser.getSelectionModel().getSelectedItem().getTexture().getHeadTexture());
    }

    @FXML
    void gotoUserCreator(){
        CONTROLLER.Goto(CONTROLLER.page_userCreator);
    }

    @FXML
    void mcIconEnter(){
        gameIcon.setEffect(new Glow(0.5));
    }

    @FXML
    void mcIconExit(){
        gameIcon.setEffect(null);
    }
}

class userCell extends JFXListCell<user> {
    @Override
    public void updateItem(user item, boolean empty) {
        super.updateItem(item, empty);
        setText("");
        if (!empty && item != null) {
            BorderPane cell = new BorderPane();

            VBox textBox = new VBox(2);
            Text version = new Text(item.getUserName()); //如果版本号含有字母则标记为NotRelease
            version.setFont(launcherUI_JavaFX.FONT_COTITLE);
            Text info = new Text(item instanceof offlineUser ? "离线登录" : item instanceof onlineUser ? "在线登录" : "外置登录");
            textBox.getChildren().addAll(version, info);

            HBox iconBox = new HBox(2);
            ImageView icon = null;
            try {
                ImageView icon1 = new ImageView(item.getTexture().getHeadTexture());
                icon1.setFitWidth(40);
                icon1.setFitHeight(40);
                icon = icon1;
            } catch (Exception e) {
                e.printStackTrace();
            }
            Pane space = new Pane();
            space.setPrefWidth(10);
            iconBox.getChildren().addAll(icon, space);

            cell.setCenter(textBox);
            cell.setLeft(iconBox);

            setGraphic(cell);
        } else if (empty) {
            setText(null);
            setGraphic(null);
        }
    }
}