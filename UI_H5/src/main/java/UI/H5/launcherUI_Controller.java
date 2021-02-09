package UI.H5;

import com.MQ.Tools.pack.mcPack;
import com.MQ.launcher;
import org.json.JSONArray;
import org.json.JSONObject;
import org.to2mbn.jmccc.option.MinecraftDirectory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class launcherUI_Controller {
    ServerSocket serverSkt = null;
    Socket clientSkt = null;
    BufferedReader in = null;
    PrintStream out = null;

    //构造方法
    public launcherUI_Controller(int port) {
        System.out.println("服务器代理正在监听，端口：" + port);
        try {
            serverSkt = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("监听端口" + port + "失败！");
        }
    }

    public static void main(String[] args) {
        launcherUI_Controller connect = new launcherUI_Controller(8080);
        while (true) {
            command command = new command(connect.getRequest());
            switch (command.summary) {
                case "verify":
                    try {
                        File path = new File(command.args.get(0));
                        MinecraftDirectory gamePath = new MinecraftDirectory(path);
                        File[] versions = gamePath.getVersions().listFiles();
                        JSONArray ans = new JSONArray();
                        for (File version : versions) {
                            JSONObject itemTemp = new JSONObject();
                            itemTemp.put("version",version.toString().substring(version.toString().lastIndexOf(File.separator)+1));
                            itemTemp.put("corePath",version.toString()+File.separator+itemTemp.getString("version")+".jar");
                            File settingsProperties = new File(version.toString()+File.separator+"independentSettings.properties");
                            if(!settingsProperties.exists()){
                                itemTemp.put("versionName","Minecraft");
                                itemTemp.put("setting","global");
                            }else{
                                Properties properties = new Properties();
                                properties.load(new BufferedInputStream (new FileInputStream(settingsProperties.toString())));
                                String nameTemp = "";
                                try{
                                    nameTemp = properties.getProperty("versionName");
                                }catch (Exception e){
                                    itemTemp.put("versionName","Minecraft");
                                    itemTemp.put("setting","global");
                                    break;
                                }
                                itemTemp.put("setting","independent");
                                itemTemp.put("versionName",nameTemp);
                                System.out.println(GetAllProperties(properties));
                                String source = "{" + GetAllProperties(properties) + "}";
                                String value = new JSONObject(source).toString();
                                itemTemp.put("independentSettings", value);
                            }
                            ans.put(itemTemp);
                        }
                        connect.sendResponse(ans.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                        connect.sendResponse("0");
                    }
                    break;
                case "launch_offline":
                    try {
                        try {
                            command.args.get(7);
                        } catch (Exception e) {
                            launcher.launch_offline(
                                    command.args.get(0),
                                    command.args.get(1),
                                    false,
                                    Boolean.parseBoolean(command.args.get(2)),
                                    Integer.parseInt(command.args.get(3)),
                                    Integer.parseInt(command.args.get(4)),
                                    Integer.parseInt(command.args.get(5)),
                                    Integer.parseInt(command.args.get(6)),
                                    null
                            );
                        }
                        launcher.launch_offline(
                                command.args.get(0),
                                command.args.get(1),
                                false,
                                Boolean.parseBoolean(command.args.get(2)),
                                Integer.parseInt(command.args.get(3)),
                                Integer.parseInt(command.args.get(4)),
                                Integer.parseInt(command.args.get(5)),
                                Integer.parseInt(command.args.get(6)),
                                command.args.get(7)
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                        connect.sendResponse("1");
                        break;
                    }
                    connect.sendResponse("0");
                    break;
                case "pack":
                    try {
                        if (!new MinecraftDirectory(command.args.get(0)).getVersions().exists()) {
                            connect.sendResponse("1");
                            break;
                        }
                        if (!new File(command.args.get(1)).isFile() || new File(command.args.get(1)).exists()) {
                            connect.sendResponse("1");
                            break;
                        }
                    } catch (Exception e) {
                        connect.sendResponse("1");
                        break;
                    }
                    try {
                        mcPack.pack(command.args.get(1), command.args.get(0));
                    } catch (Exception e) {
                        connect.sendResponse("2");
                        break;
                    }
                    connect.sendResponse("0");
                    break;
                case "unpack":
                    File zip = new File(command.args.get(0));
                    File to = new File(command.args.get(1));
                    if (!zip.exists() || !zip.isFile() || !to.exists() || !to.isDirectory()) {
                        connect.sendResponse("1");
                        break;
                    }
                    try {
                        mcPack.unpack(command.args.get(0), command.args.get(1));
                    } catch (Exception e) {
                        connect.sendResponse("2");
                        break;
                    }
                    connect.sendResponse("0");
                    break;
                case "close":
                    connect.sendResponse("HTTP/1.0 200 OK");
                    System.exit(0);
                    break;
                case "minimize":

                    launcherUI_JavaFX.controller.minimize();
                    connect.sendResponse("HTTP/1.0 200 OK");
                    break;
                case "appendProperties":
                    try {
                        File properties = new File(command.args.get(0) + "versions" + File.separator + command.args.get(1) + File.separator + "independentSettings.properties");
                        if (!properties.exists()) {
                            try {
                                properties.createNewFile();
                            } catch (IOException e) {
                                connect.sendResponse("1");
                                break;
                            }
                        }
                        Properties propertie = new Properties();
                        propertie.load(new BufferedInputStream (new FileInputStream(properties.toString())));
                        FileOutputStream fos = new FileOutputStream(properties.toString(), false);
                        OutputStreamWriter osw = new OutputStreamWriter(fos, "utf-8");
                        try{
                            propertie.get(command.args.get(2));
                        }catch (Exception e){
                            propertie.setProperty(command.args.get(2),command.args.get(3));
                            propertie.store(osw,"游戏参数");
                            break;
                        }
                        Map<String, String> keyValueMap = new HashMap<>();
                        keyValueMap.put(command.args.get(2), command.args.get(3));
                        for (String key: keyValueMap.keySet()) {
                            propertie.setProperty(key,keyValueMap.get(key));
                        }
                        propertie.store(osw,"游戏参数");

                        fos.close();
                        osw.close();
                        connect.sendResponse("0");
                        break;
                    }catch (Exception e){
                        connect.sendResponse("1");
                        break;
                    }
            }
        }
    }

    //收到客户端请求
    public String getRequest() {
        String frmClt = null;
        try {
            clientSkt = serverSkt.accept();

        } catch (IOException e) {
            System.out.println("连接失败");
        }
        try {
            in = new BufferedReader(new InputStreamReader(clientSkt.getInputStream()));
            out = new PrintStream(clientSkt.getOutputStream());

        } catch (IOException e) {

        }
        char[] temp = new char[1000];
        String t = "";
        try {
            in.read(temp);
            t = String.valueOf(temp);
            System.out.println("Server收到请求：" + t);
        } catch (Exception e) {
            System.out.println("无法读取端口.......");
            System.exit(0);
        }
        String ans = t.substring(t.lastIndexOf("{") + 1, t.lastIndexOf("}"));
        System.out.println(ans);
        return ans;
    }

    //发送响应给客户端
    public void sendResponse(String response) {
        try {
            out.println(response);
            System.out.println("Server响应请求 ：" + response);
        } catch (Exception ex) {
            System.out.println("写端口失败！。。。");
            System.exit(0);
        }
    }

    private static String GetAllProperties(Properties properties) throws IOException {
        String ans="";
        Set<Object> keys = properties.keySet();//返回属性key的集合
        for (Object key : keys) {
            ans+= key.toString() + ": " + properties.get(key) +",";
        }

        return ans.substring(0,ans.length()-1);
    }
}

class command {
    public String summary = "";
    public ArrayList<String> args = new ArrayList<>();

    /**
     * 将形如 "summary arg;arg;arg;..." 的字符串拆分
     *
     * @param commandString 形如 “summary arg;arg;arg” 的字符串
     */
    public command(String commandString) {
        String argsTemp = "";
        for (int i = 0; i < commandString.length(); i++) {
            if (commandString.charAt(i) != ' ')
                summary += commandString.charAt(i);
            else {
                argsTemp = commandString.substring(i + 1);
                break;
            }
        }
        if (argsTemp != "") {
            int startAt = 0;
            for (int i = 0; i < argsTemp.length(); i++) {
                if (argsTemp.charAt(i) == ';') {
                    String argTemp;
                    argTemp = argsTemp.substring(startAt, i);
                    args.add(argTemp);
                    startAt = i + 1;
                }
                if (i == argsTemp.length() - 1) {
                    args.add(argsTemp.substring(startAt));
                    break;
                }
            }
        }
    }
}