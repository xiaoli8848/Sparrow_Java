package com.Sparrow.UI.H5;

import org.to2mbn.jmccc.launch.LaunchException;
import org.to2mbn.jmccc.launch.LauncherBuilder;
import org.to2mbn.jmccc.option.LaunchOption;
import org.to2mbn.jmccc.option.MinecraftDirectory;
import org.to2mbn.jmccc.option.ServerInfo;
import org.to2mbn.jmccc.option.WindowSize;

import java.io.IOException;
import java.util.Objects;

import static com.Sparrow.launcher.gameProcessListener;

public class launcherUI {
    private static YggdrasilAuthenticator_H5 onlineAuth;

    public static void main(String[] args) {
        controller controllerThread = new controller();
        controllerThread.start();
        launcherUI_JavaFX.main(new String[0]);
    }

    /**
     * @param rootDir      游戏根路径（即“.minecraft”文件夹的路径）
     * @param version      要启动的版本
     * @param username     在线启动使用的用户名（邮箱）
     * @param password     在线启动使用的密码
     * @param debugPrint   是否将调试信息输出
     * @param nativesFC    是否执行natives文件夹完整性的快速检查
     * @param minMemory    游戏可以使用的最小内存
     * @param maxMemory    游戏可以使用的最大内存
     * @param windowWidth  游戏窗口宽度
     * @param windowHeight 游戏窗口高度
     * @param serverURL    指定游戏启动后要进入的服务器的URL地址。可为空，则游戏启动后不进入任何服务器。
     * @author XiaoLi8848, 1662423349@qq.com
     */
    public static void launch_online(String rootDir,
                                     String version,
                                     String username,
                                     String password,
                                     boolean debugPrint,
                                     boolean nativesFC,
                                     int minMemory,
                                     int maxMemory,
                                     int windowWidth,
                                     int windowHeight,
                                     String serverURL
    ) {
        onlineAuth = new YggdrasilAuthenticator_H5(username, password);
        org.to2mbn.jmccc.launch.Launcher launcher = LauncherBuilder.create()
                .setDebugPrintCommandline(debugPrint)
                .setNativeFastCheck(nativesFC)
                .build();

        LaunchOption option = null;
        try {
            option = new LaunchOption(
                    version, // 游戏版本
                    onlineAuth, // 使用在线验证
                    new MinecraftDirectory(rootDir));
            option.setMaxMemory(maxMemory);
            option.setMinMemory(minMemory);
            option.setWindowSize(WindowSize.window(windowWidth, windowHeight));
            if (serverURL != null && !Objects.equals(serverURL, "")) {
                option.setServerInfo(new ServerInfo(serverURL.substring(0, serverURL.lastIndexOf(":")), Integer.parseInt(serverURL.substring(serverURL.lastIndexOf(":") + 1, serverURL.length() - 1))));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 启动游戏
        try {
            launcher.launch(option, gameProcessListener);
        } catch (LaunchException e) {
            e.printStackTrace();
        }
    }
}

class controller extends Thread {
    public void run() {
        launcherUI_Controller.main(new String[0]);
    }
}