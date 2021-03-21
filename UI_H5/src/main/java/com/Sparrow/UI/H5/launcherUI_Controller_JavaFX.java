package com.Sparrow.UI.H5;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.javafx.BrowserView;
public class launcherUI_Controller_JavaFX {
    Browser browser;
    BrowserView browserView;

    public launcherUI_Controller_JavaFX() {
        this.browserView = new BrowserView();
        this.browser = this.browserView.getBrowser();
    }

    public void loadURL(String url) {
        browser.loadURL(url);
    }

    public void minimize() {
        launcherUI_JavaFX.primaryStage.setIconified(true);
    }
}

