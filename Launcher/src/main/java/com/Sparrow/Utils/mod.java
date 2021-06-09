package com.Sparrow.Utils;

import java.io.File;
import java.io.IOException;

public class mod extends File {
    private String modName;
    private boolean disabled;

    mod(String jarPath) throws IOException {
        super(jarPath);
        this.modName = this.getName().substring(0, this.getName().lastIndexOf("."));
        if (this.getName().substring(this.getName().lastIndexOf(".") + 1) != "jar") {
            this.disabled = true;
        } else {
            this.disabled = false;
        }
    }

    public String getModName() {
        return modName;
    }

    public void setDisabled(boolean disabled) {
        if(disabled){
            if(this.getName().substring(this.getName().lastIndexOf(".") + 1) == "jar"){
                this.disabled = this.renameTo(new File(this.toString().substring(0,this.toString().lastIndexOf("."))+".DISABLED"));
            }
        }else{
            if(this.getName().substring(this.getName().lastIndexOf(".") + 1) != "jar"){
                this.disabled = this.renameTo(new File(this.toString().substring(0,this.toString().lastIndexOf("."))+".JAR"));
            }
        }
    }

    public boolean isDisabled() {
        return disabled;
    }
}
