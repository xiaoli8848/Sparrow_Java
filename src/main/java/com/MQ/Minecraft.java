package com.MQ;

import com.MQ.Tools.DownloadAPI.Download;
import com.MQ.UI.JavaFX.launcherUI;
import org.to2mbn.jmccc.mcdownloader.provider.forge.ForgeVersionList;
import org.to2mbn.jmccc.mcdownloader.provider.liteloader.LiteloaderVersion;
import org.to2mbn.jmccc.mcdownloader.provider.liteloader.LiteloaderVersionList;
import org.to2mbn.jmccc.option.MinecraftDirectory;

import java.io.File;

public class Minecraft {
    public String path;
    public String version;
    public String rootPath;

    private Minecraft() {
    }

    public static String[] getMinecraftVersions(MinecraftDirectory dir) {
        //Objects.requireNonNull(dir);
        String[] versions = new String[2];
        int ver_ptr = 0;
        File[] subdirs = dir.getVersions().listFiles();
        if (subdirs != null) {
            int var4 = subdirs.length;

            for (int var5 = 0; var5 < var4; ++var5) {
                File file = subdirs[var5];
                if (file.isDirectory() && doesVersionExist(dir, file.getName())) {
                    versions[ver_ptr++] = file.getName();
                }
            }
        }
        if (versions.length == 1) {
            String[] temp = new String[1];
            temp[0] = versions[0];
            return temp;
        }
        return versions;
    }

    private static boolean doesVersionExist(MinecraftDirectory minecraftDir, String version) {
        return minecraftDir.getVersionJson(version).isFile();
    }

    public static Minecraft[] getMinecrafts(MinecraftDirectory dir) {
        Minecraft[] result = new Minecraft[2];
        int result_ptr = 0;
        String[] versions = getMinecraftVersions(dir);
        for (String s : versions) {
            Minecraft temp = new Minecraft();
            temp.version = s;
            temp.path = dir.getRoot().toString() + "/versions/" + s + "/";
            temp.rootPath = dir.getRoot().getPath();
            result[result_ptr++] = temp;
        }
        if (result.length == 1) {
            Minecraft[] temp = new Minecraft[1];
            temp[0] = result[0];
            return temp;
        }
        return result;
    }

    public void launchOffline(String playername, boolean debug, boolean FC, int minMem, int maxMem, int width, int height, String serverURL) {
        try {
            launcher.launch_offline(rootPath, version, playername, debug, FC, minMem, maxMem, width, height, serverURL);
        }catch (Exception e){
            launcherUI.controller.printError(e);
        }
    }
}
