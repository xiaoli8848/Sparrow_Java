package com.Sparrow.UI.JavaFX.controller;

import com.Sparrow.UI.JavaFX.launcherUI_JavaFX;
import com.Sparrow.Utils.Callback.launchCallback;
import com.Sparrow.Utils.Minecraft;
import com.Sparrow.Utils.dialog.errDialog;
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
    @FXML
    protected ImageView closeButton;

    @FXML
    protected ImageView minisizeButton;

    @FXML
    protected Pane pagePane;

    @FXML
    protected Pane launchPane;

    @FXML
    protected TabPane versionSummary;

    @FXML
    protected JFXListView<Minecraft.save> gameSaves;

    @FXML
    protected JFXListView<Minecraft.mod> gameMods;

    @FXML
    protected Label State;

    @FXML
    protected Label Version;

    @FXML
    protected Button homeButton;

    @FXML
    protected Button backButton;

    @FXML
    private ProgressBar launchProcess;

    @FXML
    private JFXTextArea launchLog;


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
            for (int i = 0; i < 200; i++) {
                launchProcess.setProgress(launchProcess.getProgress() + 0.001);
            }
        }

        @Override
        public void onResolvingOptions() {
            for (int i = 0; i < 600; i++) {
                launchProcess.setProgress(launchProcess.getProgress() + 0.001);
            }
        }

        @Override
        public void onLaunch() {
            launchProcess.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        }
    };

    public void install() {
        launcher.gameProcessListener = new GameProcessListener() {
            //TODO 完善响应
            @Override
            public void onLog(String log) {
                launchLog.appendText(log + "\n");
            }

            @Override
            public void onErrorLog(String log) {
                launchLog.appendText(log + "\n");
            }

            @Override
            public void onExit(int code) {
                Platform.runLater(()->launchProcess.setProgress(0));
                if(code == 0){
                    launchLog.appendText("游戏正常退出。" + "\n");
                }else{
                    launchLog.appendText("抱歉，游戏非正常退出。代码：" + code + "。" +"\n");
                }
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
            Init(launcherUI_JavaFX.ROOTDIR + ".minecraft");
        } catch (Exception e) {

        }

        if (!launcher.WorkPath.exists()) {
            launcher.WorkPath.mkdir();
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

        FXMLLoader fxmlLoader_control = new FXMLLoader();
        fxmlLoader_control.setLocation(getClass().getClassLoader().getResource("com/Sparrow/UI/JavaFX/controlFrame.fxml"));
        try {
            this.page_control = fxmlLoader_control.load();
            this.controller_control = fxmlLoader_control.getController();
            controller_control.install();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FXMLLoader fxmlLoader_userCreator = new FXMLLoader();
        fxmlLoader_userCreator.setLocation(getClass().getClassLoader().getResource("com/Sparrow/UI/JavaFX/userCreator.fxml"));
        try {
            this.page_userCreator = fxmlLoader_userCreator.load();
            this.controller_userCreator = fxmlLoader_userCreator.getController();
            controller_userCreator.install();
        } catch (IOException e) {
            e.printStackTrace();
        }

        pagePane.getChildren().add(page_control);
    }

    public void Init(String rootDir) throws InitException {
        launcherState state_import = new launcherState(com.Sparrow.UI.JavaFX.launcherState.IMPORTING);
        if (new File(rootDir).getName() != ".minecraft") {
            File file = new File(rootDir + File.separator + ".minecraft");
            if (file.exists()) {
                Init(file.toString());
                return;
            }
        } else {
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
    }

    public void addState(launcherState state) {
        states.add(state);
        refreshState();
    }

    public void deleteState(launcherState state) {
        deleteState(state.serialNumber);
    }

    public void deleteState(int serialNumber) {
        for (int i = 0; i < states.size(); i++) {
            if (states.get(i).serialNumber == serialNumber) {
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
        } catch (IOException exception) {
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
            launchLog.setText("");
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

class savesCell extends JFXListCell<Minecraft.save> {
    @Override
    public void updateItem(Minecraft.save item, boolean empty) {
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
            ImageView imageView = new ImageView(new Image("file:" + File.separator + item.image.toString()));
            HBox imageBox = new HBox(2);
            Pane space = new Pane();
            space.setPrefWidth(10);
            imageBox.getChildren().addAll(imageView, space);

            VBox textBox = new VBox(2);
            Label saveName = new Label(item.name);
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

class modsCell extends JFXListCell<Minecraft.mod> {
    @Override
    public void updateItem(Minecraft.mod item, boolean empty) {
        super.updateItem(item, empty);
        setText("");
        if (!empty && item != null) {
            BorderPane cell = new BorderPane();
            Label title = new Label(item.name);

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