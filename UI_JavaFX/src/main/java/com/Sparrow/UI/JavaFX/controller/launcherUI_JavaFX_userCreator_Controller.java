/*
 * Copyright (c) 2021 Sparrow All Rights Reserved.
 * FileName: launcherUI_JavaFX_userCreator_Controller.java
 * @author: 1662423349@qq.com
 * @date: 2021/6/12 下午9:17
 * @version: 1.0
 */

package com.Sparrow.UI.JavaFX.controller;

import com.Sparrow.UI.JavaFX.launcherUI_JavaFX;
import com.Sparrow.Utils.dialog.errDialog;
import com.Sparrow.Utils.user.libUser;
import com.Sparrow.Utils.user.offlineUser;
import com.Sparrow.Utils.user.onlineUser;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class launcherUI_JavaFX_userCreator_Controller {
    public static final launcherUI_JavaFX_Controller CONTROLLER = launcherUI_JavaFX.controller;
    protected ToggleGroup signWay = new ToggleGroup();

    @FXML
    private ToggleButton offlineSign;

    @FXML
    private ToggleButton onlineSign;

    @FXML
    private ToggleButton libSign;

    @FXML
    private VBox offlinePane;

    @FXML
    private JFXTextField userName_Offline;

    @FXML
    private VBox onlinePane;

    @FXML
    private JFXTextField userName_Online;

    @FXML
    private JFXPasswordField password_Online;

    @FXML
    private VBox libPane;

    @FXML
    private JFXTextField userName_Lib;

    @FXML
    private JFXPasswordField password_Lib;

    @FXML
    private JFXTextField server_Lib;

    protected void install(){
        signWay.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) -> {
            if (new_toggle == offlineSign) {
                offlinePane.setDisable(false);
                onlinePane.setDisable(true);
                libPane.setDisable(true);
            } else if (new_toggle == onlineSign) {
                offlinePane.setDisable(true);
                libPane.setDisable(true);
                onlinePane.setDisable(false);
            } else {
                offlinePane.setDisable(true);
                libPane.setDisable(false);
                onlinePane.setDisable(true);
            }
        });


        //设置ToggleGroup
        offlineSign.setToggleGroup(signWay);
        onlineSign.setToggleGroup(signWay);
        libSign.setToggleGroup(signWay);

        signWay.selectToggle(offlineSign);
    }

    @FXML
    void addUser_Offline() {
        if(CONTROLLER.controller_versionList.getSelectedItem() == null){
            new errDialog().apply("参数错误", null, "不好意思，创建账户前需要选择游戏。");
            return;
        }
        if (userName_Offline.getText().length() > 0) {
            offlineUser temp = new offlineUser(userName_Offline.getText());
            CONTROLLER.controller_control.addItem(temp);
            try {
                CONTROLLER.controller_control.addItem(temp);
                CONTROLLER.controller_versionList.getSelectedItem().getConfig().putOfflineUserInfo(temp);
                CONTROLLER.controller_versionList.getSelectedItem().getConfig().flush();
            } catch (Exception e) {
                new errDialog().apply("参数错误", null, "不好意思，尝试创建离线账户时遇到错误。");
            }
        }
    }

    @FXML
    void addUser_Online() {
        if(CONTROLLER.controller_versionList.getSelectedItem() == null){
            new errDialog().apply("参数错误", null, "不好意思，创建账户前需要选择游戏。");
            return;
        }
        if (userName_Online.getText().length() > 0 && password_Online.getText().length() > 0) {
            onlineUser temp = new onlineUser(userName_Online.getText(), password_Online.getText());
            try {
                if (temp.getInfo()) {
                    CONTROLLER.controller_control.addItem(temp);
                } else {
                    new errDialog().apply("参数错误", null, "不好意思，尝试创建在线账户时遇到错误。");
                    return;
                }
                CONTROLLER.controller_control.addItem(temp);
                CONTROLLER.controller_versionList.getSelectedItem().getConfig().putOnlineUserInfo(temp);
                CONTROLLER.controller_versionList.getSelectedItem().getConfig().flush();
            } catch (Exception e) {
                new errDialog().apply("参数错误", null, "不好意思，尝试创建在线账户时遇到错误。");
            }
        }
    }

    @FXML
    void addUser_Lib() {
        if(CONTROLLER.controller_versionList.getSelectedItem() == null){
            new errDialog().apply("参数错误", null, "不好意思，创建账户前需要选择游戏。");
            return;
        }
        if (userName_Lib.getText().length() > 0 && password_Lib.getText().length() > 0 && server_Lib.getText().length() > 0) {
            libUser temp = new libUser(userName_Lib.getText(), password_Lib.getText(), server_Lib.getText());
            try {
                if (temp.getInfo()) {
                    CONTROLLER.controller_control.addItem(temp);
                } else {
                    new errDialog().apply("参数错误", null, "不好意思，尝试创建外置登录账户时遇到错误。");
                    return;
                }
                CONTROLLER.controller_control.addItem(temp);
                CONTROLLER.controller_versionList.getSelectedItem().getConfig().putLibUserInfo(temp);
                CONTROLLER.controller_versionList.getSelectedItem().getConfig().flush();
            }catch (Exception e){
                new errDialog().apply("参数错误", null, "不好意思，尝试创建在线账户时遇到错误。");
            }
        }
    }
}
