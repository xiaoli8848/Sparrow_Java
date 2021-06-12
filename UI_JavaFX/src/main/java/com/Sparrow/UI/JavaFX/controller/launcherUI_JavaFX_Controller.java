/*
 * Copyright (c) 2021 Sparrow All Rights Reserved.
 * FileName: launcherUI_JavaFX_Controller.java
 * @author: 1662423349@qq.com
 * @date: 2021/6/12 下午9:17
 * @version: 1.0
 */

package com.Sparrow.UI.JavaFX.controller;

import com.Sparrow.UI.JavaFX.launcherUI_JavaFX;
import com.Sparrow.Utils.Callback.launchCallback;
import com.Sparrow.Utils.Minecraft;
import com.Sparrow.Utils.dialog.errDialog;
import com.Sparrow.Utils.mod;
import com.Sparrow.Utils.save;
import com.Sparrow.Utils.user.libUser;
import com.Sparrow.Utils.user.offlineUser;
import com.Sparrow.Utils.user.onlineUser;
import com.Sparrow.Utils.user.user;
import com.Sparrow.launcher;
import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.FontWeight;
import org.to2mbn.jmccc.exec.GameProcessListener;
import org.to2mbn.jmccc.launch.LaunchException;
import org.to2mbn.jmccc.option.MinecraftDirectory;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Stack;

/**
 * JFX主控制类。
 * 存储程序缓存目录 {@code public File TempPath = new File(ROOTDIR + ".Sparrow");}，主界面FXML中的控件，其它页面的node和controller实例。
 *
 * @author 1662423349@qq.com
 */
public class launcherUI_JavaFX_Controller {
    private final ArrayList<launcherState> states = new ArrayList<>();
    private static Logger logger = Logger.getLogger(launcherUI_JavaFX_Controller.class);
    @FXML
    private ImageView closeButton;

    @FXML
    private ImageView minisizeButton;

    @FXML
    private Button backButton;

    @FXML
    private Button homeButton;

    @FXML
    private Pane pagePane;

    @FXML
    protected Pane launchPane;

    @FXML
    protected JFXListView<save> gameSaves;

    @FXML
    protected JFXListView<mod> gameMods;

    @FXML
    private Label State;

    @FXML
    private Label Version;


    protected launcherUI_JavaFX_versionList_Controller controller_versionList;
    protected Node page_versionList;

    protected launcherUI_JavaFX_controlFrame_Controller controller_control;
    protected Node page_control;

    protected launcherUI_JavaFX_userCreator_Controller controller_userCreator;
    protected Node page_userCreator;

    private final Stack<Node> pages = new Stack<>();
    private int pointer_StateCreator = 0;

    private final launchCallback launchCallback = new launchCallback() {
        @Override
        public void onInstalling() {
        }

        @Override
        public void onResolvingOptions() {
        }

        @Override
        public void onLaunch() {
        }
    };

