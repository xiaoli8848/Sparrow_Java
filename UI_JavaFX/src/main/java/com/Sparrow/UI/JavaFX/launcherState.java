package com.Sparrow.UI.JavaFX;

public enum launcherState {
    STANDBY{
        @Override
        public String getStateString() {
            return "待命";
        }
    },
    IMPORTING{
        @Override
        public String getStateString() {
            return "导入中";
        }
    },
    DOWNLOADING{
        @Override
        public String getStateString() {
            return "下载中";
        }
    },
    PROCESSING{
        @Override
        public String getStateString() {
            return "处理中";
        }
    },
    GAMING{
        @Override
        public String getStateString() {
            return "游戏中";
        }
    }
    ;
    public abstract String getStateString();
}
