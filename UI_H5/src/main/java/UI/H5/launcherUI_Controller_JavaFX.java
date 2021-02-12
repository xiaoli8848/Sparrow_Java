package UI.H5;

import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.engine.EngineOptions;
import com.teamdev.jxbrowser.view.javafx.BrowserView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import static com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED;

public class launcherUI_Controller_JavaFX {
    Browser browser;
    BrowserView browserView;
    Engine engine;

    public Pane install() {
        engine = Engine.newInstance(
                EngineOptions.newBuilder(HARDWARE_ACCELERATED)
                        .licenseKey("1BNDHFSC1FY6AHSUNT5JUVTKW268OB84XTYQOEPO86FROYQT6TJ3KK11TAPP731KD6KFS8")
                        .build());
        browser = engine.newBrowser();
        browserView = BrowserView.newInstance(browser);
        Pane pane = new AnchorPane();
        pane.setPrefWidth(1000);
        pane.setPrefHeight(850);
        pane.getChildren().add(browserView);
        return pane;
    }

    public void loadURL(String url) {
        browser.mainFrame().ifPresent(frame -> frame.loadUrl(url));
    }

    public void minimize() {
        launcherUI_JavaFX.primaryStage.setIconified(true);
    }
}

