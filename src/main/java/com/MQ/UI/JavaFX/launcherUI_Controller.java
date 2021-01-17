package com.MQ.UI.JavaFX;

import com.MQ.Minecraft;
import com.MQ.Tools.WindowsNotification;
import com.MQ.Tools.dialog.stdDialog;
import com.MQ.launcher;
import com.sun.javafx.binding.StringFormatter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.RangeSlider;
import org.controlsfx.control.ToggleSwitch;
import org.to2mbn.jmccc.exec.GameProcessListener;
import org.to2mbn.jmccc.mcdownloader.download.DownloadCallback;
import org.to2mbn.jmccc.mcdownloader.download.DownloadTask;
import org.to2mbn.jmccc.mcdownloader.download.concurrent.CallbackAdapter;
import org.to2mbn.jmccc.mcdownloader.provider.forge.ForgeVersion;
import org.to2mbn.jmccc.mcdownloader.provider.forge.ForgeVersionList;
import org.to2mbn.jmccc.mcdownloader.provider.liteloader.LiteloaderVersion;
import org.to2mbn.jmccc.mcdownloader.provider.liteloader.LiteloaderVersionList;
import org.to2mbn.jmccc.option.MinecraftDirectory;
import org.to2mbn.jmccc.version.Version;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import static com.MQ.Tools.DownloadAPI.Download.*;

/**
 * @author XiaoLi8848, 1662423349@qq.com
 * 本类作为JavaFX UI的Controller，管理UI与程序的交互以及UI界面。
 * 本类中提供了访问UI上提供的启动游戏必备的参数的方法，例如 {@link #getRootDir}。
 */
public class launcherUI_Controller {
    private static ForgeVersion forgeVersion;
    private static LiteloaderVersion liteloaderVersion;
    public String rootDir;
    public String downloadDir;
    public String downloadVersion;
    @FXML
    private Hyperlink gamePathLink;
    @FXML
    private ToggleSwitch download_Forge_is;
    @FXML
    private RangeSlider memory;
    @FXML
    private TextField user_name;
    @FXML
    private ImageView versionImage;
    @FXML
    private Label playerNameLabel;
    @FXML
    private TextArea logText;
    @FXML
    private Pane download_Forge;
    @FXML
    private Pane download_Liteloader;
    @FXML
    private TabPane topPane;
    @FXML
    private PasswordField password;
    @FXML
    private CheckBox isThroughServer;
    @FXML
    private TextField download_Forge_version;
    @FXML
    private TextField download_MC_version;
    @FXML
    private TextField height;
    @FXML
    private Button download_MC_Button;
    @FXML
    private Tab launchTab;
    @FXML
    private Tab downloadTab;
    @FXML
    private Pane download_MC;
    @FXML
    private TextField address;
    @FXML
    private TextField playerName;
    @FXML
    private Button download_MC_Path;
    @FXML
    private TextField download_Liteloader_version;
    @FXML
    private ToggleSwitch download_Liteloader_is;
    @FXML
    private Pane infoPane;
    @FXML
    private Button launchButton;
    @FXML
    private Button rootDirChooseButton;
    @FXML
    private Label versionLabel;
    @FXML
    private Circle unfoldButton;
    @FXML
    private TextField port;
    @FXML
    private CheckBox isAutoMem;
    @FXML
    private TextField width;
    @FXML
    private Label gameVersion;
    @FXML
    private CheckBox isFullScreen;
    @FXML
    private CheckBox isOnlineLaunch;
    private ForgeVersionList forgeVersionList = null;
    private LiteloaderVersionList liteloaderVersionList = null;
    private Minecraft[] mc;
    private int mc_pointer = 0;

    public void printError(Exception e) {
        appendLog(e.toString());
    }

