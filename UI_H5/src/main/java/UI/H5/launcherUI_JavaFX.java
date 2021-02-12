package UI.H5;

import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.engine.EngineOptions;
import com.teamdev.jxbrowser.view.javafx.BrowserView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

import static com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED;

public class launcherUI_JavaFX extends Application {
    public static launcherUI_Controller_JavaFX controller;
    protected static Stage primaryStage;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        launcherUI_JavaFX.primaryStage = primaryStage;
        primaryStage.setTitle("MQ");
        //Parent root = FXMLLoader.load(com.MQ.launcher.class.getResource("launcherUI_h5.fxml"));
        controller = new launcherUI_Controller_JavaFX();
        Browser browser;
        BrowserView browserView;
        Engine engine;
        engine = Engine.newInstance(
                EngineOptions.newBuilder(HARDWARE_ACCELERATED)
                        .licenseKey("1BNDHFSC1FY6AHSUNT5JUVTKW268OB84XTYQOEPO86FROYQT6TJ3KK11TAPP731KD6KFS8")
                        .build());
        browser = engine.newBrowser();
        browser.settings().hideScrollbars();
        browserView = BrowserView.newInstance(browser);
        Scene scene = new Scene(browserView, 1000, 850);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
        browser.navigation().loadUrl(getClass().getClassLoader().getResource("UI/H5/webapp/index.html").toString());
    }
}
