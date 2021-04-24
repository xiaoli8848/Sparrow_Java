package com.Sparrow.Utils.dialog;

import javafx.scene.control.Alert;

public abstract class voidDialog extends Alert {
    public voidDialog(AlertType alertType) {
        super(alertType);
    }

    public abstract void apply(String title, String header, String content);

    public abstract void applyNotWait(String title, String header, String content);
}