package com.MQ.UI.H5;

import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class launcherUI_Controller_JavaFX {
    @FXML
    private WebView browser;

    public void install() {
        WebEngine browser_eng = browser.getEngine();
        browser_eng.load("localhost:8080");
    }
}

