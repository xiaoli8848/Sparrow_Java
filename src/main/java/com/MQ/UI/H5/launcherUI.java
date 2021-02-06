package com.MQ.UI.H5;

public class launcherUI {
    public static void main(String[] args){
        controller controllerThread = new controller();
        controllerThread.start();
        launcherUI_JavaFX.main(new String[0]);
    }
}

class controller extends Thread{
    public void run(){
        launcherUI_Controller.main(new String[0]);
    }
}