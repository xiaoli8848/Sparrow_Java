package UI.H5;

import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class launcherUI_Controller_JavaFX {
    @FXML
    private WebView browser;

    public void install() {
        WebEngine browser_eng = browser.getEngine();
        browser_eng.load("https://cornw-ae0f8d7a.localhost.run");
    }

    public void minimize() {
        launcherUI_JavaFX.primaryStage.setIconified(true);
    }
}

