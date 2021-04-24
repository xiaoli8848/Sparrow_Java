package com.Sparrow.Utils.Download;

import org.to2mbn.jmccc.mcdownloader.MinecraftDownloader;
import org.to2mbn.jmccc.mcdownloader.MinecraftDownloaderBuilder;
import org.to2mbn.jmccc.mcdownloader.provider.DefaultLayoutProvider;
import org.to2mbn.jmccc.mcdownloader.provider.MinecraftDownloadProvider;
import org.to2mbn.jmccc.mcdownloader.provider.MojangDownloadProvider;
import org.to2mbn.jmccc.mcdownloader.provider.forge.ForgeDownloadProvider;
import org.to2mbn.jmccc.mcdownloader.provider.liteloader.LiteloaderDownloadProvider;
import org.to2mbn.jmccc.option.MinecraftDirectory;

import static com.Sparrow.launcher.combinedDownloadCallback;

public class Download {
    public static final DefaultLayoutProvider defaultDownloadAPI = new MojangDownloadProvider();
    public static final ForgeDownloadProvider forgeProvider = new ForgeDownloadProvider();
    public static final LiteloaderDownloadProvider liteloaderProvider = new LiteloaderDownloadProvider();
    public static MinecraftDownloader downloader;

    public static void downloadGame(MinecraftDownloader downloader, String version, String path) {
        downloader.downloadIncrementally(new MinecraftDirectory(path), version, combinedDownloadCallback);
    }

    /**
     * @param maxConnections         设置下载时的最大链接数
     * @param maxConnectionsPerTour  设置NIO下每个I/O Dispatcher线程的最大链接数
     * @param connectionTimeout      设置连接超时的毫秒数
     * @param socketTimeout          设置Socket超时的毫秒数
     * @param downloadProvider       设置下载源
     * @param downloadProviders      添加拓展下载源到解析链中
     * @param poolMaxThreads         设置线程池的最大线程数
     * @param poolThreadLivingTime   设置线程池里线程在不使用后最大的存活时间（毫秒）
     * @param tries                  设置下载失败后最大的尝试次数（默认为3，不宜过大）
     * @param useDownloadVersionInfo 设置是否从json中指定的url下载（即1.9的新json格式，默认true）
     * @param checkAssetsHash        设置是否通过计算assets的hash来判断完整性（默认true）
     * @param checkLibrariesHash     设置是否通过计算libraries的hash来判断完整性（默认false，文件hash会与1.9新json中指定的hash值比较）
     */
    public static void downloadGame(
            String version,
            String path,
            int maxConnections,
            int maxConnectionsPerTour,
            int connectionTimeout,
            int socketTimeout,
            MinecraftDownloadProvider downloadProvider,
            MinecraftDownloadProvider[] downloadProviders,
            int poolMaxThreads,
            long poolThreadLivingTime,
            int tries,
            boolean useDownloadVersionInfo,
            boolean checkAssetsHash,
            boolean checkLibrariesHash
    ) {
        MinecraftDownloaderBuilder downloaderTemp =
                MinecraftDownloaderBuilder.create()
                        .setMaxConnections(maxConnections)
                        .setMaxConnectionsPerRouter(maxConnectionsPerTour)
                        .setConnectTimeout(connectionTimeout)
                        .setSoTimeout(socketTimeout)
                        .setBaseProvider(downloadProvider)
                        .setPoolMaxThreads(poolMaxThreads)
                        .setPoolThreadLivingTime(poolThreadLivingTime)
                        .setDefaultTries(tries)
                        .setUseVersionDownloadInfo(useDownloadVersionInfo)
                        .setCheckAssetsHash(checkAssetsHash)
                        .setCheckLibrariesHash(checkLibrariesHash)
                        .appendProvider(forgeProvider)
                        .appendProvider(liteloaderProvider);
        for (MinecraftDownloadProvider provider : downloadProviders) {
            downloaderTemp = downloaderTemp.appendProvider(provider);
        }
        downloader = downloaderTemp.build();
        downloader.downloadIncrementally(new MinecraftDirectory(path), version, combinedDownloadCallback);
    }

