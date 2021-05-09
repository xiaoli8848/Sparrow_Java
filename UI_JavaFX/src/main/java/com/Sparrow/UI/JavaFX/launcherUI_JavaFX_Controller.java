package com.Sparrow.UI.JavaFX;

import com.Sparrow.Utils.MinecraftJFX;
import com.Sparrow.Utils.dialog.errDialog;
import com.Sparrow.Utils.user.libUser;
import com.Sparrow.Utils.user.offlineUser;
import com.Sparrow.Utils.user.onlineUser;
import com.Sparrow.Utils.user.user;
import com.jfoenix.controls.*;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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
 * @author 1662423349@qq.com
 */
public class launcherUI_JavaFX_Controller {
    protected static final Font FONT_COTITLE = Font.font("DengXian", FontWeight.BOLD, 16);
    protected static final String ROOTDIR = System.getProperty("user.dir") + File.separator;
    public File TempPath = new File(ROOTDIR + ".Sparrow");
    private Stack<Node> pages = new Stack<>();

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
    protected JFXListView<MinecraftJFX.save> gameSaves;

    @FXML
    protected JFXListView<MinecraftJFX.mod> gameMods;

    @FXML
    protected ToggleButton offlineSign;

    @FXML
    protected ToggleButton onlineSign;

    @FXML
    protected ToggleButton libSign;

    @FXML
    protected VBox offlinePane;

    @FXML
    protected JFXTextField userName_Offline;

    @FXML
    protected VBox onlinePane;

    @FXML
    protected JFXTextField userName_Online;

    @FXML
    protected JFXPasswordField password_Online;

    @FXML
    protected VBox libPane;

    @FXML
    protected JFXTextField userName_Lib;

    @FXML
    protected JFXPasswordField password_Lib;

    @FXML
    protected JFXTextField server_Lib;

    @FXML
    protected Label State;

    @FXML
    protected Label Version;

    @FXML
    protected Button homeButton;

    @FXML
    protected Button backButton;

    protected ToggleGroup signWay = new ToggleGroup();
    private int pointer_StateCreator = 0;
    private final ArrayList<launcherState> states = new ArrayList<>();

    protected launcherUI_JavaFX_versionList_Controller controller_versionList;
    protected Node page_versionList;

    protected launcherUI_JavaFX_controlFrame_Controller controller_control;
    protected Node page_control;

