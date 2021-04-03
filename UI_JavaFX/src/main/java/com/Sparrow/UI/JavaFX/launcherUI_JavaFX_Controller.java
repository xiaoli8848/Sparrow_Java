package com.Sparrow.UI.JavaFX;

import com.Sparrow.Minecraft;
import com.Sparrow.Tools.SystemPlatform;
import com.Sparrow.Tools.dialog.expDialog;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import org.to2mbn.jmccc.option.MinecraftDirectory;

import java.io.File;

public class launcherUI_JavaFX_Controller {
    @FXML
    private ListView<Minecraft> versionList;

    @FXML
    private AnchorPane launchPane;

    @FXML
    private ListView<Minecraft> versionSummary;

    public void install(){
        versionList.setCellFactory(param -> new minecraftCell());

        ContextMenu versionList_ContextMenu = new ContextMenu();
        MenuItem inputGame = new MenuItem("导入游戏");
        inputGame.setOnAction(event -> {
            DirectoryChooser rootDirChooser = new DirectoryChooser();
            rootDirChooser.setTitle("请选择游戏“.minecraft”文件夹。");
            Init(rootDirChooser.showDialog(launcherUI_JavaFX.primaryStage).toString());
        });
        versionList_ContextMenu.getItems().addAll(inputGame);
        versionList.setContextMenu(versionList_ContextMenu);
    }

    public void Init(String rootDir) {
        final File test = new File(rootDir);
        if (!test.exists() || !test.isDirectory()) {
            return;
        }
        try {
            versionList.getItems().addAll(Minecraft.getMinecrafts(new MinecraftDirectory(rootDir)));
        } catch (Exception e) {
            new expDialog().apply("导入错误", null, "游戏导入发生错误。", e);
        }
    }
}


class minecraftCell extends ListCell<Minecraft> {

    @Override
    public void updateItem(Minecraft item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty && item != null) {
            BorderPane cell = new BorderPane();

            Text version = new Text(item.version);
            version.setFont(javafx.scene.text.Font.font("DengXian", FontWeight.BOLD, 20));

            ContextMenu versionList_OpenRootDir = new ContextMenu();
            MenuItem inputGame = new MenuItem("打开游戏路径");
            inputGame.setOnAction(event -> {
                try {
                    SystemPlatform.browseFile(item.rootPath);
                } catch (Exception e) {
                    new expDialog().apply("打开路径失败", null, "不好意思，打开游戏版本所在路径失败。", e);
                }
            });
            versionList_OpenRootDir.getItems().addAll(inputGame);

            ImageView icon = new ImageView(this.getClass().getClassLoader().getResource("com/Sparrow/UI/JavaFX/imgs/mc_icon.png").toString());

            cell.setCenter(version);
            cell.setLeft(icon);
            cell.setOnContextMenuRequested(event -> {
                versionList_OpenRootDir.show(cell, Side.BOTTOM,0,0);
            });

            setGraphic(cell);
        } else if (empty) {
            setText(null);
            setGraphic(null);
        }
    }
}
