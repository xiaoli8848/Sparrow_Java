package com.Sparrow.Utils;

import com.Sparrow.launcher;
import org.to2mbn.jmccc.auth.Authenticator;
import org.to2mbn.jmccc.launch.LaunchException;
import org.to2mbn.jmccc.launch.LauncherBuilder;
import org.to2mbn.jmccc.option.LaunchOption;
import org.to2mbn.jmccc.option.MinecraftDirectory;
import org.to2mbn.jmccc.option.ServerInfo;
import org.to2mbn.jmccc.option.WindowSize;

import java.io.File;
import java.io.FileFilter;
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
    private version version;
    private String rootPath;
    private ArrayList<save> saves;
    private ArrayList<mod> mods;
    private config config;

    /**
     * 为本类工厂方法和子类等提供私有无参构造函数，一般为private或protected。
     */
    protected Minecraft() {
    }

    /**
     * 获取游戏目录下所有版本的版本号。
     *
     * @param dir 游戏根目录
     * @return {@link ArrayList<String>}，含有所有版本的版本号。
     */
    public static ArrayList<version> getMinecraftVersions(MinecraftDirectory dir) {
        //Objects.requireNonNull(dir);
        ArrayList<version> versions = new ArrayList<>();
        File[] subdirs = dir.getVersions().listFiles();
        if (subdirs != null) {
            for (File file : subdirs) {
                if (file.isDirectory() && doesVersionExist(dir, file.getName())) {
                    versions.add(new version(file));
                }
            }
        }
        return versions;
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
        ArrayList<version> versions = new ArrayList<>(getMinecraftVersions(dir));
        ArrayList<Minecraft> result = new ArrayList<>();
        for (version s : versions) {
            Minecraft temp = new Minecraft();
            temp.version = s;
            temp.path = dir.getRoot().toString() + File.separator + "versions" + File.separator + s.getPath().getName() + File.separator;
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
        return result.toArray(new Minecraft[versions.size()]);
    }

    @Override
    public String toString() {
        return this.getVersion() + " - " + getPath();
    }

    public void launchOffline(String playername, boolean debug, boolean FC, int minMem, int maxMem, int width, int height, String serverURL) {
        launcher.launch_offline(getRootPath(), getVersion().getVersion(), playername, debug, FC, minMem, maxMem, width, height, serverURL);
    }

    public void launch(Authenticator authenticator, boolean debugPrint, boolean nativesFC, int minMemory, int maxMemory, int windowWidth, int windowHeight, String serverURL) throws LaunchException {
        org.to2mbn.jmccc.launch.Launcher launcher = LauncherBuilder.create()
                .setDebugPrintCommandline(debugPrint)
                .setNativeFastCheck(nativesFC)
                .build();

        LaunchOption option = null;
        try {
            option = new LaunchOption(
                    getVersion().getVersion(), // 游戏版本
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
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 启动游戏
        launcher.launch(option, gameProcessListener);
    }

    public MinecraftDirectory toMinecraftDirectory() {
        return new MinecraftDirectory(this.getRootPath());
    }

    protected ArrayList<save> getSaves(String rootDir) {
        File[] saves = new File(rootDir + File.separator + "saves" + File.separator).listFiles();
        ArrayList<save> temp = new ArrayList<>();
        if (saves == null) {
            return new ArrayList<>();
        }
        for (File save : saves) {
            save t = new save(save.toString());
            t.name = save.getName();
            t.image = new File(save + File.separator + "icon.png");
            temp.add(t);
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

    public com.Sparrow.Utils.version getVersion() {
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

    /**
     * 存档类，表示 {@code .minecraft/saves/}下的存档。
     */
    public class save extends File {
        public String name;
        public File image;

        private save(String path) {
            super(path);
        }
    }

    public class mod extends File {
        public String name;

        private mod(String jarPath) throws IOException {
            super(jarPath);
            this.name = this.getName().substring(0, this.getName().lastIndexOf(".jar"));
        }
    }
}

class jsonFilter implements FileFilter {
    @Override
    public boolean accept(File pathname) {
        return pathname.getName().toLowerCase().endsWith(".json");
        // 若pathname是文件夹 则返回true 继续遍历这个文件夹
    }
}