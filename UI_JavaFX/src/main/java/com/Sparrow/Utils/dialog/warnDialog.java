/*
 * Copyright (c) 2021 Sparrow All Rights Reserved.
 * FileName: warnDialog.java
 * @author: 1662423349@qq.com
 * @date: 2021/6/12 下午9:17
 * @version: 1.0
 */

package com.Sparrow.Utils.dialog;

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
