package com.MQ.Tools.dialog;

public class warnDialog extends voidDialog {
    public warnDialog() {
        super(AlertType.WARNING);
    }

    @Override
    public void apply(String title, String header, String content) {
        this.setTitle(title);
        if (header != "")
            this.setHeaderText(header);
        else
            this.setHeaderText(null);
        this.setContentText(content);

        this.showAndWait();
    }

    @Override
    public void applyNotWait(String title, String header, String content) {
        this.setTitle(title);
        this.setHeaderText(header);
        this.setContentText(content);

        this.show();
    }
}