    public void install() {
        if (!TempPath.exists()) {
            TempPath.mkdirs();
        }
        for (File fileTemp : Objects.requireNonNull(TempPath.listFiles())) {
            fileTemp.delete();
        }
        Version.setText(launcherUI_JavaFX.VERSION_UI);
        launchPane.setVisible(false);
        //设置ListView视图模板类
        gameSaves.setCellFactory(param -> new savesCell());
        gameMods.setCellFactory(param -> new modsCell());


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


        //自动导入程序目录下的游戏
        try {
            Init(ROOTDIR + ".minecraft");
        } catch (Exception e) {

        }

        if (!TempPath.exists()) {
            TempPath.mkdir();
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
        MinecraftJFX[] minecrafts = MinecraftJFX.getMinecrafts(new MinecraftDirectory(rootDir));
        controller_versionList.addItems(Arrays.asList(minecrafts));
        for (MinecraftJFX minecraft : minecrafts) {
            if (minecraft.config.haveUsers()) {
                controller_control.addItems(minecraft.config.getOnlineUsers());
                controller_control.addItems(minecraft.config.getOfflineUsers());
                controller_control.addItems(minecraft.config.getLibUsers());
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
        System.exit(0);
    }

    @FXML
    void minsizeWindow(MouseEvent event) {
        launcherUI_JavaFX.primaryStage.setIconified(true);
    }

    @FXML
    void launch() {
        /*if (sw == signWays.Offline)
            versionList.getSelectionModel().getSelectedItem().launchOffline(nickName.getText(), true, true, 0, 0, 1000, 800, "");
        else {
            if (userName.getText() != "") {
                if (password.getText() != "") {
                    try {
                        YggdrasilAuthenticator_JavaFX onlineAuth = YggdrasilAuthenticator_JavaFX.password(userName.getText(), password.getText());
                        org.to2mbn.jmccc.launch.Launcher launcher = LauncherBuilder.create()
                                .setDebugPrintCommandline(false)
                                .setNativeFastCheck(true)
                                .build();

                        LaunchOption option = null;
                        try {
                            option = new LaunchOption(
                                    versionList.getSelectionModel().getSelectedItem().version, // 游戏版本
                                    onlineAuth, // 使用在线验证
                                    new MinecraftDirectory(versionList.getSelectionModel().getSelectedItem().rootPath));
                            option.setMaxMemory(0);
                            option.setMinMemory(0);
                            option.setWindowSize(WindowSize.window(0, 0));
                            *//*
                            if (serverURL != null && !Objects.equals(serverURL, "")) {
                                option.setServerInfo(new ServerInfo(serverURL.substring(0, serverURL.lastIndexOf(":")), Integer.parseInt(serverURL.substring(serverURL.lastIndexOf(":") + 1, serverURL.length() - 1))));
                            }
                            *//*
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        // 启动游戏
                        try {
                            launcher.launch(option, gameProcessListener);
                        } catch (LaunchException e) {
                            e.printStackTrace();
                        }
                    } catch (AuthenticationException e) {
                        new errDialog().apply("在线登陆失败", null, "非常不好意思，但是我们在尝试在线登录时出现错误。启动意外失败了。你可以检查一下你的用户名和密码是否输入正确。");
                        return;
                    }
                } else {
                    new errDialog().apply("参数错误", null, "不好意思，你需要填写密码才能在线登录。");
                }
            } else {
                new errDialog().apply("参数错误", null, "不好意思，你需要填写用户名（邮箱）才能在线登录。");
            }
        }*/
        if (controller_control.isEmpty()) {
            new errDialog().apply("参数错误", null, "不好意思，你需要选择一个账户才能启动。");
        } else if (controller_versionList.isEmpty()) {
            new errDialog().apply("参数错误", null, "不好意思，你需要选择一个游戏版本才能启动。");
        } else {
            user selectedUser = controller_control.getSelectedItem();
            if (selectedUser instanceof offlineUser) {
                controller_versionList.getSelectedItem().launch(selectedUser.getAuthenticator(), true, true, 0, 0, 1000, 800, "");
            } else if (selectedUser instanceof onlineUser) {
                ((onlineUser) selectedUser).getInfo();
                controller_versionList.getSelectedItem().launch(selectedUser.getAuthenticator(), true, true, 0, 0, 1000, 800, "");
            } else if (selectedUser instanceof libUser) {
                ((libUser) selectedUser).getInfo();
                controller_versionList.getSelectedItem().launch(selectedUser.getAuthenticator(), true, true, 0, 0, 1000, 800, "");
            }
        }
    }

    @FXML
    void addUser_Offline() {
        if (userName_Offline.getText().length() > 0) {
            controller_control.addItem(new offlineUser(userName_Offline.getText()));
            try {
                controller_versionList.getSelectedItem().config.putOfflineUserInfo(new offlineUser(userName_Offline.getText()));
                controller_versionList.getSelectedItem().config.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void addUser_Online() {
        if (userName_Online.getText().length() > 0 && password_Online.getText().length() > 0) {
            onlineUser temp = new onlineUser(userName_Online.getText(), password_Online.getText());
            if (temp.getInfo()) {
                controller_control.addItem(temp);
            } else {
                new errDialog().apply("参数错误", null, "不好意思，尝试创建在线账户时遇到错误。");
            }
        }
    }

    @FXML
    void addUser_Lib() {
        if (userName_Lib.getText().length() > 0 && password_Lib.getText().length() > 0 && server_Lib.getText().length() > 0) {
            libUser temp = new libUser(userName_Lib.getText(), password_Lib.getText(), server_Lib.getText());
            if (temp.getInfo()) {
                controller_control.addItem(temp);
            } else {
                new errDialog().apply("参数错误", null, "不好意思，尝试创建外置登录账户时遇到错误。");
            }
        }
    }

    @FXML
    void home(){
        homeButton.setDisable(true);
        pages.push(pagePane.getChildren().get(0));
        pagePane.getChildren().remove(0);
        pagePane.getChildren().add(page_control);
    }

    protected void Goto(Node page){
        if(page == page_control){
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
    void back(){
        Node backer = pages.pop();
        if(pages.empty()){
            backButton.setDisable(true);
        }
        if(backer == page_control){
            home();
            return;
        }
        pagePane.getChildren().remove(0);
        pagePane.getChildren().add(backer);
    }

    //定义在线或离线的登录方式
    enum signWays {
        Offline,
        Online
    }

    public class launcherState {
        public int serialNumber;
        private final com.Sparrow.UI.JavaFX.launcherState state;

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

class savesCell extends JFXListCell<MinecraftJFX.save> {
    @Override
    public void updateItem(MinecraftJFX.save item, boolean empty) {
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

class modsCell extends JFXListCell<MinecraftJFX.mod> {
    @Override
    public void updateItem(MinecraftJFX.mod item, boolean empty) {
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