    public void install() {
        logger.info("控制类开始初始化。");

        launcher.gameProcessListener = new GameProcessListener() {
            //TODO 完善响应
            @Override
            public void onLog(String log) {
                System.out.println(log);
            }

            @Override
            public void onErrorLog(String log) {
                System.err.println(log);
            }

            @Override
            public void onExit(int code) {
                System.out.println("游戏退出：" + code);
            }
        };

        if (!launcher.TempPath.exists()) {
            launcher.TempPath.mkdirs();
        }
        for (File fileTemp : Objects.requireNonNull(launcher.TempPath.listFiles())) {
            fileTemp.delete();
        }
        Version.setText(launcherUI_JavaFX.VERSION_UI);
        launchPane.setVisible(false);
        //设置ListView视图模板类
        gameSaves.setCellFactory(param -> new savesCell());
        gameMods.setCellFactory(param -> new modsCell());

        //自动导入程序目录下的游戏
        try {
            logger.info("尝试载入当前目录下游戏。");
            Init(launcherUI_JavaFX.ROOTDIR + ".minecraft");
        } catch (Exception e) {
            logger.info("未能载入当前目录下游戏。");
        }

        if (!launcher.WorkPath.exists()) {
            logger.info("工作目录不存在，尝试创建：" + launcher.WorkPath.toString());
            if(launcher.WorkPath.mkdir()){
                logger.info("创建工作目录成功。");
            }else{
                logger.fatal("创建工作目录失败。");
                new errDialog().apply("致命错误",null,"不好意思，程序无法创建工作目录，无法启动。");
                System.exit(0);
            }
        }

        FXMLLoader fxmlLoader_versionList = new FXMLLoader();
        fxmlLoader_versionList.setLocation(getClass().getClassLoader().getResource("com/Sparrow/UI/JavaFX/versionlistFrame.fxml"));
        try {
            this.page_versionList = fxmlLoader_versionList.load();
            this.controller_versionList = fxmlLoader_versionList.getController();
            controller_versionList.install();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("加载versionList页面完毕。");

        FXMLLoader fxmlLoader_control = new FXMLLoader();
        fxmlLoader_control.setLocation(getClass().getClassLoader().getResource("com/Sparrow/UI/JavaFX/controlFrame.fxml"));
        try {
            this.page_control = fxmlLoader_control.load();
            this.controller_control = fxmlLoader_control.getController();
            controller_control.install();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("加载controlFrame页面完毕。");

        FXMLLoader fxmlLoader_userCreator = new FXMLLoader();
        fxmlLoader_userCreator.setLocation(getClass().getClassLoader().getResource("com/Sparrow/UI/JavaFX/userCreator.fxml"));
        try {
            this.page_userCreator = fxmlLoader_userCreator.load();
            this.controller_userCreator = fxmlLoader_userCreator.getController();
            controller_userCreator.install();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("加载userCreator页面完毕。");

        pagePane.getChildren().add(page_control);
        logger.info("控制器初始化完成。");
    }

    public void Init(String rootDir) throws InitException {
        logger.info("尝试载入游戏：" + rootDir);
        launcherState state_import = new launcherState(com.Sparrow.UI.JavaFX.launcherState.IMPORTING);
        if (new File(rootDir).getName() != ".minecraft") {
            File file = new File(rootDir + File.separator + ".minecraft");
            if (file.exists()) {
                logger.info("检测到下一级的游戏文件夹并尝试载入：" + file);
                Init(file.toString());
                return;
            }
        } else {
            logger.info("未检测到游戏文件夹");
            deleteState(state_import);
            throw new InitException("非Minecraft文件夹");
        }
        addState(state_import);
        File test = new File(rootDir);
        if (!test.exists() || !test.isDirectory()) {
            deleteState(state_import);
            throw new InitException("路径无效");
        }
        Minecraft[] minecrafts = Minecraft.getMinecrafts(new MinecraftDirectory(rootDir));
        controller_versionList.addItems(Arrays.asList(minecrafts));
        for (Minecraft minecraft : minecrafts) {
            if (minecraft.getConfig().haveUsers()) {
                controller_control.addItems(minecraft.getConfig().getOnlineUsers());
                controller_control.addItems(minecraft.getConfig().getOfflineUsers());
                controller_control.addItems(minecraft.getConfig().getLibUsers());
            }
        }
        deleteState(state_import);
        logger.info("成功载入" + minecrafts.length + "个游戏。");
    }

    public void addState(launcherState state) {
        logger.info("启动器状态添加：" + state.toString());
        states.add(state);
        refreshState();
    }

    public void deleteState(launcherState state) {
        logger.info("启动器状态消除：" + state.toString());
        deleteState(state.serialNumber);
    }

    public void deleteState(int serialNumber) {
        for (int i = 0; i < states.size(); i++) {
            if (states.get(i).serialNumber == serialNumber) {
                logger.info("启动器状态消除：" + states.get(i).toString());
                states.remove(i);
                refreshState();
                return;
            }
        }
    }

    private void refreshState() {
        if (states.size() != 0) {
            String result = "";
            for (launcherState state : states) {
                result += state.toString() + "；";
            }
            result = result.substring(0, result.length() - 1) + "。";
            State.setText(result);
        } else {
            State.setText(com.Sparrow.UI.JavaFX.launcherState.STANDBY.toString());
        }
    }

    @FXML
    void windowButtonActive(MouseEvent event) {
        closeButton.setImage(new Image(getClass().getClassLoader().getResource("com/Sparrow/UI/JavaFX/imgs/WindowCloseButton_Active.png").toString()));
        minisizeButton.setImage(new Image(getClass().getClassLoader().getResource("com/Sparrow/UI/JavaFX/imgs/WindowMinsizeButton_Active.png").toString()));
    }

    @FXML
    void windowButtonCancel(MouseEvent event) {
        closeButton.setImage(new Image(getClass().getClassLoader().getResource("com/Sparrow/UI/JavaFX/imgs/WindowCloseButton.png").toString()));
        minisizeButton.setImage(new Image(getClass().getClassLoader().getResource("com/Sparrow/UI/JavaFX/imgs/WindowMinsizeButton.png").toString()));
    }

    @FXML
    void closeWindow(MouseEvent event) {
        for(File temp:launcher.TempPath.listFiles()){
            temp.delete();
        }
        try {
            controller_versionList.getSelectedItem().getConfig().flush();
        } catch (Exception e) {
        }
        System.exit(0);
    }

    @FXML
    void minsizeWindow(MouseEvent event) {
        launcherUI_JavaFX.primaryStage.setIconified(true);
    }

    @FXML
    void launch() {
        if (controller_control.isEmpty()) {
            new errDialog().apply("参数错误", null, "不好意思，你需要选择一个账户才能启动。");
        } else if (controller_versionList.isEmpty()) {
            new errDialog().apply("参数错误", null, "不好意思，你需要选择一个游戏版本才能启动。");
        } else {
            user selectedUser = controller_control.getSelectedItem();
            if (selectedUser instanceof offlineUser) {
                try {
                    controller_versionList.getSelectedItem().launch(launchCallback, selectedUser.getAuthenticator(), true, true, 0, 0, 1000, 800, "");
                } catch (LaunchException e) {
                    e.printStackTrace();
                }
            } else if (selectedUser instanceof onlineUser) {
                ((onlineUser) selectedUser).getInfo();
                try {
                    controller_versionList.getSelectedItem().launch(launchCallback, selectedUser.getAuthenticator(), true, true, 0, 0, 1000, 800, "");
                } catch (LaunchException e) {
                    e.printStackTrace();
                }
            } else if (selectedUser instanceof libUser) {
                ((libUser) selectedUser).getInfo();
                try {
                    controller_versionList.getSelectedItem().launch(launchCallback, selectedUser.getAuthenticator(), true, true, 0, 0, 1000, 800, "");
                } catch (LaunchException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    void home() {
        homeButton.setDisable(true);
        pages.push(pagePane.getChildren().get(0));
        pagePane.getChildren().remove(0);
        pagePane.getChildren().add(page_control);
    }

    protected void Goto(Node page) {
        if (page == page_control) {
            home();
            return;
        }
        pages.push(pagePane.getChildren().get(0));
        pagePane.getChildren().remove(0);
        pagePane.getChildren().add(page);
        backButton.setDisable(false);
        homeButton.setDisable(false);
    }

    @FXML
    void back() {
        Node backer = pages.pop();
        if (pages.empty()) {
            backButton.setDisable(true);
        }
        if (backer == page_control) {
            home();
            return;
        }
        pagePane.getChildren().remove(0);
        pagePane.getChildren().add(backer);
    }

    public class launcherState {
        private final com.Sparrow.UI.JavaFX.launcherState state;
        public int serialNumber;

        public launcherState(com.Sparrow.UI.JavaFX.launcherState state) {
            this.state = state;
            this.serialNumber = pointer_StateCreator++;
        }

        @Override
        public String toString() {
            return state.toString();
        }
    }
}

class savesCell extends JFXListCell<save> {
    @Override
    public void updateItem(save item, boolean empty) {
        super.updateItem(item, empty);
        setText("");
        if (empty) {
            setText(null);
            setGraphic(null);
            setMouseTransparent(true);
            setStyle("-fx-background-color:TRANSPARENT;");
        } else {
            setMouseTransparent(false);
            setStyle(null);
            BorderPane cell = new BorderPane();
            ImageView imageView = new ImageView(new Image("file:" + File.separator + item.getImage().toString()));
            HBox imageBox = new HBox(2);
            Pane space = new Pane();
            space.setPrefWidth(10);
            imageBox.getChildren().addAll(imageView, space);

            VBox textBox = new VBox(2);
            Label saveName = new Label(item.getName());
            saveName.setFont(javafx.scene.text.Font.font("DengXian", FontWeight.BOLD, 14));
            Label modDate = new Label(launcherUI_JavaFX.DATE_FORMAT.format(new File(item.toString()).lastModified()));
            modDate.setFont(javafx.scene.text.Font.font("DengXian", FontWeight.NORMAL, 8));
            textBox.getChildren().addAll(saveName, modDate);

            cell.setCenter(textBox);
            cell.setLeft(imageBox);

            setGraphic(cell);
        }
    }
}

class modsCell extends JFXListCell<mod> {
    @Override
    public void updateItem(mod item, boolean empty) {
        super.updateItem(item, empty);
        setText("");
        if (!empty && item != null) {
            BorderPane cell = new BorderPane();
            Label title = new Label(item.getName());

            Label modDate = new Label(launcherUI_JavaFX.DATE_FORMAT.format(new File(item.toString()).lastModified()));
            modDate.setFont(javafx.scene.text.Font.font("DengXian", FontWeight.NORMAL, 8));

            cell.setTop(title);
            cell.setLeft(modDate);

            setGraphic(cell);
        } else if (empty) {
            setText(null);
            setGraphic(null);
        }
    }
}

class InitException extends Exception {
    protected InitException() {
        super();
    }

    protected InitException(String message) {
        super(message);
    }
}