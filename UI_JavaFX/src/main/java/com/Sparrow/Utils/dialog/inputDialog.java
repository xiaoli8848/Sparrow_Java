/*
 * Copyright (c) 2021 Sparrow All Rights Reserved.
 * FileName: inputDialog.java
 * @author: 1662423349@qq.com
 * @date: 2021/6/12 下午9:17
 * @version: 1.0
 */

package com.Sparrow.Utils.dialog;

import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class inputDialog extends TextInputDialog {
    public String apply(String title, String header, String content) {
        this.setTitle(title);
        if (header != "")
            this.setHeaderText(header);
        else
            this.setHeaderText(null);
        this.setContentText(content);

        Optional<String> result = this.showAndWait();
        if (result.isPresent()) {
            return result.get();
        }
        return "";
    }
}