    /**
     * @param maxConnections         设置下载时的最大链接数
     * @param maxConnectionsPerTour  设置NIO下每个I/O Dispatcher线程的最大链接数
     * @param connectionTimeout      设置连接超时的毫秒数
     * @param socketTimeout          设置Socket超时的毫秒数
     * @param downloadProvider       设置下载源
     * @param poolMaxThreads         设置线程池的最大线程数
     * @param poolThreadLivingTime   设置线程池里线程在不使用后最大的存活时间（毫秒）
     * @param tries                  设置下载失败后最大的尝试次数（默认为3，不宜过大）
     * @param useDownloadVersionInfo 设置是否从json中指定的url下载（即1.9的新json格式，默认true）
     * @param checkAssetsHash        设置是否通过计算assets的hash来判断完整性（默认true）
     * @param checkLibrariesHash     设置是否通过计算libraries的hash来判断完整性（默认false，文件hash会与1.9新json中指定的hash值比较）
     */
    public static void downloadGame(
            String version,
            String path,
            int maxConnections,
            int maxConnectionsPerTour,
            int connectionTimeout,
            int socketTimeout,
            MinecraftDownloadProvider downloadProvider,
            int poolMaxThreads,
            long poolThreadLivingTime,
            int tries,
            boolean useDownloadVersionInfo,
            boolean checkAssetsHash,
            boolean checkLibrariesHash
    ) {
        MinecraftDownloaderBuilder downloaderTemp =
                MinecraftDownloaderBuilder.create()
                        .setMaxConnections(maxConnections)
                        .setMaxConnectionsPerRouter(maxConnectionsPerTour)
                        .setConnectTimeout(connectionTimeout)
                        .setSoTimeout(socketTimeout)
                        .setBaseProvider(downloadProvider)
                        .setPoolMaxThreads(poolMaxThreads)
                        .setPoolThreadLivingTime(poolThreadLivingTime)
                        .setDefaultTries(tries)
                        .setUseVersionDownloadInfo(useDownloadVersionInfo)
                        .setCheckAssetsHash(checkAssetsHash)
                        .setCheckLibrariesHash(checkLibrariesHash)
                        .appendProvider(forgeProvider)
                        .appendProvider(liteloaderProvider);
        downloader = downloaderTemp.build();
        downloader.downloadIncrementally(new MinecraftDirectory(path), version, combinedDownloadCallback);
    }

    /**
     * @param maxConnections         设置下载时的最大链接数
     * @param maxConnectionsPerTour  设置NIO下每个I/O Dispatcher线程的最大链接数
     * @param connectionTimeout      设置连接超时的毫秒数
     * @param socketTimeout          设置Socket超时的毫秒数
     * @param poolMaxThreads         设置线程池的最大线程数
     * @param poolThreadLivingTime   设置线程池里线程在不使用后最大的存活时间（毫秒）
     * @param tries                  设置下载失败后最大的尝试次数（默认为3，不宜过大）
     * @param useDownloadVersionInfo 设置是否从json中指定的url下载（即1.9的新json格式，默认true）
     * @param checkAssetsHash        设置是否通过计算assets的hash来判断完整性（默认true）
     * @param checkLibrariesHash     设置是否通过计算libraries的hash来判断完整性（默认false，文件hash会与1.9新json中指定的hash值比较）
     */
    public static void downloadGame(
            String version,
            String path,
            int maxConnections,
            int maxConnectionsPerTour,
            int connectionTimeout,
            int socketTimeout,
            int poolMaxThreads,
            long poolThreadLivingTime,
            int tries,
            boolean useDownloadVersionInfo,
            boolean checkAssetsHash,
            boolean checkLibrariesHash
    ) {
        MinecraftDownloaderBuilder downloaderTemp =
                MinecraftDownloaderBuilder.create()
                        .setMaxConnections(maxConnections)
                        .setMaxConnectionsPerRouter(maxConnectionsPerTour)
                        .setConnectTimeout(connectionTimeout)
                        .setSoTimeout(socketTimeout)
                        .setBaseProvider(defaultDownloadAPI)
                        .setPoolMaxThreads(poolMaxThreads)
                        .setPoolThreadLivingTime(poolThreadLivingTime)
                        .setDefaultTries(tries)
                        .setUseVersionDownloadInfo(useDownloadVersionInfo)
                        .setCheckAssetsHash(checkAssetsHash)
                        .setCheckLibrariesHash(checkLibrariesHash)
                        .appendProvider(forgeProvider)
                        .appendProvider(liteloaderProvider);
        downloader = downloaderTemp.build();
        downloader.downloadIncrementally(new MinecraftDirectory(path), version, combinedDownloadCallback);
    }

