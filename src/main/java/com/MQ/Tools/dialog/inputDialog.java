package com.MQ.Tools.dialog;

import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class inputDialog extends TextInputDialog {
    public String apply(String title, String header, String content) {
        this.setTitle(title);
        this.setHeaderText(header);
        this.setContentText(content);

        Optional<String> result = this.showAndWait();
        if (result.isPresent()) {
            return result.get();
        }
        return "";
    }
}
