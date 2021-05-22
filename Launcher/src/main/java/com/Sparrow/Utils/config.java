package com.Sparrow.Utils;

import com.Sparrow.Utils.user.libUser;
import com.Sparrow.Utils.user.offlineUser;
import com.Sparrow.Utils.user.onlineUser;
import com.Sparrow.launcher;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class config {
    private static final JSONObject userMoulde = new JSONObject();

    static {
        userMoulde.put("online", new JSONArray());
        userMoulde.put("offline", new JSONArray());
        userMoulde.put("lib", new JSONArray());
    }

    private JSONObject versionJson;
    private final File versionJsonFile;
    private final JSONObject packJson = new JSONObject();
    private final File packJsonFile;

    public config(Minecraft minecraft) throws IOException, NullPointerException {
        File configFileTemp = new File(launcher.WorkPath.toString() + File.separator + "config.json");
        if (!configFileTemp.exists()) {
            configFileTemp.createNewFile();
        }
        this.versionJsonFile = configFileTemp;
        this.versionJson = JSONObject.parseObject(FileUtils.readFileToString(configFileTemp, "UTF-8"));
        if (this.versionJson == null) {
            this.versionJson = new JSONObject();
        }
        this.packJsonFile = new File(launcher.WorkPath.toString() + File.separator + "pack.json");

        checkOrCreate(minecraft);
    }

    public void checkOrCreate(Minecraft minecraft) {
        if (this.versionJson.get("version") == null) {
            this.versionJson.put("version", minecraft.getVersion().getVersion());
        }
        if (this.versionJson.get("versionType") == null) {
            this.versionJson.put("versionType", minecraft.getVersion().getType());
        }
    }

    public void putOnlineUserInfo(onlineUser onlineUser) throws UserInfoGettingException {
        if (this.versionJson.get("users") == null) {
            this.versionJson.put("users", userMoulde);
        }
        if (!onlineUser.getInfo()) {
            throw new UserInfoGettingException();
        }
        JSONObject temp = new JSONObject();
        temp.put("name", onlineUser.getUserName());
        temp.put("password", onlineUser.getPassword());
        this.versionJson.getJSONObject("users").getJSONArray("online").add(temp);
    }

    public void putOfflineUserInfo(offlineUser offlineUser) throws IOException {
        if (this.versionJson.get("users") == null) {
            this.versionJson.put("users", userMoulde);
        }
        JSONObject temp = new JSONObject();
        temp.put("name", offlineUser.getUserName());
        if (!offlineUser.haveDefaultTexture())
            temp.put("texture", imageString.imageToString(offlineUser.getTexture().getFullTextureFile().toString()));
        this.versionJson.getJSONObject("users").getJSONArray("offline").add(temp);
    }

    public void putLibUserInfo(libUser libUser) throws UserInfoGettingException {
        if (this.versionJson.get("users") == null) {
            this.versionJson.put("users", userMoulde);
        }
        if (!libUser.getInfo()) {
            throw new UserInfoGettingException();
        }
        JSONObject temp = new JSONObject();
        temp.put("name", libUser.getUserName());
        temp.put("texture", libUser.getPassword());
        temp.put("server", libUser.getServer());
        this.versionJson.getJSONObject("users").getJSONArray("lib").add(temp);
    }

    public void createPack(String name, String author, File icon) throws IOException {
        if (!this.packJsonFile.exists()) {
            this.packJsonFile.createNewFile();
        }
        this.packJson.put("name", name);
        this.packJson.put("author", author);
        this.packJson.put("createTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));
        this.packJson.put("icon", imageString.imageToString(icon.toString()));
    }

    public String getPackName() {
        return this.packJson.getString("name");
    }

    public void setPackName(String name) {
        if (this.packJson.get("name") == null) {
            this.packJson.put("name", name);
        } else {
            this.packJson.remove("name");
            this.packJson.put("name", name);
        }
    }

    public List<offlineUser> getOfflineUsers() {
        List<JSONObject> temp = JSONObject.parseArray(versionJson.getJSONObject("users").getJSONArray("offline").toJSONString(), JSONObject.class);
        ArrayList<offlineUser> result = new ArrayList<>();
        for (JSONObject t : temp) {
            result.add(new offlineUser(t.getString("name")));
        }
        return result;
    }

    public List<onlineUser> getOnlineUsers() {
        List<JSONObject> temp = JSONObject.parseArray(versionJson.getJSONObject("users").getJSONArray("online").toJSONString(), JSONObject.class);
        ArrayList<onlineUser> result = new ArrayList<>();
        for (JSONObject t : temp) {
            result.add(new onlineUser(t.getString("name"), t.getString("password")));
        }
        return result;
    }

    public List<libUser> getLibUsers() {
        List<JSONObject> temp = JSONObject.parseArray(versionJson.getJSONObject("users").getJSONArray("lib").toJSONString(), JSONObject.class);
        ArrayList<libUser> result = new ArrayList<>();
        for (JSONObject t : temp) {
            result.add(new libUser(t.getString("name"), t.getString("password"), t.getString("server")));
        }
        return result;
    }

    public boolean haveUsers() {
        return versionJson.getJSONObject("users") != null;
    }

    public void flush() throws IOException {
        FileUtils.writeStringToFile(versionJsonFile, versionJson.toString(), "UTF-8");
    }

    public class UserInfoGettingException extends Exception {

    }
}