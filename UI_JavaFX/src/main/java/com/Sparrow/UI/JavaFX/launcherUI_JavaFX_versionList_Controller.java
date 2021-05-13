package com.Sparrow.UI.JavaFX;

import com.Sparrow.Utils.MinecraftJFX;
import com.Sparrow.Utils.SystemPlatform;
import com.Sparrow.Utils.dialog.expDialog;
import com.jfoenix.controls.JFXListCell;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionModel;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class launcherUI_JavaFX_versionList_Controller {
    private final launcherUI_JavaFX_Controller controller = launcherUI_JavaFX.controller;
    @FXML
    private ListView<MinecraftJFX> versionList;

    protected void install() {
        versionList.setCellFactory(param -> new minecraftCell());
        //设置右键菜单
        ContextMenu versionList_ContextMenu = new ContextMenu();
        MenuItem inputGame = new MenuItem("导入游戏");
        inputGame.setOnAction(event -> {
            DirectoryChooser rootDirChooser = new DirectoryChooser();
            rootDirChooser.setTitle("请选择游戏“.minecraft”文件夹。");
            try {
                controller.Init(rootDirChooser.showDialog(launcherUI_JavaFX.primaryStage).toString());
            } catch (InitException e) {
                new expDialog().apply("导入错误", null, "游戏导入发生错误。", e);
            }
        });
        versionList_ContextMenu.getItems().addAll(inputGame);
//        versionList.setContextMenu(versionList_ContextMenu);
        versionList.setOnContextMenuRequested(event -> {
            if (!versionList.getSelectionModel().isEmpty()) {
                ContextMenu temp = new ContextMenu();

                MenuItem inputer = new MenuItem("导入游戏");
                inputer.setOnAction(event1 -> {
                    DirectoryChooser rootDirChooser = new DirectoryChooser();
                    rootDirChooser.setTitle("请选择游戏“.minecraft”文件夹。");
                    File result = rootDirChooser.showDialog(launcherUI_JavaFX.primaryStage);
                    if (result == null) {
                        return;
                    }
                    try {
                        controller.Init(result.toString());
                    } catch (InitException e) {
                        new expDialog().apply("导入错误", null, "游戏导入发生错误。", e);
                    }
                });

                MenuItem versionList_MenuItem_OpenRootDir = new MenuItem("打开所选游戏路径");
                versionList_MenuItem_OpenRootDir.setOnAction(e -> {
                    try {
                        SystemPlatform.browseFile(versionList.getSelectionModel().getSelectedItem().getRootPath());
                    } catch (IOException ioException) {
                        new expDialog().apply("打开路径错误", null, "打开游戏路径发生错误。可能游戏路径已被移动或删除。", ioException);
                    }
                });
                temp.getItems().addAll(inputer, versionList_MenuItem_OpenRootDir);

                temp.show(versionList, Side.RIGHT, 0, 0);
            } else {
                versionList_ContextMenu.show(versionList, Side.RIGHT, 0, 0);
            }
        });

        //设置控件事件
        versionList.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends MinecraftJFX> observable, MinecraftJFX oldValue, MinecraftJFX newValue) -> {
                    controller.launchPane.setVisible(true);

                    //versionText.setText(newValue.version);

                    try {
                        controller.gameSaves.getItems().remove(0, controller.gameSaves.getItems().size() - 1);
                        controller.gameSaves.getItems().remove(0);
                        controller.gameMods.getItems().remove(0, controller.gameSaves.getItems().size() - 1);
                        controller.gameMods.getItems().remove(0);
                    } catch (java.lang.IndexOutOfBoundsException e) {

                    }
                    controller.gameSaves.getItems().addAll(newValue.getSaves());
                    controller.gameMods.getItems().addAll(newValue.getMods());
                    controller.signWay.selectToggle(controller.signWay.getToggles().get(0));
                }
        );
    }

    protected void addItem(MinecraftJFX item) {
        versionList.getItems().add(item);
    }

    protected void addItems(List<MinecraftJFX> items) {
        versionList.getItems().addAll(items);
    }

    protected MinecraftJFX getSelectedItem() {
        return versionList.getSelectionModel().getSelectedItem();
    }

    protected boolean isEmpty() {
        return versionList.getItems().isEmpty();
    }

    protected SelectionModel<MinecraftJFX> getSelectionModel() {
        return versionList.getSelectionModel();
    }
}

class minecraftCell extends JFXListCell<MinecraftJFX> {
    @Override
    public void updateItem(MinecraftJFX item, boolean empty) {
        super.updateItem(item, empty);
        setText("");
        if (!empty && item != null) {
            BorderPane cell = new BorderPane();

            VBox textBox = new VBox(3);
            Text version = new Text(item.getVersion().getName() + " - " + item.getVersion().getType()); //如果版本号含有字母则标记为NotRelease
            version.setFont(javafx.scene.text.Font.font("DengXian", FontWeight.BOLD, 16));
            Text info;
            if (item.getConfig().getPackName() == null) {
                info = new Text(item.getSaves().size() + "个存档，" + item.getMods().size() + "个模组。");
            } else {
                info = new Text("整合包 - " + item.getConfig().getPackName());
            }
            Text date = new Text(launcherUI_JavaFX.DATE_FORMAT.format(new File(item.getRootPath()).lastModified()));
            date.setFont(javafx.scene.text.Font.font("DengXian", FontWeight.EXTRA_BOLD, 10));
            textBox.getChildren().addAll(version, info, date);

            HBox iconBox = new HBox(2);
            ImageView icon = new ImageView(this.getClass().getClassLoader().getResource("com/Sparrow/UI/JavaFX/imgs/mc_icon.png").toString());
            Pane space = new Pane();
            space.setPrefWidth(10);
            iconBox.getChildren().addAll(icon, space);

            cell.setCenter(textBox);
            cell.setLeft(iconBox);

            setGraphic(cell);
        } else if (empty) {
            setText(null);
            setGraphic(null);
        }
    }
}