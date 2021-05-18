package com.Sparrow.Utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class version {
    private static File versionListFile = new File(version.class.getClassLoader().getResource("com/Sparrow/UI/JavaFX/versionList.json").toString().substring(6));
    private static JSONObject versionList;
    private File path;
    private String version;
    private String name;
    private versionType type;

    {
        try {
            versionList = JSONObject.parseObject(FileUtils.readFileToString(versionListFile, charsetGuess.guessCharset(versionListFile)));
        } catch (IOException exception) {
            versionList = null;
        }
    }

    public version(File versionPath) {
        this.path = versionPath;
        String versionName = versionPath.getName();
        String version = versionName;
        JSONObject tempJson = null;
        File[] jsons = versionPath.listFiles(new jsonFilter());
        for (File json : jsons) {
            try {
                tempJson = JSONObject.parseObject(FileUtils.readFileToString(json, charsetGuess.guessCharset(json)));
                versionName = tempJson.getString("id");
                version = tempJson.getString("assets");
                break;
            } catch (Exception e) {
                continue;
            }
        }
        this.version = version;
        this.name = versionName;
        try {
            for (int i = 0; i < versionList.getJSONArray("versions").size(); i++) {
                if (versionList.getJSONArray("versions").getJSONObject(i).getString("id") == this.version) {
                    switch (versionList.getJSONArray("versions").getJSONObject(i).getString("type")) {
                        case "release":
                            this.type = versionType.RELEASE;
                            break;
                        case "snapshot":
                            this.type = versionType.SNAPSHOT;
                            break;
                        case "old_alpha":
                            this.type = versionType.OLD_ALPHA;
                            break;
                        case "old_beta":
                            this.type = versionType.OLD_BETA;
                            break;
                    }
                }
            }
            if (this.type == null) {
                if (judgeContainsLetters(this.version)) {
                    this.type = versionType.SNAPSHOT;
                } else {
                    this.type = versionType.RELEASE;
                }
            }
        } catch (Exception e) {
            if (judgeContainsLetters(this.version)) {
                this.type = versionType.SNAPSHOT;
            } else {
                this.type = versionType.RELEASE;
            }
        }
    }

    private static boolean judgeContainsLetters(String cardNum) {
        String regex = ".*[a-zA-Z]+.*";
        Matcher m = Pattern.compile(regex).matcher(cardNum);
        return m.matches();
    }

    public static JSONObject getVersionList() {
        return versionList;
    }

    public static void setVersionList(File file) throws IOException {
        versionList = JSONObject.parseObject(FileUtils.readFileToString(file, charsetGuess.guessCharset(file)));
    }

    public static void setVersionList(JSONObject json) {
        versionList = json;
    }

    public String getVersion() {
        return version;
    }

    public String getName() {
        return name;
    }

    public versionType getType() {
        return type;
    }

    public File getPath() {
        return path;
    }
}
