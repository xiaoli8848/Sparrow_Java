package com.Sparrow.UI.JavaFX;

import com.Sparrow.launcher;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class launcherUI_About_Controller {
    @FXML
    private Label version;

    public void Init() {
        version.setText(launcher.LAUNCHER_VERSION);
    }
}
