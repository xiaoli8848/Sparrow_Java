package UI.H5;

import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.engine.EngineOptions;
import com.teamdev.jxbrowser.view.swing.BrowserView;

import javax.swing.*;
import java.awt.*;

import static com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED;

public class launcherUI_JFrame {

    public static void main(String[] args) {
        Engine engine;
        Browser browser;
        BrowserView view;
        engine = Engine.newInstance(
                EngineOptions.newBuilder(HARDWARE_ACCELERATED)
                        .licenseKey("1BNDHFSC1FY6AHSUNT5JUVTKW268OB84XTYQOEPO86FROYQT6TJ3KK11TAPP731KD6KFS8")
                        .build());
        browser = engine.newBrowser();
        view = BrowserView.newInstance(browser);
        JFrame frame = new JFrame();
        frame.add(view, BorderLayout.CENTER);
        //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationByPlatform(true);
        frame.setSize(1000, 850);
        frame.setResizable(false);
        frame.setUndecorated(true);
        frame.setVisible(true);
        browser.navigation().loadUrlAndWait("http://cornw-7a46512e.localhost.run");
    }
}
