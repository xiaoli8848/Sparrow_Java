package com.MQ;

import com.MQ.UI.JavaFX.launcherUI;
import org.to2mbn.jmccc.option.MinecraftDirectory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Minecraft {
    public String path;
    public String version;
    public String rootPath;

    private Minecraft() {
    }

    @Override
    public String toString() {
        return this.version + " - " + path;
    }

    private static Stack<String> getMinecraftVersions(MinecraftDirectory dir) {
        //Objects.requireNonNull(dir);
        Stack<String> versions = new Stack<>();
        File[] subdirs = dir.getVersions().listFiles();
        if (subdirs != null) {
            for (File file : subdirs) {
                if (file.isDirectory() && doesVersionExist(dir, file.getName())) {
                    versions.push(file.getName());
                }
            }
        }
        return versions;
    }

    private static boolean doesVersionExist(MinecraftDirectory minecraftDir, String version) {
        return minecraftDir.getVersionJson(version).isFile();
    }

    public static Minecraft[] getMinecrafts(MinecraftDirectory dir) {
        List<String> versions= new ArrayList<>(getMinecraftVersions(dir));
        ArrayList<Minecraft> result = new ArrayList<>();
        for (String s : versions) {
            Minecraft temp = new Minecraft();
            temp.version = s;
            temp.path = dir.getRoot().toString() + "/versions/" + s + "/";
            temp.rootPath = dir.getRoot().getPath();
            result.add(temp);
        }
        return result.toArray(new Minecraft[versions.size()]);
    }

    public void launchOffline(String playername, boolean debug, boolean FC, int minMem, int maxMem, int width, int height, String serverURL) {
        try {
            launcher.launch_offline(rootPath, version, playername, debug, FC, minMem, maxMem, width, height, serverURL);
        } catch (Exception e) {
            launcherUI.controller.printError(e);
        }
    }
}
