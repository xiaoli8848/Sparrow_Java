package com.MQ.UI;

import com.MQ.GameClass.Minecraft;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.to2mbn.jmccc.option.MinecraftDirectory;

public class launcherUI_Controller {
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

    public String rootDir = "D:/Minecraft1.12.2/.minecraft";
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

    @FXML
    void launchGame(ActionEvent event) {
        launcherUI.launchGamer();
    }
}