    public void install() {
        versionLabel.setText("MQ启动器 " + launcher.launcherVersion);
        launchTab.setDisable(true);
        rootDirChooseButton.setVisible(true);
        ableAutoMem();
        ableOffline();
        ableFullScreen();
        ableNonThroughServer();

        launcher.gameProcessListener = new GameProcessListener() {

            @Override
            public void onLog(String log) {
                appendLog(log); // 输出日志到控制台
            }

            @Override
            public void onErrorLog(String log) {
                appendLog(log); // 输出日志到控制台（同上）
            }

            @Override
            public void onExit(int code) {
                freeArgs();
                appendLog("游戏进程停止。返回码：" + code); // 游戏结束时输出状态码
                try {
                    WindowsNotification.displayTray("MQ - 游戏结束", "游戏进程结束", "返回码：" + code);
                } catch (AWTException awtException) {
                }
                appendLog("\n------------------\n");
            }
        };

        launcher.combinedDownloadCallback = new CallbackAdapter<Version>() {

            @Override
            public void done(Version result) {
                // 当完成时调用
                // 参数代表实际下载到的Minecraft版本
                appendLog("MC（版本 " + result + " )下载完成。");
                try {
                    WindowsNotification.displayTray("MQ - 下载完成", "下载完成", "恭喜。MC版本：" + result + "已下载完成。");
                } catch (AWTException awtException) {
                }
            }

            @Override
            public void failed(Throwable e) {
                // 当失败时调用
                // 参数代表是由于哪个异常而失败的
                appendLog("下载出现错误。");
                try {
                    WindowsNotification.displayTray("MQ - 下载错误", "下载错误", "抱歉。下载MC时遇到错误：" + e.toString());
                } catch (AWTException awtException) {
                }
                e.printStackTrace();
            }

            @Override
            public void cancelled() {
                // 当被取消时调用
                appendLog("下载取消。");
            }

            @Override
            public <R> DownloadCallback<R> taskStart(DownloadTask<R> task) {
                // 当有一个下载任务被派生出来时调用
                // 在这里返回一个DownloadCallback就可以监听该下载任务的状态
                appendLog("开始下载：" + task.getURI());
                return new CallbackAdapter<R>() {

                    @Override
                    public void done(R result) {
                        // 当这个DownloadTask完成时调用
                        appendLog("子任务完成：" + task.getURI());
                    }

                    @Override
                    public void failed(Throwable e) {
                        // 当这个DownloadTask失败时调用
                        appendLog(String.valueOf(StringFormatter.format("子任务失败：%s。原因：%s", task.getURI(), e)));
                    }

                    @Override
                    public void cancelled() {
                        // 当这个DownloadTask被取消时调
                        appendLog("子任务取消：" + task.getURI());
                    }

                    @Override
                    public void retry(Throwable e, int current, int max) {
                        // 当这个DownloadTask因出错而重试时调用
                        // 重试不代表着失败
                        // 也就是说，一个DownloadTask可以重试若干次，
                        // 每次决定要进行一次重试时就会调用这个方法
                        // 当最后一次重试失败，这个任务也将失败了，failed()才会被调用
                        // 所以调用顺序就是这样：
                        // retry()->retry()->...->failed()
                        appendLog(String.valueOf(StringFormatter.format("子任务重试[%d/%d]：%s。原因：%s%n", current, max, task.getURI(), e)));
                    }
                };
            }
        };

        appendLog("轻巧、便捷为一体，尽在 MQ · 新一代MC启动器 。加载完毕。");

        downloadForgeVersionList();
        downloadLiteloaderVersionList();
    }

    public void Init() {
        //launchLanguage(launcherUI.defaultLocale);
        try {
            mc = Minecraft.getMinecrafts(new MinecraftDirectory(rootDir));
            gameVersion.setText(mc[0].version);
            gamePathLink.setText(getSelectMC().rootPath);
            launchTab.setDisable(false);
            rootDirChooseButton.setVisible(false);
        } catch (Exception e) {
            mc = new Minecraft[0];
            gameVersion.setText("未知");
        }
    }

