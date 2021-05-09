package com.Sparrow.Utils;

import com.Sparrow.Utils.dialog.errDialog;
import com.Sparrow.launcher;
import javafx.util.Pair;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
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
public class MinecraftJFX {
    public String path;
    public String version;
    public String versionName;
    public String rootPath;
    public ArrayList<save> saves;
    public ArrayList<mod> mods;
    public config config;

    /**
     * 为本类工厂方法和子类等提供私有无参构造函数，一般为private或protected。
     */
    protected MinecraftJFX() {
    }

    /**
     * 获取游戏目录下所有版本的版本号。
     *
     * @param dir 游戏根目录
     * @return {@link ArrayList<String>}，含有所有版本的版本号。
     */
    public static ArrayList<Pair<String, String>> getMinecraftVersions(MinecraftDirectory dir) {
        //Objects.requireNonNull(dir);
        ArrayList<Pair<String, String>> versions = new ArrayList<>();
        File[] subdirs = dir.getVersions().listFiles();
        if (subdirs != null) {
            for (File file : subdirs) {
                if (file.isDirectory() && doesVersionExist(dir, file.getName())) {
                    String versionName = file.getName();
                    String version;
                    JSONObject tempJson = null;
                    try {
                        tempJson = new JSONObject(FileUtils.readFileToString(file.listFiles(new jsonFilter())[0], charsetGuess.guessCharset(file.listFiles(new jsonFilter())[0])));
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (org.json.JSONException e) {
                        try {
                            tempJson = new JSONObject(FileUtils.readFileToString(file.listFiles(new jsonFilter())[1], charsetGuess.guessCharset(file.listFiles(new jsonFilter())[0])));
                        } catch (IOException exception){
                            e.printStackTrace();
                        }
                    }
                    try {
                        versionName = tempJson.getString("id");
                        try {
                            version = tempJson.getString("clientVersion");
                        } catch (org.json.JSONException e){
                            version = versionName;
                        }
                    } catch (NullPointerException exception) {
                        version = versionName;
                    }
                    versions.add(new Pair<>(version, versionName));
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
     * @return 一个 {@link MinecraftJFX} 类型的数组，表示游戏根目录下所有的游戏版本。
     */
    public static MinecraftJFX[] getMinecrafts(MinecraftDirectory dir) {
        ArrayList<Pair<String, String>> versions = new ArrayList<>(getMinecraftVersions(dir));
        ArrayList<MinecraftJFX> result = new ArrayList<>();
        for (Pair<String, String> s : versions) {
            MinecraftJFX temp = new MinecraftJFX();
            temp.version = s.getKey();
            temp.versionName = s.getValue();
            temp.path = dir.getRoot().toString() + File.separator + "versions" + File.separator + temp.versionName + File.separator;
            temp.rootPath = dir.getRoot().getPath();
            temp.saves = temp.getSaves(temp.rootPath);
            temp.mods = temp.getMods(temp.rootPath);
            try {
                temp.config = new config(temp);
                result.add(temp);
            } catch (IOException e) {
                result.add(temp);
                continue;
            }
        }
        return result.toArray(new MinecraftJFX[versions.size()]);
    }

    @Override
    public String toString() {
        return this.version + " - " + path;
    }

    public void launchOffline(String playername, boolean debug, boolean FC, int minMem, int maxMem, int width, int height, String serverURL) {
        launcher.launch_offline(rootPath, version, playername, debug, FC, minMem, maxMem, width, height, serverURL);
    }

    public void launch(Authenticator authenticator, boolean debugPrint, boolean nativesFC, int minMemory, int maxMemory, int windowWidth, int windowHeight, String serverURL) {
        org.to2mbn.jmccc.launch.Launcher launcher = LauncherBuilder.create()
                .setDebugPrintCommandline(debugPrint)
                .setNativeFastCheck(nativesFC)
                .build();

        LaunchOption option = null;
        try {
            option = new LaunchOption(
                    version, // 游戏版本
                    authenticator, // 使用离线验证
                    new MinecraftDirectory(rootPath));
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
        try {
            launcher.launch(option, gameProcessListener);
        } catch (LaunchException e) {
            new errDialog().apply("启动错误", null, e.toString());
        }
    }

    public MinecraftDirectory toMinecraftDirectory() {
        return new MinecraftDirectory(this.rootPath);
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