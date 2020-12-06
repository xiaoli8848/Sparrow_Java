package com.MQ;

import org.to2mbn.jmccc.auth.OfflineAuthenticator;
import org.to2mbn.jmccc.auth.yggdrasil.YggdrasilAuthenticator;
import org.to2mbn.jmccc.exec.GameProcessListener;
import org.to2mbn.jmccc.launch.LaunchException;
import org.to2mbn.jmccc.launch.LauncherBuilder;
import org.to2mbn.jmccc.mcdownloader.MinecraftDownloader;
import org.to2mbn.jmccc.mcdownloader.MinecraftDownloaderBuilder;
import org.to2mbn.jmccc.mcdownloader.download.DownloadCallback;
import org.to2mbn.jmccc.mcdownloader.download.DownloadTask;
import org.to2mbn.jmccc.mcdownloader.download.concurrent.CallbackAdapter;
import org.to2mbn.jmccc.option.LaunchOption;
import org.to2mbn.jmccc.option.MinecraftDirectory;
import org.to2mbn.jmccc.option.ServerInfo;
import org.to2mbn.jmccc.option.WindowSize;
import org.to2mbn.jmccc.version.Version;
import org.to2mbn.jmccc.version.Versions;

import java.io.IOException;
import java.net.URL;

public class launcher {
    static YggdrasilAuthenticator onlineAuth;
    public static void main(String[] args) {
        switch (Integer.parseInt(args[0])) {
            case 0: //离线登录
                launch_offline(
                        args[1],    //游戏路径
                        args[2],    //玩家昵称
                        Boolean.parseBoolean(args[3]),  //是否将调试信息输出到System.out
                        Boolean.parseBoolean(args[4]),  //是否对natives执行快速检查（比对文件大小）
                        Integer.parseInt(args[5]),  //最小内存
                        Integer.parseInt(args[6]),  //最大内存
                        Integer.parseInt(args[7]),  //窗口宽
                        Integer.parseInt(args[8]),  //窗口高
                        args[9] //进入游戏后要进入的服务器，可为null
                );
                break;
            case 1: //在线登录
                //TODO 在线登录

                break;
            case 2: //下载游戏
                download(
                        args[1],    //游戏版本
                        args[2] //目标路径
                );

        }
    }

