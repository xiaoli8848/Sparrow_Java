package com.MQ.Tools.dialog;

public class errDialog extends voidDialog {
    public errDialog() {
        super(AlertType.ERROR);
    }

    @Override
    public void apply(String title, String header, String content) {
        this.setTitle(title);
        this.setHeaderText(header);
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
