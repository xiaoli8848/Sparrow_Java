package com.Sparrow.UI;

import com.Sparrow.Utils.Minecraft;

public abstract class launcherUI_Controller {
    public static int WIDTH;    //主界面宽度
    public static int HEIGHT;   //主界面高度

    /**
     * 添加日志记录
     *
     * @param text 要添加的日志文本
     */
    public abstract void appendLog(String text);

    /**
     * 导入游戏
     *
     * @param rootDir 要导入的游戏的.minecraft文件夹的路径
     */
    public abstract void Init(String rootDir);

    /**
     * 获取预设的游戏窗口宽度
     *
     * @return int-游戏窗口宽度
     */
    public abstract int getGameWindowWidth();

    /**
     * 获取预设的游戏窗口高度
     *
     * @return int-游戏窗口宽度
     */
    public abstract int getGameWindowHeight();

    /**
     * 获取预设的游戏最大内存（0表示自动）
     *
     * @return int-游戏最大内存
     */
    public abstract int getMaxMemory();

    /**
     * 获取预设的游戏最小内存（0表示自动）
     *
     * @return int-游戏最小内存
     */
    public abstract int getMinMemory();

    /**
     * 获取游戏内玩家昵称（离线登录可用）
     *
     * @return String-玩家昵称
     */
    public abstract String getPlayerName();

    /**
     * 输出错误信息，默认通过本类中的appendLog()输出
     *
     * @param e 抛出的错误对象
     */
    public void printErrorInfo(Exception e) {
        appendLog(e.toString());
    }

    /**
     * 显示“关于”窗口
     */
    public abstract void showAbout();

    /**
     * 获取当前选中的将启动的游戏版本
     *
     * @return {@link Minecraft}-游戏版本
     */
    public abstract Minecraft getSelectMC();

    /**
     * 获取当前选中的 将启动的游戏版本 的版本号
     *
     * @return String-游戏版本的版本号
     */
    public String getSelectMC_Version() {
        return getSelectMC().getVersion().getVersion();
    }

    /**
     * 初始化操作
     */
    public void install() {
        return;
    }
}
