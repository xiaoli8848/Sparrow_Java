package com.MQ.UI.JavaFX;

import com.MQ.GameClass.Minecraft;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class launcherUI_MCView extends AnchorPane {

    @FXML
    private Label path;

    @FXML
    private Circle unfoldButton;

    @FXML
    private ImageView versionImage;

    @FXML
    private Label version;

    private List<Minecraft> mc;
    private int mc_pointer = 0;

    public launcherUI_MCView(Minecraft[] mcs) {
        mc = Arrays.asList(mcs);
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("UI/JavaFX/launcherUI_javafx_MCView.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            if(mcs.length != 0) {
                this.version.setText(mc.get(0).version);
                this.path.setText(mc.get(0).rootPath);
            }else{
                this.version.setText("未知");
                this.path.setText("未知");
            }
            loader.load();
        } catch (Exception e) {
        }
    }

    public void addMC(Minecraft minecraft){
        mc.add(minecraft);
    }

    public Minecraft getSelectMC(){
        return mc.get(mc_pointer);
    }

    public void changeMC(Minecraft[] mcs){
        mc=Arrays.asList(mcs);
        mc_pointer=0;
    }

    @FXML
    public void changeVersion(ActionEvent event){
        if(mc_pointer < mc.size()-1){
            mc_pointer++;
            this.version.setText(mc.get(mc_pointer).version);
            this.path.setText(mc.get(mc_pointer).rootPath);
        }
    }
}

