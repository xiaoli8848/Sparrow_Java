package com.MQ.UI.JavaFX;

import com.MQ.GameClass.Minecraft;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import org.to2mbn.jmccc.option.MinecraftDirectory;

import java.io.File;
import java.util.Locale;

public class launcherUI_Controller {
    public String rootDir = "D:/Minecraft1.12.2/.minecraft";
    @FXML
    private Label gameVersionLabel;
    @FXML
    private ComboBox<String> gameVersionChooser;
    @FXML
    private Label gameVersion;
    @FXML
    private Pane infoPane;
    @FXML
    private Label playerNameLabel;
    @FXML
    private TextField playerName;
    @FXML
    private WebView browser;
    @FXML
    private Button rootDirChooseButton;
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
        browser_eng.load(launcherUI.adURL);
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

    @FXML
    void chooseRootDir(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle(launcherUI.resourceBundle.getString("dialog.chooseRootDir"));
        String chooserTemp = directoryChooser.showDialog(launcherUI.primaryStage).getPath();
        if(chooserTemp != null){
            this.rootDir = chooserTemp;
        }
        Init();
    }
}

