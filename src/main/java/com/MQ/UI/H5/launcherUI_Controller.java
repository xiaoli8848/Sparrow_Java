package com.MQ.UI.H5;

import com.MQ.launcher;
import org.to2mbn.jmccc.option.MinecraftDirectory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

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
            switch (command.summary){
                case "verify":
                    try {
                        File path = new File(command.args.get(0));
                        MinecraftDirectory gamePath = new MinecraftDirectory(path);
                        File[] versions = gamePath.getVersions().listFiles();
                        String ans="";
                        for (File version : versions) {
                            ans += version.toString() + ";";
                        }
                        ans=ans.substring(0,ans.length()-1);
                        connect.sendResponse(ans);
                    }catch (Exception e){
                        connect.sendResponse("0");
                    }
                    break;
                case "launch_offline":
                    try {
                        try{command.args.get(7);}catch (Exception e){
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
                    }catch (Exception e){
                        e.printStackTrace();
                        connect.sendResponse("0");
                        break;
                    }
                    connect.sendResponse("1");
                    break;
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
        String ans = t.substring(t.lastIndexOf("data:")+6,t.lastIndexOf("}"));
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