    /**
     * @param connectionTimeout      设置连接超时的毫秒数
     * @param socketTimeout          设置Socket超时的毫秒数
     * @param poolMaxThreads         设置线程池的最大线程数
     * @param tries                  设置下载失败后最大的尝试次数（默认为3，不宜过大）
     * @param useDownloadVersionInfo 设置是否从json中指定的url下载（即1.9的新json格式，默认true）
     * @param checkAssetsHash        设置是否通过计算assets的hash来判断完整性（默认true）
     * @param checkLibrariesHash     设置是否通过计算libraries的hash来判断完整性（默认false，文件hash会与1.9新json中指定的hash值比较）
     */
    public static void downloadGame(
            String version,
            String path,
            int connectionTimeout,
            int socketTimeout,
            int poolMaxThreads,
            int tries,
            boolean useDownloadVersionInfo,
            boolean checkAssetsHash,
            boolean checkLibrariesHash
    ) {
        MinecraftDownloaderBuilder downloaderTemp =
                MinecraftDownloaderBuilder.create()
                        .setConnectTimeout(connectionTimeout)
                        .setSoTimeout(socketTimeout)
                        .setBaseProvider(defaultDownloadAPI)
                        .setPoolMaxThreads(poolMaxThreads)
                        .setDefaultTries(tries)
                        .setUseVersionDownloadInfo(useDownloadVersionInfo)
                        .setCheckAssetsHash(checkAssetsHash)
                        .setCheckLibrariesHash(checkLibrariesHash)
                        .appendProvider(forgeProvider)
                        .appendProvider(liteloaderProvider);
        downloader = downloaderTemp.build();
        downloader.downloadIncrementally(new MinecraftDirectory(path), version, combinedDownloadCallback);
    }

    /**
     * @param checkAssetsHash    设置是否通过计算assets的hash来判断完整性（默认true）
     * @param checkLibrariesHash 设置是否通过计算libraries的hash来判断完整性（默认false，文件hash会与1.9新json中指定的hash值比较）
     */
    public static void downloadGame(
            String version,
            String path,
            boolean checkAssetsHash,
            boolean checkLibrariesHash
    ) {
        MinecraftDownloaderBuilder downloaderTemp =
                MinecraftDownloaderBuilder.create()
                        .setBaseProvider(defaultDownloadAPI)
                        .setCheckAssetsHash(checkAssetsHash)
                        .setCheckLibrariesHash(checkLibrariesHash)
                        .appendProvider(forgeProvider)
                        .appendProvider(liteloaderProvider);
        downloader = downloaderTemp.build();
        downloader.downloadIncrementally(new MinecraftDirectory(path), version, combinedDownloadCallback);
    }

    public static void downloadGame(String version, String path) {
        // 创建MinecraftDownloader
        MinecraftDownloader downloader =
                MinecraftDownloaderBuilder.create()
                        .setBaseProvider(defaultDownloadAPI)
                        //.appendProvider(forgeProvider)
                        //.appendProvider(liteloaderProvider)
                        .build();

        // 下载Minecraft
        downloader.downloadIncrementally(new MinecraftDirectory(path), version, combinedDownloadCallback);
    }
}
