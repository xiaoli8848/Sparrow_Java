package UI.H5;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.javafx.BrowserView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class launcherUI_Controller_JavaFX {
    Browser browser;
    BrowserView browserView;

    public Pane install() {
        browserView = new BrowserView();
        browser = browserView.getBrowser();
        Pane pane = new AnchorPane();
        pane.setPrefWidth(1000);
        pane.setPrefHeight(850);
        pane.getChildren().add(browserView);
        return pane;
    }

    public void loadURL(String url) {
        browser.loadURL(url);
    }

    public void minimize() {
        launcherUI_JavaFX.primaryStage.setIconified(true);
    }
}

