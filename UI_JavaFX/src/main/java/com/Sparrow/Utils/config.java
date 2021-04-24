package com.Sparrow.Utils;

import com.Sparrow.Minecraft;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

public class config{
    JSONObject jsonObject;
    private static JSONObject userMould = new JSONObject();
    static{
        userMould.put("online",new JSONArray());
        userMould.put("offline",new JSONArray());
        userMould.put("lib",new JSONArray());
    }

    public config(Minecraft minecraft) throws IOException,NullPointerException{
        this.jsonObject = JSONObject.parseObject(FileUtils.readFileToString(new File(minecraft.path + "config.json"),"UTF-8"));
        checkOrCreate(minecraft);
    }

    public void checkOrCreate(Minecraft minecraft){
        if(this.jsonObject.get("version")==null){
            this.jsonObject.put("version",minecraft.version);
        }
        if(this.jsonObject.get("versionType")==null) {
            this.jsonObject.put("versionType", Pattern.compile(".*[a-zA-Z]+.*").matcher(minecraft.version).matches() ? "Release" : "NotRelease");
        }
    }

    public void putOnlineUserInfo(String userName, String password){
        if(this.jsonObject.get("users")==null){
            this.jsonObject.put("users",userMould);
        }
        JSONObject temp = new JSONObject();
        temp.put("name",userName);
        temp.put("password",password);
        this.jsonObject.getJSONObject("users").getJSONArray("online").add(temp);
    }

    public void putOfflineUserInfo(String userName){
        if(this.jsonObject.get("users")==null){
            this.jsonObject.put("users",userMould);
        }
        JSONObject temp = new JSONObject();
        temp.put("name",userName);
        temp.put("head",null);
        this.jsonObject.getJSONObject("users").getJSONArray("offline").add(temp);
    }

    public void putOfflineUserInfo(String userName, File pic) throws IOException{
        if(this.jsonObject.get("users")==null){
            this.jsonObject.put("users",userMould);
        }
        JSONObject temp = new JSONObject();
        temp.put("name",userName);
        temp.put("head",imageString.imageToString(pic.toString()));
        this.jsonObject.getJSONObject("users").getJSONArray("offline").add(temp);
    }
}
