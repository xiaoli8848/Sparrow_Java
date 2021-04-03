package com.Sparrow.Tools.dialog;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class confirmDialog extends Alert {
    public confirmDialog() {
        super(AlertType.CONFIRMATION);
    }

    public boolean apply(String title, String header, String content) {
        this.setTitle(title);
        if (header != "")
            this.setHeaderText(header);
        else
            this.setHeaderText(null);
        this.setContentText(content);

        Optional<ButtonType> result = this.showAndWait();
        if (result.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }
    }
}
