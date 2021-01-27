package com.MQ.UI.JavaFX;

import com.MQ.Minecraft;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.to2mbn.jmccc.launch.LaunchException;
import org.to2mbn.jmccc.launch.LauncherBuilder;
import org.to2mbn.jmccc.option.LaunchOption;
import org.to2mbn.jmccc.option.MinecraftDirectory;
import org.to2mbn.jmccc.option.ServerInfo;
import org.to2mbn.jmccc.option.WindowSize;
import org.to2mbn.jmccc.version.Versions;

import java.io.IOException;
import java.util.Objects;

import static com.MQ.launcher.gameProcessListener;

/**
 * @author XiaoLi8848, 1662423349@qq.com
 * 本类中提供了JavaFX UI平台的{@link #main(String[])}程序入口，以采用JavaFX UI启动程序。同时，本类中的{@link #coverURL}等公开静态（常）量，为{@link launcherUI_Controller}类或者其它UI类提供必要的参数。
 */
public class launcherUI extends Application {
    public static final String coverURL = "http://xiaoli8848.usa3v.vip/JMCCC/";
    public static final String projectURL = "https://github.com/xiaoli8848/MQ";
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 850;
    public static launcherUI_Controller controller;
    public static Stage primaryStage;
    public static Parent root;
    private static YggdrasilAuthenticator_JavaFX onlineAuth;

    public static void main(String[] args) {
        launch(args);
    }

    public static void launchGameOffline() {
        controller.getSelectMC().launchOffline(controller.getPlayerName(), false, true, controller.getMinMem(), controller.getMaxMem(), controller.getWidth(), controller.getHeight(), controller.getServer());
    }

    public static void launchGameOnline() {
        Minecraft tempMC = controller.getSelectMC();
        launch_online(tempMC.rootPath, false, true, controller.getMinMem(), controller.getMaxMem(), controller.getWidth(), controller.getHeight(), controller.getServer());
    }

    public static void gotoWebSite(String url) throws IOException {
        java.net.URI uri = java.net.URI.create(url);
        // 获取当前系统桌面扩展
        java.awt.Desktop dp = java.awt.Desktop.getDesktop();
        // 判断系统桌面是否支持要执行的功能
        if (dp.isSupported(java.awt.Desktop.Action.BROWSE)) {
            dp.browse(uri);
            // 获取系统默认浏览器打开链接
        }
    }

    /**
     * @param rootDir      游戏根路径（即“.minecraft”文件夹的路径）
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
                                     boolean debugPrint,
                                     boolean nativesFC,
                                     int minMemory,
                                     int maxMemory,
                                     int windowWidth,
                                     int windowHeight,
                                     String serverURL
    ) {
        onlineAuth = new YggdrasilAuthenticator_JavaFX();
        org.to2mbn.jmccc.launch.Launcher launcher = LauncherBuilder.create()
                .setDebugPrintCommandline(debugPrint)
                .setNativeFastCheck(nativesFC)
                .build();

        LaunchOption option = null;
        try {
            option = new LaunchOption(
                    (String) Versions.getVersions(new MinecraftDirectory(rootDir)).toArray()[0], // 游戏版本
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

    @Override
    public void start(Stage primaryStage) {
        try {
            this.primaryStage = primaryStage;
            primaryStage.setTitle("MQ");
            FXMLLoader a = new FXMLLoader(getClass().getClassLoader().getResource("UI/JavaFX/launcherUI_javafx.fxml"));
            root = a.load();
            controller = a.getController();
            Scene scene = new Scene(root, WIDTH, HEIGHT);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
            controller.install();
        }catch (Exception e){
            e.printStackTrace();
            System.exit(0);
        }
    }

    @Override
    public void stop() {
        System.exit(0);
    }
}