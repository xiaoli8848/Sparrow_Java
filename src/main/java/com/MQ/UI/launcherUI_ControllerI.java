package com.MQ.UI;

import com.MQ.Minecraft;

public interface launcherUI_ControllerI {
    int WIDTH = 1000;
    int HEIGHT = 850;

    /**
     * 添加日志记录
     *
     * @param text 要添加的日志文本
     */
    void appendLog(String text);

    /**
     * 导入游戏
     *
     * @param rootDir 要导入的游戏的.minecraft文件夹的路径
     */
    void Init(String rootDir);

    /**
     * 获取预设的游戏窗口宽度
     *
     * @return int-游戏窗口宽度
     */
    int getGameWindowWidth();

    /**
     * 获取预设的游戏窗口高度
     *
     * @return int-游戏窗口宽度
     */
    int getGameWindowHeight();

    /**
     * 获取预设的游戏最大内存（0表示自动）
     *
     * @return int-游戏最大内存
     */
    int getMaxMemory();

    /**
     * 获取预设的游戏最小内存（0表示自动）
     *
     * @return int-游戏最小内存
     */
    int getMinMemory();

    /**
     * 获取游戏内玩家昵称（离线登录可用）
     *
     * @return String-玩家昵称
     */
    String getPlayerName();

    /**
     * 输出错误信息，默认通过本类中的appendLog()输出
     *
     * @param e 抛出的错误对象
     */
    void printErrorInfo(Exception e);

    /**
     * 显示“关于”窗口
     */
    void showAbout();

    /**
     * 获取当前选中的将启动的游戏版本
     *
     * @return {@link com.MQ.Minecraft}-游戏版本
     */
    Minecraft getSelectMC();

    /**
     * 获取当前选中的 将启动的游戏版本 的版本号
     *
     * @return String-游戏版本的版本号
     */
    String getSelectMC_Version();

    /**
     * 初始化操作
     */
    void install();
}
