package com.MQ.UI.JavaFX;

import com.MQ.launcher;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class launcherUI_About_Controller {
    @FXML
    private Label version;

    public void Init() {
        version.setText(launcher.LAUNCHER_VERSION);
    }
}
