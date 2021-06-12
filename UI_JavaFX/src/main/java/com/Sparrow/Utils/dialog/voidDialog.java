/*
 * Copyright (c) 2021 Sparrow All Rights Reserved.
 * FileName: voidDialog.java
 * @author: 1662423349@qq.com
 * @date: 2021/6/12 下午9:17
 * @version: 1.0
 */

package com.Sparrow.Utils.dialog;

import javafx.scene.control.Alert;

public abstract class voidDialog extends Alert {
    public voidDialog(AlertType alertType) {
        super(alertType);
    }

    public abstract void apply(String title, String header, String content);

    public abstract void applyNotWait(String title, String header, String content);
}