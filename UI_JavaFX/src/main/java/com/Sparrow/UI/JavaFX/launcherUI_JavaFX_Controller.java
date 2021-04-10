package com.Sparrow.UI.JavaFX;

import com.Sparrow.Minecraft;
import com.Sparrow.Tools.SystemPlatform;
import com.Sparrow.Tools.dialog.expDialog;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import org.to2mbn.jmccc.option.MinecraftDirectory;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class launcherUI_JavaFX_Controller {
    @FXML
    private ListView<Minecraft> versionList;

    @FXML
    private AnchorPane launchPane;

    @FXML
    private ListView<Minecraft> versionSummary;

    @FXML
    private Label State;

    @FXML
    private Label Version;

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

    public void changeState(launcherState state){
        State.setText(state.getStateString()+"。"); //TODO 使用栈或队列实现多状态显示
    }
}


class minecraftCell extends ListCell<Minecraft> {
    private static boolean judgeContainsLetters(String cardNum) {
        String regex=".*[a-zA-Z]+.*";
        Matcher m= Pattern.compile(regex).matcher(cardNum);
        return m.matches();
    }

    @Override
    public void updateItem(Minecraft item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty && item != null) {
            BorderPane cell = new BorderPane();

            VBox textBox = new VBox(3);
            Text version = new Text(judgeContainsLetters(item.version) ? item.version + " - NotRelease" : item.version + " - Release"); //如果版本号含有字母则标记为NotRelease
            version.setFont(javafx.scene.text.Font.font("DengXian", FontWeight.BOLD, 16));
            Text info = new Text("详细信息：暂无");  //TODO 后期加入整合包信息或存档概览
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Text date = new Text(format.format(new File(item.rootPath).lastModified()));
            date.setFont(javafx.scene.text.Font.font("DengXian", FontWeight.EXTRA_BOLD, 10));
            textBox.getChildren().addAll(version, info, date);

            HBox iconBox = new HBox(2);
            ImageView icon = new ImageView(this.getClass().getClassLoader().getResource("com/Sparrow/UI/JavaFX/imgs/mc_icon.png").toString());
            Pane space = new Pane();
            space.setPrefWidth(10);
            iconBox.getChildren().addAll(icon,space);

            cell.setCenter(textBox);
            cell.setLeft(iconBox);

            setGraphic(cell);
        } else if (empty) {
            setText(null);
            setGraphic(null);
        }
    }
}