package com.MQ.UI.JavaFX;

import com.MQ.GameClass.Minecraft;
import com.MQ.launcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.DirectoryChooser;
import org.to2mbn.jmccc.option.MinecraftDirectory;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author XiaoLi8848, 1662423349@qq.com
 * 本类作为JavaFX UI的Controller，管理UI与程序的交互以及UI界面。
 * 本类中提供了访问UI上提供的启动游戏必备的参数的方法，例如 {@link #getRootDir}。
 */
public class launcherUI_Controller {
    public static Profile profile = new Profile();
    public String rootDir = "D:/Minecraft1.12.2/.minecraft";
    @FXML
    private Label gameVersionLabel;
    @FXML
    private Button launchButton;
    @FXML
    private Label gamePathLabel;
    @FXML
    private Hyperlink gamePathLink;
    @FXML
    private Button rootDirChooseButton;
    @FXML
    private TextField playerName;
    @FXML
    private WebView browser;
    @FXML
    private ComboBox<String> gameVersionChooser;
    @FXML
    private Label gameVersion;
    @FXML
    private Label rootDirLabel;
    @FXML
    private Label playerNameLabel;
    @FXML
    private Pane infoPane;
    private Minecraft[] mc;

    public void Init() {
        WebEngine browser_eng = browser.getEngine();
        //TODO 替换rootDir
        mc = Minecraft.getMinecrafts(new MinecraftDirectory(rootDir));
        ObservableList<String> options;
        if (mc[1].version != null && mc[1].version != "") {
            options =
                    FXCollections.observableArrayList(
                            mc[0].version,
                            mc[1].version
                    );
        } else {
            options =
                    FXCollections.observableArrayList(
                            mc[0].version
                    );
        }
        gameVersionChooser.setItems(options);
        gameVersionChooser.setValue(mc[0].version);
        gameVersion.setText(mc[0].version);
        browser_eng.load(launcherUI.coverURL);
        launchLanguage(launcherUI.defaultLocale);
    }

    /**
     * @return 返回用户指定的玩家名。如果未指定，则返回缺省值“MQ”。
     * @author XiaoLi8848, 1662423349@qq.com
     */
    public String getPlayerName() {
        return playerName.getText() != "" ? playerName.getText() : "MQ";
    }

    /**
     * @return 返回用户当前选中的游戏类（{@link com.MQ.GameClass.Minecraft}）。
     * 该方法不可用，待完善。
     * @author XiaoLi8848, 1662423349@qq.com
     */
    public Minecraft getSelctMC() {
        //TODO 依据用户指定的版本返回对应Minecraft
        return mc[0];
    }

    /**
     * @return 返回用户当前选中的游戏的版本号。如：1.7
     * @author XiaoLi8848, 1662423349@qq.com
     */
    public String getSelctVersion() {
        return gameVersionChooser.getValue();
    }

    /**
     * @return 返回用户当前选中的游戏的根路径。即“.minecraft”文件夹的路径。
     * @author XiaoLi8848, 1662423349@qq.com
     */
    public String getRootDir() {
        return rootDir;
    }

    /**
     * @author XiaoLi8848, 1662423349@qq.com
     * 加载游戏。
     */
    @FXML
    void launchGame(ActionEvent event) {
        launcherUI.launchGamer();
    }

    /**
     * @author XiaoLi8848, 1662423349@qq.com
     * 以特定语言重载UI上所有的文字。
     */
    public void launchLanguage(Locale locale) {
        launcherUI.defaultLocale = locale;
        launcherUI.resourceBundle = ResourceBundle.getBundle("UI/JavaFX/properties/UI-javafx", launcherUI.defaultLocale, launcher.class.getClassLoader());
        this.gameVersionLabel.setText(launcherUI.getResString(profile.UI_MAIN_VERSION_LABEL));
        this.playerNameLabel.setText(launcherUI.getResString(profile.UI_MAIN_PLAYER_NAME_LABEL));
        this.rootDirChooseButton.setText(launcherUI.getResString(profile.UI_MAIN_CHOOSE));
        this.launchButton.setText(launcherUI.getResString(profile.UI_MAIN_LAUNCH));
        this.rootDirLabel.setText(launcherUI.getResString(profile.UI_MAIN_ROOT_DIR_LABEL));
        this.gamePathLabel.setText(launcherUI.getResString(profile.UI_MAIN_PATH_LABEL));
    }

    /**
     * @author XiaoLi8848, 1662423349@qq.com
     * 弹出一个{@link DirectoryChooser}对话框，引导用户选择rootDir路径。
     */
    @FXML
    void chooseRootDir(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle(launcherUI.getResString(profile.UI_DIALOG_CHOOSE_ROOT_DIR));
        String chooserTemp = directoryChooser.showDialog(launcherUI.primaryStage).getPath();
        if (chooserTemp != null) {
            this.rootDir = chooserTemp;
        }
        Init();
    }
}

