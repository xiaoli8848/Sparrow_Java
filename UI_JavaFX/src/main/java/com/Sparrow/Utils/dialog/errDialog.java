package com.Sparrow.Utils.dialog;

public class errDialog extends voidDialog {
    public errDialog() {
        super(AlertType.ERROR);
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
