package com.MQ.UI.H5;

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
                        for(int i=0;i<versions.length;i++){
                            ans += versions[i].toString()+";";
                        }
                        ans=ans.substring(0,ans.length()-1);
                        connect.sendResponse(ans);
                    }catch (Exception e){
                        connect.sendResponse("0");
                    }
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
        try {
            frmClt = in.readLine();
            System.out.println("Server收到请求：" + frmClt);
        } catch (Exception e) {
            System.out.println("无法读取端口.......");
            System.exit(0);
        }
        return frmClt;
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
     * @param commandString
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