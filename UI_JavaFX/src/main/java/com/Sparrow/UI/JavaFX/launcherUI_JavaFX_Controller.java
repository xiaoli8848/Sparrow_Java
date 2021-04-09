package com.Sparrow.UI.JavaFX;

import com.Sparrow.Minecraft;
import com.Sparrow.Tools.SystemPlatform;
import com.Sparrow.Tools.dialog.expDialog;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import org.to2mbn.jmccc.option.MinecraftDirectory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
//        versionList.setContextMenu(versionList_ContextMenu);
        versionList.setOnContextMenuRequested(event -> {
            if(!versionList.getSelectionModel().isEmpty()){
                ContextMenu temp = new ContextMenu();

                MenuItem inputer = new MenuItem("导入游戏");
                inputer.setOnAction(event1 -> {
                    DirectoryChooser rootDirChooser = new DirectoryChooser();
                    rootDirChooser.setTitle("请选择游戏“.minecraft”文件夹。");
                    Init(rootDirChooser.showDialog(launcherUI_JavaFX.primaryStage).toString());
                });

                MenuItem versionList_MenuItem_OpenRootDir = new MenuItem("打开所选游戏路径");
                versionList_MenuItem_OpenRootDir.setOnAction(e -> {
                    try {
                        SystemPlatform.browseFile(versionList.getSelectionModel().getSelectedItem().rootPath);
                    } catch (IOException ioException) {
                        new expDialog().apply("打开路径错误", null, "打开游戏路径发生错误。可能游戏路径已被移动或删除。", ioException);
                    }
                });
                temp.getItems().addAll(inputer, versionList_MenuItem_OpenRootDir);

                temp.show(versionList, Side.RIGHT, 0, 0);
            }else {
                versionList_ContextMenu.show(versionList, Side.RIGHT, 0, 0);
            }
        });
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


            ImageView icon = new ImageView(this.getClass().getClassLoader().getResource("com/Sparrow/UI/JavaFX/imgs/mc_icon.png").toString());

            cell.setCenter(version);
            cell.setLeft(icon);

            setGraphic(cell);
        } else if (empty) {
            setText(null);
            setGraphic(null);
        }
    }
}