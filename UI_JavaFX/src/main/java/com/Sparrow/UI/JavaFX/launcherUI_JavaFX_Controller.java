package com.Sparrow.UI.JavaFX;

import com.Sparrow.Utils.MinecraftJFX;
import com.Sparrow.Utils.SystemPlatform;
import com.Sparrow.Utils.dialog.errDialog;
import com.Sparrow.Utils.dialog.expDialog;
import com.Sparrow.Utils.user.libUser;
import com.Sparrow.Utils.user.offlineUser;
import com.Sparrow.Utils.user.onlineUser;
import com.Sparrow.Utils.user.user;
import com.jfoenix.controls.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Side;
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
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import org.to2mbn.jmccc.option.MinecraftDirectory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class launcherUI_JavaFX_Controller {
    protected static final Font FONT_COTITLE = Font.font("DengXian", FontWeight.BOLD, 16);
    private static final String SEPA = File.separator;
    protected static final String ROOTDIR = System.getProperty("user.dir") + SEPA;
    public File TempPath = new File(ROOTDIR + ".Sparrow");

    @FXML
    private ImageView closeButton;

    @FXML
    private ImageView minisizeButton;

    @FXML
    private JFXListView<MinecraftJFX> versionList;

    @FXML
    private Pane launchPane;

    @FXML
    private ImageView headTexture;

    @FXML
    private JFXComboBox<user> characterChooser;

    @FXML
    private TabPane versionSummary;

    @FXML
    private JFXListView<MinecraftJFX.save> gameSaves;

    @FXML
    private JFXListView<MinecraftJFX.mod> gameMods;

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

    @FXML
    private Label State;

    @FXML
    private Label Version;

    private int pointer_StateCreator = 0;
    private ArrayList<launcherState> states = new ArrayList<>();
    private ToggleGroup signWay = new ToggleGroup();

    public void install() {
        for(File fileTemp : TempPath.listFiles()){
            fileTemp.delete();
        }
        Version.setText(launcherUI_JavaFX.VERSION_UI);
        launchPane.setVisible(false);
        //设置ListView视图模板类
        versionList.setCellFactory(param -> new minecraftCell());
        gameSaves.setCellFactory(param -> new savesCell());
        gameMods.setCellFactory(param -> new modsCell());
        characterChooser.setCellFactory(param -> new userCell());

        headTexture.setImage(new Image(getClass().getClassLoader().getResource("com/Sparrow/UI/JavaFX/imgs/login.png").toString()));

        //设置右键菜单
        ContextMenu versionList_ContextMenu = new ContextMenu();
        MenuItem inputGame = new MenuItem("导入游戏");
        inputGame.setOnAction(event -> {
            DirectoryChooser rootDirChooser = new DirectoryChooser();
            rootDirChooser.setTitle("请选择游戏“.minecraft”文件夹。");
            try {
                Init(rootDirChooser.showDialog(launcherUI_JavaFX.primaryStage).toString());
            } catch (Exception e) {
                new expDialog().apply("导入错误", null, "游戏导入发生错误。", e);
            }
        });
        versionList_ContextMenu.getItems().addAll(inputGame);
//        versionList.setContextMenu(versionList_ContextMenu);
        versionList.setOnContextMenuRequested(event -> {
            if (!versionList.getSelectionModel().isEmpty()) {
                ContextMenu temp = new ContextMenu();

                MenuItem inputer = new MenuItem("导入游戏");
                inputer.setOnAction(event1 -> {
                    DirectoryChooser rootDirChooser = new DirectoryChooser();
                    rootDirChooser.setTitle("请选择游戏“.minecraft”文件夹。");
                    try {
                        Init(rootDirChooser.showDialog(launcherUI_JavaFX.primaryStage).toString());
                    } catch (Exception e) {
                        new expDialog().apply("导入错误", null, "游戏导入发生错误。", e);
                    }
                });

                MenuItem versionList_MenuItem_OpenRootDir = new MenuItem("打开所选游戏路径");
                versionList_MenuItem_OpenRootDir.setOnAction(e -> {
                    try {
                        SystemPlatform.browseFile(versionList.getSelectionModel().getSelectedItem().rootPath);
                    } catch (IOException ioException) {
                        new expDialog().apply("打开路径错误", null, "打开游戏路径发生错误。可能游戏路径已被移动或删除。", ioException);
                    }
                });
                temp.getItems().addAll(inputer, versionList_MenuItem_OpenRootDir);

                temp.show(versionList, Side.RIGHT, 0, 0);
            } else {
                versionList_ContextMenu.show(versionList, Side.RIGHT, 0, 0);
            }
        });

        //设置控件事件
        versionList.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends MinecraftJFX> observable, MinecraftJFX oldValue, MinecraftJFX newValue) -> {
                    launchPane.setVisible(true);

                    //versionText.setText(newValue.version);

                    try {
                        gameSaves.getItems().remove(0, gameSaves.getItems().size() - 1);
                        gameSaves.getItems().remove(0);
                        gameMods.getItems().remove(0, gameSaves.getItems().size() - 1);
                        gameMods.getItems().remove(0);
                    } catch (java.lang.IndexOutOfBoundsException e) {

                    }
                    gameSaves.getItems().addAll(newValue.saves);
                    gameMods.getItems().addAll(newValue.mods);
                    signWay.selectToggle(signWay.getToggles().get(0));
                }
        );
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
        characterChooser.selectionModelProperty().addListener(new ChangeListener<SingleSelectionModel<user>>() {
            @Override
            public void changed(ObservableValue<? extends SingleSelectionModel<user>> observableValue, SingleSelectionModel<user> userSingleSelectionModel, SingleSelectionModel<user> t1) {
                headTexture.setImage(characterChooser.getSelectionModel().getSelectedItem().getTexture().getHeadTexture());
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

    }

    public void Init(String rootDir) throws Exception {
        launcherState state_import = new launcherState(com.Sparrow.UI.JavaFX.launcherState.IMPORTING);
        addState(state_import);
        final File test = new File(rootDir);
        if (!test.exists() || !test.isDirectory()) {
            deleteState(state_import);
            return;
        }
        MinecraftJFX[] minecrafts = MinecraftJFX.getMinecrafts(new MinecraftDirectory(rootDir));
        versionList.getItems().addAll(minecrafts);
        for(MinecraftJFX minecraft : minecrafts){
            if(minecraft.config.haveUsers()){
                characterChooser.getItems().addAll(minecraft.config.getOnlineUsers());
                characterChooser.getItems().addAll(minecraft.config.getOfflineUsers());
                characterChooser.getItems().addAll(minecraft.config.getLibUsers());
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
        if (characterChooser.getSelectionModel().isEmpty()) {
            new errDialog().apply("参数错误", null, "不好意思，你需要选择一个账户才能启动。");
        } else if (versionList.getSelectionModel().isEmpty()) {
            new errDialog().apply("参数错误", null, "不好意思，你需要选择一个游戏版本才能启动。");
        } else {
            user selectedUser = characterChooser.getSelectionModel().getSelectedItem();
            if (selectedUser instanceof offlineUser) {
                versionList.getSelectionModel().getSelectedItem().launch(selectedUser.getAuthenticator(), true, true, 0, 0, 1000, 800, "");
            } else if (selectedUser instanceof onlineUser) {
                ((onlineUser) selectedUser).getInfo();
                versionList.getSelectionModel().getSelectedItem().launch(selectedUser.getAuthenticator(), true, true, 0, 0, 1000, 800, "");
            } else if (selectedUser instanceof libUser) {
                ((libUser) selectedUser).getInfo();
                versionList.getSelectionModel().getSelectedItem().launch(selectedUser.getAuthenticator(), true, true, 0, 0, 1000, 800, "");
            }
        }
    }

    @FXML
    void addUser_Offline() {
        if (userName_Offline.getText().length() > 0) {
            characterChooser.getItems().add(new offlineUser(userName_Offline.getText()));
            try {
                versionList.getSelectionModel().getSelectedItem().config.putOfflineUserInfo(new offlineUser(userName_Offline.getText()));
                versionList.getSelectionModel().getSelectedItem().config.flush();
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
                characterChooser.getItems().add(temp);
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
                characterChooser.getItems().add(temp);
            } else {
                new errDialog().apply("参数错误", null, "不好意思，尝试创建外置登录账户时遇到错误。");
            }
        }
    }

    //定义在线或离线的登录方式
    enum signWays {
        Offline,
        Online
    }

    public class launcherState {
        public int serialNumber;
        private com.Sparrow.UI.JavaFX.launcherState state;

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


class minecraftCell extends JFXListCell<MinecraftJFX> {

    private static boolean judgeContainsLetters(String cardNum) {
        String regex = ".*[a-zA-Z]+.*";
        Matcher m = Pattern.compile(regex).matcher(cardNum);
        return m.matches();
    }

    @Override
    public void updateItem(MinecraftJFX item, boolean empty) {
        super.updateItem(item, empty);
        setText("");
        if (!empty && item != null) {
            BorderPane cell = new BorderPane();

            VBox textBox = new VBox(3);
            Text version = new Text(judgeContainsLetters(item.version) ? item.version + " - NotRelease" : item.version + " - Release"); //如果版本号含有字母则标记为NotRelease
            version.setFont(javafx.scene.text.Font.font("DengXian", FontWeight.BOLD, 16));
            Text info;
            if(item.config.getPackName() == null) {
                info = new Text(item.saves.size()+"个存档，" + item.mods.size()+"个模组。");
            }else{
                info = new Text("整合包 - " + item.config.getPackName());
            }
            Text date = new Text(launcherUI_JavaFX.DATE_FORMAT.format(new File(item.rootPath).lastModified()));
            date.setFont(javafx.scene.text.Font.font("DengXian", FontWeight.EXTRA_BOLD, 10));
            textBox.getChildren().addAll(version, info, date);

            HBox iconBox = new HBox(2);
            ImageView icon = new ImageView(this.getClass().getClassLoader().getResource("com/Sparrow/UI/JavaFX/imgs/mc_icon.png").toString());
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

class userCell extends JFXListCell<user> {

    @Override
    public void updateItem(user item, boolean empty) {
        super.updateItem(item, empty);
        setText("");
        if (!empty && item != null) {
            BorderPane cell = new BorderPane();

            VBox textBox = new VBox(2);
            Text version = new Text(item.getUserName()); //如果版本号含有字母则标记为NotRelease
            version.setFont(launcherUI_JavaFX_Controller.FONT_COTITLE);
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