/*
 * Copyright (c) 2021 Sparrow All Rights Reserved.
 * FileName: launcherState.java
 * @author: 1662423349@qq.com
 * @date: 2021/6/12 下午9:17
 * @version: 1.0
 */

package com.Sparrow.UI.JavaFX;

public enum launcherState {
    STANDBY {
        @Override
        public String toString() {
            return "待命";
        }
    },
    IMPORTING {
        @Override
        public String toString() {
            return "导入中";
        }
    },
    DOWNLOADING {
        @Override
        public String toString() {
            return "下载中";
        }
    },
    PROCESSING {
        @Override
        public String toString() {
            return "处理中";
        }
    },
    GAMING {
        @Override
        public String toString() {
            return "游戏中";
        }
    }
}
