package com.MQ.UI.H5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class launcherUI_Controller{
    ServerSocket serverSkt=null;
    Socket clientSkt=null;
    BufferedReader in=null;
    PrintStream out=null;
    //构造方法
    public launcherUI_Controller(int port){
        System.out.println("服务器代理正在监听，端口："+port);
        try{
            serverSkt=new ServerSocket(port);
        }catch(IOException e){
            System.out.println("监听端口"+port+"失败！");
        }
    }
    //收到客户端请求
    public String getRequest(){
        String frmClt=null;
        try{
            clientSkt=serverSkt.accept();

        }catch(IOException e){
            System.out.println("连接失败");
        }
        try{
            in=new BufferedReader(new InputStreamReader(clientSkt.getInputStream()));
            out=new PrintStream(clientSkt.getOutputStream());

        }catch(IOException e){

        }
        try{
            frmClt=in.readLine();
            System.out.println("Server收到请求："+frmClt);
        }catch(Exception e){
            System.out.println("无法读取端口.......");
            System.exit(0);
        }
        return frmClt;
    }
    //发送响应给客户端
    public void sendResponse(String response){
        try{
            out.println(response);
            System.out.println("Server响应请求 ："+response);
        }catch(Exception ex){
            System.out.println("写端口失败！。。。");
            System.exit(0);
        }
    }

    public static void main(String args[]) {
        launcherUI_Controller sa = new launcherUI_Controller(8080);
        while (true) {
            sa.sendResponse(sa.getRequest());
        }
    }
}