    /**
     * @return 返回用户指定的玩家名。如果未指定，则返回缺省值“MQ”。
     * @author XiaoLi8848, 1662423349@qq.com
     */
    public String getPlayerName() {
        return playerName.getText().length() > 0 ? playerName.getText() : "MQ";
    }

    /**
     * @return 返回用户当前选中的游戏类（{@link Minecraft}）。
     * 该方法不可用，待完善。
     * @author XiaoLi8848, 1662423349@qq.com
     */
    public Minecraft getSelectMC() {
        return mc[mc_pointer];
    }

    /**
     * @return 返回用户当前选中的游戏的版本号。如：1.7
     * @author XiaoLi8848, 1662423349@qq.com
     */
    public String getSelectVersion() {
        return getSelectMC().version;
    }

    /**
     * @return 返回用户当前选中的游戏的根路径。即“.minecraft”文件夹的路径。
     * @author XiaoLi8848, 1662423349@qq.com
     */
    public String getRootDir() {
        return rootDir;
    }

    /**
     * @author XiaoLi8848, 1662423349@qq.com
     * 加载游戏。
     */
    @FXML
    void launchGame(ActionEvent event) {
        if (rootDir != null && !rootDir.equals("")) {
            if (!isOnlineLaunch.isSelected()) {
                lockArgs();
                launcherUI.launchGameOffline();
            } else {
                if (!user_name.getText().equals("") && !password.getText().equals("")) {
                    lockArgs();
                    launcherUI.launchGameOnline();
                } else {
                    //TODO 处理空账号/密码
                }
            }
        }
    }

    /**
     * @author XiaoLi8848, 1662423349@qq.com
     * 弹出一个{@link DirectoryChooser}对话框，引导用户选择rootDir路径。
     */
    @FXML
    void chooseRootDir(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("选择“.minecraft”文件夹");
        String chooserTemp = directoryChooser.showDialog(launcherUI.primaryStage).getPath();
        if (chooserTemp != null) {
            rootDir = chooserTemp;
        }
        Init();
    }

    public void appendLog(String text) {
        try {
            logText.appendText(text + "\n");
        } catch (Exception e) {

        }
    }

    @FXML
    void linkFire(ActionEvent event) {
        Desktop desktop = Desktop.getDesktop();
        File dirToOpen = null;
        try {
            dirToOpen = new File(gamePathLink.getText());
            desktop.open(dirToOpen);
        } catch (Exception e) {
        }
    }

