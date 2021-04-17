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
    };
}
