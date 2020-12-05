package com.MQ;

import org.to2mbn.jmccc.mcdownloader.MinecraftDownloader;
import org.to2mbn.jmccc.mcdownloader.MinecraftDownloaderBuilder;
import org.to2mbn.jmccc.mcdownloader.download.DownloadCallback;
import org.to2mbn.jmccc.mcdownloader.download.DownloadTask;
import org.to2mbn.jmccc.mcdownloader.download.concurrent.CallbackAdapter;
import org.to2mbn.jmccc.option.MinecraftDirectory;
import org.to2mbn.jmccc.version.Version;

public class launcher {
    /*public static void main(String[] args){
        org.to2mbn.jmccc.launch.Launcher launcher = LauncherBuilder.create()
                .setDebugPrintCommandline(true)
                .setNativeFastCheck(false)
                .build();

        LaunchOption option = null; // .minecraft目录
        try {
            option = new LaunchOption(
                    "1.9", // 游戏版本
                    new OfflineAuthenticator("test_user"), // 使用离线验证，用户名test_user
                    new MinecraftDirectory("D:/test/.minecraft"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 最大内存2048M
        option.setMaxMemory(2048);

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
*/
    public static void main(String[] args) {
        // 下载位置（要下载到的.minecraft目录）
        MinecraftDirectory dir = new MinecraftDirectory("D:/test/.minecraft");

        // 创建MinecraftDownloader
        MinecraftDownloader downloader = MinecraftDownloaderBuilder.create().build();

        // 下载Minecraft1.9
        downloader.downloadIncrementally(dir, "1.12.2", new CallbackAdapter<Version>() {

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

}