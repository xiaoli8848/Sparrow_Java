package com.MQ.UI.JavaFX;

import com.MQ.GameClass.Minecraft;
import com.MQ.launcher;
import com.sun.javafx.binding.StringFormatter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.to2mbn.jmccc.exec.GameProcessListener;
import org.to2mbn.jmccc.mcdownloader.download.DownloadCallback;
import org.to2mbn.jmccc.mcdownloader.download.DownloadTask;
import org.to2mbn.jmccc.mcdownloader.download.concurrent.CallbackAdapter;
import org.to2mbn.jmccc.option.MinecraftDirectory;
import org.to2mbn.jmccc.version.Version;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * @author XiaoLi8848, 1662423349@qq.com
 * 本类作为JavaFX UI的Controller，管理UI与程序的交互以及UI界面。
 * 本类中提供了访问UI上提供的启动游戏必备的参数的方法，例如 {@link #getRootDir}。
 */
public class launcherUI_Controller {
    public static Profile profile = new Profile();
    public String rootDir;
    @FXML
    private Hyperlink gamePathLink;

    @FXML
    private MenuItem help_About;

    @FXML
    private TextField playerName;

    @FXML
    private ImageView versionImage;

    @FXML
    private Label rootDirLabel;

    @FXML
    private Label playerNameLabel;

    @FXML
    private Pane infoPane;

    @FXML
    private TextArea logText;

    @FXML
    private Button launchButton;

    @FXML
    private Button rootDirChooseButton;

    @FXML
    private Circle unfoldButton;

    @FXML
    private WebView browser;

    @FXML
    private MenuItem help_WebSite;

    @FXML
    private Label gameVersion;

    @FXML
    private MenuItem file_Input;

    @FXML
    private MenuItem file_Close;

    private Minecraft[] mc;
    private int mc_pointer = 0;

    public void Init() {
        //launchLanguage(launcherUI.defaultLocale);
        WebEngine browser_eng = browser.getEngine();
        //TODO 替换rootDir
        try {
            mc = Minecraft.getMinecrafts(new MinecraftDirectory(rootDir));
            gameVersion.setText(mc[0].version);
            browser_eng.load(launcherUI.coverURL);
        }catch (java.lang.NullPointerException e){
            mc = new Minecraft[0];
            gameVersion.setText("未知");
        }
        try {
            gamePathLink.setText(getSelectMC().rootPath);
        }catch (Exception e){
            gamePathLink.setText("未知");
        }

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
                appendLog("游戏进程停止。返回码：" + String.valueOf(code)); // 游戏结束时输出状态码
            }
        };

        launcher.combinedDownloadCallback = new CallbackAdapter<Version>() {

            @Override
            public void done(Version result) {
                // 当完成时调用
                // 参数代表实际下载到的Minecraft版本
                appendLog("MC（版本 " + result + " )下载完成。");
            }

            @Override
            public void failed(Throwable e) {
                // 当失败时调用
                // 参数代表是由于哪个异常而失败的
                appendLog("下载出现错误。");
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
                appendLog("开始下载："+task.getURI());
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
                        appendLog("子任务取消："+task.getURI());
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
    }

    /**
     * @return 返回用户指定的玩家名。如果未指定，则返回缺省值“MQ”。
     * @author XiaoLi8848, 1662423349@qq.com
     */
    public String getPlayerName() {
        return playerName.getText() != "" ? playerName.getText() : "MQ";
    }

    /**
     * @return 返回用户当前选中的游戏类（{@link com.MQ.GameClass.Minecraft}）。
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
        if(rootDir != null && rootDir != "")
            launcherUI.launchGamer();
    }
/*
    *//**
     * @author XiaoLi8848, 1662423349@qq.com
     * 以特定语言重载UI上所有的文字。
     *//*
    public void launchLanguage(Locale locale) {
        launcherUI.defaultLocale = locale;
        launcherUI.resourceBundle = ResourceBundle.getBundle("UI/JavaFX/properties/UI-javafx", launcherUI.defaultLocale, launcher.class.getClassLoader());
        this.gameVersionLabel.setText(launcherUI.getResString(profile.UI_MAIN_VERSION_LABEL));
        this.playerNameLabel.setText(launcherUI.getResString(profile.UI_MAIN_PLAYER_NAME_LABEL));
        this.rootDirChooseButton.setText(launcherUI.getResString(profile.UI_MAIN_CHOOSE));
        this.launchButton.setText(launcherUI.getResString(profile.UI_MAIN_LAUNCH));
        this.rootDirLabel.setText(launcherUI.getResString(profile.UI_MAIN_ROOT_DIR_LABEL));
        this.gamePathLabel.setText(launcherUI.getResString(profile.UI_MAIN_PATH_LABEL));
    }*/

    /**
     * @author XiaoLi8848, 1662423349@qq.com
     * 弹出一个{@link DirectoryChooser}对话框，引导用户选择rootDir路径。
     */
    @FXML
    void chooseRootDir(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle(launcherUI.getResString(profile.UI_DIALOG_CHOOSE_ROOT_DIR));
        String chooserTemp = directoryChooser.showDialog(launcherUI.primaryStage).getPath();
        if (chooserTemp != null) {
            this.rootDir = chooserTemp;
        }
        Init();
    }

    public void appendLog(String text){
        logText.appendText(text + "\n");
    }

    @FXML
    void linkFire(ActionEvent event){
        Desktop desktop = Desktop.getDesktop();
        File dirToOpen = null;
        try {
            dirToOpen = new File(gamePathLink.getText());
            desktop.open(dirToOpen);
        } catch (Exception e) {
        }
    }

    @FXML
    void changeGameVersion(){
        if(mc_pointer < mc.length-1 && mc[mc_pointer].version != "" && mc[mc_pointer].version != null){
            mc_pointer++;
        }
    }

    @FXML
    void inputGame(ActionEvent event) {
        chooseRootDir(event);
    }

    @FXML
    void close(ActionEvent event) {
        launcherUI.primaryStage.close();
    }

    @FXML
    void gotoWebSite(ActionEvent event) {
        try {
            launcherUI.gotoWebSite(launcherUI.projectURL);
        } catch (IOException e) {
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
        }
        launcherUI_About_Controller controller;
        controller = a.getController();
        controller.Init();
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void updateVersionView(){
        gameVersion.setText(getSelectVersion());
        gamePathLink.setText(getSelectMC().rootPath);
    }
}

