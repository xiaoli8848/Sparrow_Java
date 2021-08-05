package com.Sparrow.Utils.pack;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class mcPack {
    public static boolean pack(String zipPath, String rootDirPath) {
        File zipFile = new File(zipPath);
        if (!zipFile.exists()) {
            try {
                zipFile.createNewFile();
            } catch (IOException e) {
                return false;
            }
        }
        try {
            zip.toZip(rootDirPath, new FileOutputStream(zipFile), true);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean unpack(String zipPath, String toPath) {
        return zip.unZipFiles(zipPath, toPath);
    }
}