    public static void launch_offline(String rootDir,
                                      String playerName,
                                      boolean debugPrint,
                                      boolean nativesFC,
                                      int minMemory,
                                      int maxMemory,
                                      int windowWidth,
                                      int windowsHeight,
                                      String serverURL
    ) {
        org.to2mbn.jmccc.launch.Launcher launcher = LauncherBuilder.create()
                .setDebugPrintCommandline(debugPrint)
                .setNativeFastCheck(nativesFC)
                .build();

        LaunchOption option = null;
        try {
            option = new LaunchOption(
                    (String) Versions.getVersions(new MinecraftDirectory(rootDir)).toArray()[0], // 游戏版本
                    new OfflineAuthenticator(playerName), // 使用离线验证
                    new MinecraftDirectory(rootDir));
            option.setMaxMemory(maxMemory);
            option.setMinMemory(minMemory);
            option.setWindowSize(WindowSize.window(windowWidth, windowsHeight));
            if (serverURL != null && serverURL != "") {
                URL svURL = new URL(serverURL);
                option.setServerInfo(new ServerInfo(serverURL.substring(0, serverURL.lastIndexOf(":") - 1), svURL.getPort()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 启动游戏
        try {
            launcher.launch(option, new GameProcessListener() {

                @Override
                public void onLog(String log) {
                    System.out.println(log); // 输出日志到控制台
                }

                @Override
                public void onErrorLog(String log) {
                    System.err.println(log); // 输出日志到控制台（同上）
                }

                @Override
                public void onExit(int code) {
                    System.err.println("游戏进程退出，状态码：" + code); // 游戏结束时输出状态码
                }
            });
        } catch (LaunchException e) {
            e.printStackTrace();
        }
    }

    public static void launch_online(String rootDir,
                                     String username,
                                     String password,
                                     boolean debugPrint,
                                     boolean nativesFC,
                                     int minMemory,
                                     int maxMemory,
                                     int windowWidth,
                                     int windowsHeight,
                                     String serverURL
    ) {
        org.to2mbn.jmccc.launch.Launcher launcher = LauncherBuilder.create()
                .setDebugPrintCommandline(debugPrint)
                .setNativeFastCheck(nativesFC)
                .build();

        LaunchOption option = null;
        try {
            onlineAuth = YggdrasilAuthenticator.password(username,password);
            option = new LaunchOption(
                    (String) Versions.getVersions(new MinecraftDirectory(rootDir)).toArray()[0], // 游戏版本
                    onlineAuth, // 使用在线验证
                    new MinecraftDirectory(rootDir));
            option.setMaxMemory(maxMemory);
            option.setMinMemory(minMemory);
            option.setWindowSize(WindowSize.window(windowWidth, windowsHeight));
            if (serverURL != null) {
                URL svURL = new URL(serverURL);
                option.setServerInfo(new ServerInfo(serverURL.substring(0, serverURL.lastIndexOf(":") - 1), svURL.getPort()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.to2mbn.jmccc.auth.AuthenticationException e){
            e.printStackTrace();
        }

        // 启动游戏
        try {
            launcher.launch(option, new GameProcessListener() {

                @Override
                public void onLog(String log) {
                    System.out.println(log); // 输出日志到控制台
                }

                @Override
                public void onErrorLog(String log) {
                    System.err.println(log); // 输出日志到控制台（同上）
                }

                @Override
                public void onExit(int code) {
                    System.err.println("游戏进程退出，状态码：" + code); // 游戏结束时输出状态码
                }
            });
        } catch (LaunchException e) {
            e.printStackTrace();
        }
    }

    public static void download(String version, String path){
        // 创建MinecraftDownloader
        MinecraftDownloader downloader = MinecraftDownloaderBuilder.create().build();

        // 下载Minecraft
        downloader.downloadIncrementally(new MinecraftDirectory(path), version, new CallbackAdapter<Version>() {

            @Override
            public void done(Version result) {
                // 当完成时调用
                // 参数代表实际下载到的Minecraft版本
                System.out.printf("下载完成，下载到的Minecraft版本：%s%n", result);
            }

            @Override
            public void failed(Throwable e) {
                // 当失败时调用
                // 参数代表是由于哪个异常而失败的
                System.out.printf("下载失败%n");
                e.printStackTrace();
            }

            @Override
            public void cancelled() {
                // 当被取消时调用
                System.out.printf("下载取消%n");
            }

            @Override
            public <R> DownloadCallback<R> taskStart(DownloadTask<R> task) {
                // 当有一个下载任务被派生出来时调用
                // 在这里返回一个DownloadCallback就可以监听该下载任务的状态
                System.out.printf("开始下载：%s%n", task.getURI());
                return new CallbackAdapter<R>() {

                    @Override
                    public void done(R result) {
                        // 当这个DownloadTask完成时调用
                        System.out.printf("子任务完成：%s%n", task.getURI());
                    }

                    @Override
                    public void failed(Throwable e) {
                        // 当这个DownloadTask失败时调用
                        System.out.printf("子任务失败：%s。原因：%s%n", task.getURI(), e);
                    }

                    @Override
                    public void cancelled() {
                        // 当这个DownloadTask被取消时调用
                        System.out.printf("子任务取消：%s%n", task.getURI());
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
                        System.out.printf("子任务重试[%d/%d]：%s。原因：%s%n", current, max, task.getURI(), e);
                    }
                };
            }
        });
    }

    public static void launch_offline(String gamePath, String userName, boolean debug, boolean fastCheck, int minMem, int maxMem, int windowWidth, int windowHeight, URL server){
        String[] args = {"0",userName,String.valueOf(debug),String.valueOf(fastCheck),String.valueOf(minMem),String.valueOf(maxMem),String.valueOf(windowWidth),String.valueOf(windowHeight),server.toString()};
        main(args);
    }
}
