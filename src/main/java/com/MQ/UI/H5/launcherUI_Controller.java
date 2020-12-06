package com.MQ.UI.H5;

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
    private WebView browser;

    public String rootDir = "D:/Minecraft1.12.2/.minecraft";
    private Minecraft[] mc;

    public void Init() {
        WebEngine browser_eng = browser.getEngine();
        //TODO 替换rootDir
        mc = Minecraft.getMinecrafts(new MinecraftDirectory(rootDir));
        String[] versions = new String[2];
        if (mc[1].version != null && mc[1].version != "") {
            versions[0] = mc[0].version;
            versions[1] = mc[1].version;
        } else {
            versions = new String[1];
            versions[0] = mc[0].version;
        }
        browser_eng.load(launcherUI.adURL);
    }

    //public String getPlayerName() { return ""; }

    //public Minecraft getSelctMC() { return mc[0]; }

    //public String getSelctVersion() {return gameVersionChooser.getValue();}

    //void launchGame(ActionEvent event) { launcherUI.launchGamer();}
}

