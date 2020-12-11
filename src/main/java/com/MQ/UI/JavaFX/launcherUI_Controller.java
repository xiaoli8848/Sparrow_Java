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
import javafx.stage.FileChooser;
import org.to2mbn.jmccc.option.MinecraftDirectory;

import java.io.File;
import java.util.Locale;
import java.util.ResourceBundle;

public class launcherUI_Controller {
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
    public static Profile profile = new Profile();
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
        browser_eng.load(launcherUI.adURL);
        launchLanguage(launcherUI.defaultLocale);
    }

    public String getPlayerName() {
        return playerName.getText() != "" ? playerName.getText() : "MQ";
    }

    public Minecraft getSelctMC() {
        return mc[0];
    }

    public String getSelctVersion() {
        return gameVersionChooser.getValue();
    }

    public String getRootDir() {
        return rootDir;
    }

    @FXML
    void launchGame(ActionEvent event) {
        launcherUI.launchGamer();
    }

    public void launchLanguage(Locale locale){
        launcherUI.defaultLocale = locale;
        launcherUI.resourceBundle = ResourceBundle.getBundle("UI/JavaFX/properties/UI-javafx",launcherUI.defaultLocale, launcher.class.getClassLoader());
        this.gameVersionLabel.setText(launcherUI.getResString(profile.UI_MAIN_VERSION_LABEL));
        this.playerNameLabel.setText(launcherUI.getResString(profile.UI_MAIN_PLAYER_NAME_LABEL));
        this.rootDirChooseButton.setText(launcherUI.getResString(profile.UI_MAIN_CHOOSE));
        this.launchButton.setText(launcherUI.getResString(profile.UI_MAIN_LAUNCH));
        this.rootDirLabel.setText(launcherUI.getResString(profile.UI_MAIN_ROOT_DIR_LABEL));
        this.gamePathLabel.setText(launcherUI.getResString(profile.UI_MAIN_PATH_LABEL));
    }

    @FXML
    void chooseRootDir(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle(launcherUI.getResString(profile.UI_DIALOG_CHOOSE_ROOT_DIR));
        String chooserTemp = directoryChooser.showDialog(launcherUI.primaryStage).getPath();
        if(chooserTemp != null){
            this.rootDir = chooserTemp;
        }
        Init();
    }
}

