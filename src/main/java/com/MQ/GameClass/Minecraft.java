package com.MQ.GameClass;

import org.to2mbn.jmccc.option.MinecraftDirectory;

import java.io.File;

public class Minecraft {
    public String path;
    public String version;

    private Minecraft(){
    }

    public static String[] getMinecraftVersions(MinecraftDirectory dir){
        //Objects.requireNonNull(dir);
        String[] versions = new String[2];
        int ver_ptr = 0;
        File[] subdirs = dir.getVersions().listFiles();
        if (subdirs != null) {
            File[] var3 = subdirs;
            int var4 = subdirs.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                File file = var3[var5];
                if (file.isDirectory() && doesVersionExist(dir, file.getName())) {
                    versions[ver_ptr++]=file.getName();
                }
            }
        }

        return versions;
    }

    private static boolean doesVersionExist(MinecraftDirectory minecraftDir, String version) {
        return minecraftDir.getVersionJson(version).isFile();
    }

    public static Minecraft[] getMinecrafts(MinecraftDirectory dir){
        Minecraft[] result = new Minecraft[2];
        int result_ptr=0;
        String[] versions = getMinecraftVersions(dir);
        for(int i=0;i<versions.length;i++){
            Minecraft temp = new Minecraft();
            temp.version = versions[i];
            temp.path = dir.getRoot().toString() + "/versions/" + versions[i] + "/";
            result[result_ptr++] = temp;
        }
        return result;
    }
}
