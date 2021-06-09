package com.Sparrow.Utils;

import java.io.File;

/**
 * 存档类，表示 {@code .minecraft/saves/}下的存档。
 */
public class save extends File {
    private String name;
    private File image;

    save(String path, String name, File image) {
        super(path);
        this.name = name;
        this.image = image;
    }

    @Override
    public String getName() {
        return name;
    }

    public File getImage() {
        return image;
    }
}
