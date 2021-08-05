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
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.List;

public class launcherUI_JavaFX_controlFrame_Controller {
    protected static final Minecraft MINECRAFT_CREATOR = new Minecraft(null, null, null, null, null, null);
    protected static final user USER_CREATOR = new offlineUser();
    private static final launcherUI_JavaFX_Controller CONTROLLER = launcherUI_JavaFX.controller;
    private static final Logger logger = Logger.getLogger(launcherUI_JavaFX_controlFrame_Controller.class);
    @FXML
    public ComboBox<Minecraft> versionList;
    @FXML
    private Label gameVersion;
    @FXML
    private ImageView gameIcon;
    @FXML
    private Label gameCotitle;
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
        addUser(USER_CREATOR);
    }

    protected user getSelectedUser() {
        return characterChooser.getSelectionModel().getSelectedItem();
    }

    protected void addUser(user user) {
        characterChooser.getItems().add(user);
    }

    protected void addUsers(List<? extends user> users) {
        characterChooser.getItems().addAll(users);
    }

    protected boolean isUsersEmpty() {
        return characterChooser.getSelectionModel().isEmpty();
    }

    @FXML
    void flushImage() {
        if(characterChooser.getSelectionModel().getSelectedItem() != USER_CREATOR)
            headTexture.setImage(characterChooser.getSelectionModel().getSelectedItem().getTexture().getHeadTexture());
    }

    @FXML
    void mcIconEnter() {
        gameIcon.setEffect(new Glow(0.5));
    }

    @FXML
    void mcIconExit() {
        gameIcon.setEffect(null);
    }
}

class userCell extends ComboBoxListCell<user> {
    @Override
    public void updateItem(user item, boolean empty) {
        if (!empty && item != null && item != launcherUI_JavaFX_controlFrame_Controller.USER_CREATOR) {
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
        } else if (item == launcherUI_JavaFX_controlFrame_Controller.USER_CREATOR) {
            BorderPane cell = new BorderPane();

            VBox textBox = new VBox(2);
            Text version = new Text("创建账户"); //如果版本号含有字母则标记为NotRelease
            version.setFont(launcherUI_JavaFX.FONT_COTITLE);
            Text info = new Text("点我创建账户");
            textBox.getChildren().addAll(version, info);

            /*HBox iconBox = new HBox(2);
            ImageView icon = null;
            Pane space = new Pane();
            space.setPrefWidth(10);
            iconBox.getChildren().addAll(icon, space);*/

            cell.setCenter(textBox);
//            cell.setLeft(iconBox);

            setGraphic(cell);
        }
    }
}

class minecraftCell extends ComboBoxListCell<Minecraft> {
    @Override
    public void updateItem(Minecraft item, boolean empty) {
        super.updateItem(item, empty);
        setText("");
        if (!empty && item != null && item != launcherUI_JavaFX_controlFrame_Controller.MINECRAFT_CREATOR) {
            BorderPane cell = new BorderPane();

            VBox textBox = new VBox(3);
            Text version = new Text(item.getVersion().getVersion() + " - " + item.getVersion().getType()); //如果版本号含有字母则标记为NotRelease
            version.setFont(javafx.scene.text.Font.font("DengXian", FontWeight.BOLD, 16));
            Text info;
            if (item.getConfig().getPackName() == null) {
                info = new Text(item.getSaves().size() + "个存档，" + item.getMods().size() + "个模组。");
            } else {
                info = new Text("整合包 - " + item.getConfig().getPackName());
            }
            Text date = new Text(launcherUI_JavaFX.DATE_FORMAT.format(new File(item.getRootPath()).lastModified()));
            date.setFont(javafx.scene.text.Font.font("DengXian", FontWeight.EXTRA_BOLD, 10));
            textBox.getChildren().addAll(version, info, date);

            HBox iconBox = new HBox(2);
            ImageView icon = new ImageView(this.getClass().getClassLoader().getResource("com/Sparrow/UI/JavaFX/imgs/mc_icon.png").toString());
            icon.setFitWidth(30);
            icon.setFitHeight(30);
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