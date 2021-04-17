package com.Sparrow.UI.JavaFX;

import com.Sparrow.Minecraft;
import com.Sparrow.Tools.SystemPlatform;
import com.Sparrow.Tools.dialog.errDialog;
import com.Sparrow.Tools.dialog.expDialog;
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
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import org.to2mbn.jmccc.auth.AuthenticationException;
import org.to2mbn.jmccc.launch.LaunchException;
import org.to2mbn.jmccc.launch.LauncherBuilder;
import org.to2mbn.jmccc.option.LaunchOption;
import org.to2mbn.jmccc.option.MinecraftDirectory;
import org.to2mbn.jmccc.option.WindowSize;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.Sparrow.launcher.gameProcessListener;

public class launcherUI_JavaFX_Controller {
    private static final String SEPA = File.separator;
    public static final String ROOTDIR = System.getProperty("user.dir") + SEPA;
    @FXML
    private ListView<Minecraft> versionList;

    @FXML
    private Pane launchPane;

    @FXML
    private TabPane versionSummary;

    @FXML
    private ListView<Minecraft.save> gameSaves;

    @FXML
    private ListView<Minecraft.mod> gameMods;

    @FXML
    private ListView<?> gameResPacks;

    @FXML
    private ImageView closeButton;

    @FXML
    private ImageView minisizeButton;

    @FXML
    private Label State;

    @FXML
    private Label Version;

    @FXML
    private BorderPane launchButton;

    @FXML
    private Label versionText;

    @FXML
    private VBox offlinePane;

    @FXML
    private TextField nickName;

    @FXML
    private VBox onlinePane;

    @FXML
    private TextField userName;

    @FXML
    private PasswordField password;

    private int pointer_StateCreator = 0;

    private ArrayList<launcherState> states = new ArrayList<>();

    protected File TempPath = new File(ROOTDIR + ".Sparrow" + SEPA);

    @FXML
    private ToggleButton offlineSign;

    @FXML
    private ToggleButton onlineSign;

    private ToggleGroup signWay = new ToggleGroup();

    private signWays sw = signWays.Offline;

    public void install() {
        Version.setText(launcherUI_JavaFX.VERSION_UI);
        launchPane.setVisible(false);
        //设置ListView视图模板类
        versionList.setCellFactory(param -> new minecraftCell());
        gameSaves.setCellFactory(param -> new savesCell());
        gameMods.setCellFactory(param -> new modsCell());

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
                (ObservableValue<? extends Minecraft> observable, Minecraft oldValue, Minecraft newValue) -> {
                    launchPane.setVisible(true);

                    versionText.setText(newValue.version);

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
                    sw = signWays.Offline;
                }
        );
        signWay.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) -> {
            if (new_toggle == offlineSign) {
                offlinePane.setDisable(false);
                onlinePane.setDisable(true);
                sw = signWays.Offline;
            } else {
                offlinePane.setDisable(true);
                onlinePane.setDisable(false);
                sw = signWays.Online;
            }
        });

        //设置ToggleGroup
        offlineSign.setToggleGroup(signWay);
        onlineSign.setToggleGroup(signWay);


        //自动导入程序目录下的游戏
        try {
            Init(ROOTDIR + ".minecraft");
        } catch (Exception e) {

        }

        if (!TempPath.exists()) {
            TempPath.mkdir();
        }

    }

    //定义在线或离线的登录方式
    enum signWays {
        Offline,
        Online
    }

    public void Init(String rootDir) throws Exception {
        launcherState state_import = new launcherState(com.Sparrow.UI.JavaFX.launcherState.IMPORTING);
        addState(state_import);
        final File test = new File(rootDir);
        if (!test.exists() || !test.isDirectory()) {
            deleteState(state_import);
            return;
        }
        Minecraft[] minecrafts = Minecraft.getMinecrafts(new MinecraftDirectory(rootDir));
        versionList.getItems().addAll(minecrafts);
        deleteState(state_import);
    }

    public String getNickName() {
        return nickName.getText();
    }

    public String getUserName() {
        return userName.getText();
    }

    public String getPassword() {
        return password.getText();
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
    void launch(MouseEvent event) {
        //TODO 实现在线启动
        if (sw == signWays.Offline)
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
                            /*
                            if (serverURL != null && !Objects.equals(serverURL, "")) {
                                option.setServerInfo(new ServerInfo(serverURL.substring(0, serverURL.lastIndexOf(":")), Integer.parseInt(serverURL.substring(serverURL.lastIndexOf(":") + 1, serverURL.length() - 1))));
                            }
                            */
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
                    new errDialog().apply("参数错误", null, "不好意思，你需要填写密码才能在线登录");
                }
            } else {
                new errDialog().apply("参数错误", null, "不好意思，你需要填写用户名（邮箱）才能在线登录。");
            }
        }
    }

    public class launcherState {
        private com.Sparrow.UI.JavaFX.launcherState state;
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


class minecraftCell extends ListCell<Minecraft> {

    private static boolean judgeContainsLetters(String cardNum) {
        String regex = ".*[a-zA-Z]+.*";
        Matcher m = Pattern.compile(regex).matcher(cardNum);
        return m.matches();
    }

    @Override
    public void updateItem(Minecraft item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty && item != null) {
            BorderPane cell = new BorderPane();

            VBox textBox = new VBox(3);
            Text version = new Text(judgeContainsLetters(item.version) ? item.version + " - NotRelease" : item.version + " - Release"); //如果版本号含有字母则标记为NotRelease
            version.setFont(javafx.scene.text.Font.font("DengXian", FontWeight.BOLD, 16));
            Text info = new Text("详细信息：暂无");  //TODO 后期加入整合包信息或存档概览
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

class savesCell extends ListCell<Minecraft.save> {
    @Override
    public void updateItem(Minecraft.save item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty && item != null) {
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
        } else if (empty) {
            setText(null);
            setGraphic(null);
        }
    }
}

class modsCell extends ListCell<Minecraft.mod> {
    @Override
    public void updateItem(Minecraft.mod item, boolean empty) {
        super.updateItem(item, empty);
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