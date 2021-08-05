package com.Sparrow.Utils;

import com.Sparrow.Utils.Callback.launchCallback;
import org.to2mbn.jmccc.auth.Authenticator;
import org.to2mbn.jmccc.launch.LaunchException;
import org.to2mbn.jmccc.launch.LauncherBuilder;
import org.to2mbn.jmccc.mcdownloader.provider.forge.ForgeVersion;
import org.to2mbn.jmccc.mcdownloader.provider.liteloader.LiteloaderVersion;
import org.to2mbn.jmccc.option.LaunchOption;
import org.to2mbn.jmccc.option.MinecraftDirectory;
import org.to2mbn.jmccc.option.ServerInfo;
import org.to2mbn.jmccc.option.WindowSize;
import org.to2mbn.jmccc.version.Version;
import org.to2mbn.jmccc.version.Versions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static com.Sparrow.launcher.gameProcessListener;
import static com.Sparrow.launcher.setVersionTypeToMQ;

/**
 * 游戏版本类，表示 {@code .minecraft/versions/} 下的所有游戏版本。
 * 本类类似于 {@link MinecraftDirectory} ，且本类工厂方法参数含有 {@link MinecraftDirectory} ，但两者没有继承关系，本类封装了它的一些功能。
 */
public class Minecraft {
    private String path;
    private Version version;
    private String rootPath;
    private ArrayList<save> saves;
    private ArrayList<mod> mods;
    private config config;
    private ForgeVersion forgeVersion;
    private LiteloaderVersion liteloaderVersion;

    protected Minecraft() {
    }

    public Minecraft(String path, Version version, String rootPath, ArrayList<save> saves, ArrayList<mod> mods, config config) {
        this.path = path;
        this.version = version;
        this.rootPath = rootPath;
        this.saves = saves;
        this.mods = mods;
        this.config = config;
    }

    /**
     * 检测游戏目录下是否有指定版本。
     *
     * @param minecraftDir 游戏目录
     * @param version      游戏版本
     * @return boolean，目录下是否有指定版本。
     */
    public static boolean doesVersionExist(MinecraftDirectory minecraftDir, String version) {
        return minecraftDir.getVersionJson(version).isFile();
    }

    /**
     * 获取游戏根目录下所有游戏版本。
     *
     * @param dir 游戏根目录
     * @return 一个 {@link Minecraft} 类型的数组，表示游戏根目录下所有的游戏版本。
     */
    public static Minecraft[] getMinecrafts(MinecraftDirectory dir) {
        File[] versions = dir.getVersions().listFiles(pathname -> pathname.isDirectory() && doesVersionExist(dir, pathname.getName()));
        ArrayList<Minecraft> result = new ArrayList<>();
        for (File s : versions) {
            Minecraft temp = new Minecraft();
            try {
                temp.version = Versions.resolveVersion(dir, s.getName());
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            temp.path = s + File.separator;
            temp.rootPath = dir.getRoot().getPath();
            temp.saves = temp.getSaves(temp.getRootPath());
            temp.mods = temp.getMods(temp.getRootPath());
            try {
                temp.config = new config(temp);
                result.add(temp);
            } catch (IOException e) {
                result.add(temp);
                continue;
            }
        }
        return result.toArray(new Minecraft[versions.length]);
    }

    @Override
    public String toString() {
        return this.getVersion() + " - " + getPath();
    }

    /**
     * 启动游戏。
     *
     * @param launchCallback
     * @param authenticator
     * @param debugPrint
     * @param nativesFC
     * @param minMemory
     * @param maxMemory
     * @param windowWidth
     * @param windowHeight
     * @param serverURL
     * @throws LaunchException
     */
    public Process launch(launchCallback launchCallback, Authenticator authenticator, boolean debugPrint, boolean nativesFC, int minMemory, int maxMemory, int windowWidth, int windowHeight, String serverURL) throws LaunchException {
        org.to2mbn.jmccc.launch.Launcher launcher = LauncherBuilder.create()
                .setDebugPrintCommandline(debugPrint)
                .setNativeFastCheck(nativesFC)
                .build();
        launchCallback.onInstalling();

        LaunchOption option = null;
        try {
            option = new LaunchOption(
                    getVersion(), // 游戏版本
                    authenticator,
                    new MinecraftDirectory(getRootPath()));
            option.setMaxMemory(maxMemory);
            option.setMinMemory(minMemory);
            if (windowHeight > 0 && windowWidth > 0)
                option.setWindowSize(WindowSize.window(windowWidth, windowHeight));
            else
                option.setWindowSize(WindowSize.fullscreen());
            if (serverURL != null && !Objects.equals(serverURL, "")) {
                option.setServerInfo(new ServerInfo(serverURL.substring(0, serverURL.lastIndexOf(":")), Integer.parseInt(serverURL.substring(serverURL.lastIndexOf(":") + 1, serverURL.length() - 1))));
            }
            setVersionTypeToMQ(option);
            launchCallback.onResolvingOptions();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 启动游戏
        launchCallback.onLaunch();
        return launcher.launch(option, gameProcessListener).getProcess();
    }

    protected ArrayList<save> getSaves(String rootDir) {
        File[] saves = new File(rootDir + File.separator + "saves" + File.separator).listFiles();
        ArrayList<save> temp = new ArrayList<>();
        if (saves == null) {
            return new ArrayList<>();
        }
        for (File save : saves) {
            temp.add(new save(save.toString(), save.getName(), new File(save + File.separator + "icon.png")));
        }
        return temp;
    }

    protected ArrayList<mod> getMods(String rootDir) {
        File[] mods = new File(rootDir + File.separator + "mods" + File.separator).listFiles();
        ArrayList<mod> temp = new ArrayList<>();
        if (mods == null) {
            return new ArrayList<>();
        }
        for (File mod : mods) {
            try {
                temp.add(new mod(mod.toString()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return temp;
    }

    public String getPath() {
        return path;
    }

    public Version getVersion() {
        return version;
    }

    public String getRootPath() {
        return rootPath;
    }

    public ArrayList<save> getSaves() {
        return saves;
    }

    public ArrayList<mod> getMods() {
        return mods;
    }

    public com.Sparrow.Utils.config getConfig() {
        return config;
    }

}