    @FXML
    void showAbout(ActionEvent event) {
        Stage primaryStage = new Stage();
        primaryStage.setTitle("MQ - About");
        FXMLLoader a = new FXMLLoader(getClass().getClassLoader().getResource("UI/JavaFX/launcherUI_javafx_about.fxml"));
        Parent root = null;
        try {
            root = a.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        launcherUI_About_Controller controller;
        controller = a.getController();
        controller.Init();
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void updateVersionView() {
        gameVersion.setText(getSelectVersion());
        gamePathLink.setText(getSelectMC().rootPath);
    }

    @FXML
    void isOnline(ActionEvent event) {
        if (isOnlineLaunch.isSelected()) {
            ableOnline();
        } else {
            ableOffline();
        }
    }

    @FXML
    void isMem(ActionEvent event) {
        if (isAutoMem.isSelected()) {
            ableAutoMem();
        } else {
            ableManMem();
        }
    }

    @FXML
    void isFull(ActionEvent event) {
        if (isFullScreen.isSelected()) {
            ableFullScreen();
        } else {
            ableNotFullScreen();
        }
    }

    private void ableOnline() {
        isOnlineLaunch.setSelected(true);
        playerName.setDisable(true);
        user_name.setDisable(false);
        password.setDisable(false);
        versionImage.setImage(new Image(String.valueOf(getClass().getClassLoader().getResource("UI/JavaFX/imgs/iron_sword_double.png"))));
    }

    private void ableOffline() {
        isOnlineLaunch.setSelected(false);
        playerName.setDisable(false);
        user_name.setDisable(true);
        password.setDisable(true);
        versionImage.setImage(new Image(String.valueOf(getClass().getClassLoader().getResource("UI/JavaFX/imgs/iron_sword.png"))));
    }

    private void ableAutoMem() {
        isAutoMem.setSelected(true);
        memory.setDisable(true);
    }

    private void ableManMem() {
        isAutoMem.setSelected(false);
        memory.setDisable(false);
    }

    private void ableFullScreen() {
        isFullScreen.setSelected(true);
        width.setDisable(true);
        height.setDisable(true);
    }

    private void ableNotFullScreen() {
        isFullScreen.setSelected(false);
        width.setDisable(false);
        height.setDisable(false);
    }

    private void ableThroughServer() {
        isThroughServer.setSelected(true);
        address.setDisable(false);
        port.setDisable(false);
    }

    private void ableNonThroughServer() {
        isThroughServer.setSelected(false);
        address.setDisable(true);
        port.setDisable(true);
    }

    public int getMaxMem() {
        if (isAutoMem.isSelected())
            return 0;
        if (memory.getMax() > memory.getMin()) {
            return new Double(memory.getHighValue()).intValue();
        } else {
            ableAutoMem();
            return 0;
        }
    }

    public int getMinMem() {
        if (isAutoMem.isSelected())
            return 0;
        if (memory.getMin() < memory.getMax()) {
            return new Double(memory.getLowValue()).intValue();
        } else {
            ableAutoMem();
            return 0;
        }
    }

    public int getWidth() {
        if (isFullScreen.isSelected())
            return 0;
        if (Integer.parseInt(width.getText()) >= 0) {
            return Integer.parseInt(width.getText());
        } else {
            ableFullScreen();
            return 0;
        }
    }

    public int getHeight() {
        if (isFullScreen.isSelected())
            return 0;
        if (Integer.parseInt(height.getText()) >= 0) {
            return Integer.parseInt(height.getText());
        } else {
            ableFullScreen();
            return 0;
        }
    }

    public String getUserName() {
        return user_name.getText();
    }

    public String getPassword() {
        return password.getText();
    }

    public void lockArgs() {
        launchTab.setDisable(true);
    }

    public void freeArgs() {
        launchTab.setDisable(false);
    }

    @FXML
    void choosePath_MC() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("选择“.minecraft”文件夹");
        String chooserTemp = directoryChooser.showDialog(launcherUI.primaryStage).getPath();
        if (chooserTemp != null) {
            downloadDir = chooserTemp;
        }
    }

    @FXML
    void download_MC() {
        if (new File(downloadDir).exists()) {
            downloadGame(download_MC_version.getText(), downloadDir);
        }
    }

    @FXML
    void chooseVersion_MC() {
        downloadVersion = download_MC_version.getText();
        try {
            if (forgeVersionList != null) {
                forgeVersion = forgeVersionList.getLatest(download_MC_version.getText());
                download_Forge_version.setText(forgeVersion.toString());
            }
            if (liteloaderVersionList != null) {
                liteloaderVersion = liteloaderVersionList.getLatest(download_MC_version.getText());
                download_Liteloader_version.setText(liteloaderVersion.toString());
            }
        } catch (Exception e) {
            download_Forge_version.setText("");
            download_Liteloader_version.setText("");
        }
    }

    public void updateForgeVersion(ForgeVersionList list) {
        forgeVersionList = list;
    }

    public void updateLiteloader(LiteloaderVersionList list) {
        liteloaderVersionList = list;
    }

    @FXML
    void changeTab() {

    }

    @FXML
    void isThroughServer() {
        if (isThroughServer.isSelected()) {
            ableThroughServer();
        } else {
            ableNonThroughServer();
        }
    }

    public String getServer() {
        return !address.getText().equals("") && !port.getText().equals("") ? !port.getText().equals("") ? address.getText() + ":25565" : address.getText() + ":" + port.getText() : "";
    }

    public void changeThread() {

    }
}