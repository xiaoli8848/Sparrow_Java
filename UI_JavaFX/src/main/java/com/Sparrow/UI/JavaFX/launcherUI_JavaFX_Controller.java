package com.Sparrow.UI.JavaFX;

import com.Sparrow.Minecraft;
import com.Sparrow.Tools.SystemPlatform;
import com.Sparrow.Tools.dialog.expDialog;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import org.to2mbn.jmccc.option.MinecraftDirectory;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class launcherUI_JavaFX_Controller {
    @FXML
    private ListView<Minecraft> versionList;

    @FXML
    private Pane launchPane;

    @FXML
    private TabPane versionSummary;

    @FXML
    private ListView<Minecraft.save> gameSaves;

    @FXML
    private ListView<?> gameMods;

    @FXML
    private ListView<?> gameResPacks;

    @FXML
    private ImageView closeButton;

    @FXML
    private ImageView minisizeButton;

    @FXML
    private Label State;

    @FXML
    private Label Version;

    @FXML
    private BorderPane launchButton;

    @FXML
    private Label versionText;

    public void install() {
        launchPane.setVisible(false);
        //设置ListView视图模板类
        versionList.setCellFactory(param -> new minecraftCell());
        gameSaves.setCellFactory(param -> new savesCell());

        //设置右键菜单
        ContextMenu versionList_ContextMenu = new ContextMenu();
        MenuItem inputGame = new MenuItem("导入游戏");
        inputGame.setOnAction(event -> {
            DirectoryChooser rootDirChooser = new DirectoryChooser();
            rootDirChooser.setTitle("请选择游戏“.minecraft”文件夹。");
            try {
                Init(rootDirChooser.showDialog(launcherUI_JavaFX.primaryStage).toString());
            } catch (Exception e) {
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
                    try {
                        Init(rootDirChooser.showDialog(launcherUI_JavaFX.primaryStage).toString());
                    } catch (Exception e) {
                        new expDialog().apply("导入错误", null, "游戏导入发生错误。", e);
                    }
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
            } else {
                versionList_ContextMenu.show(versionList, Side.RIGHT, 0, 0);
            }
        });

        //设置控件事件
        versionList.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends Minecraft> observable, Minecraft oldValue, Minecraft newValue) -> {
                    launchPane.setVisible(true);

                    versionText.setText(newValue.version);

                    try {
                        gameSaves.getItems().remove(0, gameSaves.getItems().size() - 1);
                        gameSaves.getItems().remove(0);
                    }catch (java.lang.IndexOutOfBoundsException e){

                    }
                    gameSaves.getItems().addAll(newValue.saves);
                }
        );

        //自动导入程序目录下的游戏
        try {
            Init(System.getProperty("user.dir") + ".minecraft");
        } catch (Exception e) {

        }
    }

    public void Init(String rootDir) throws Exception {
        final File test = new File(rootDir);
        if (!test.exists() || !test.isDirectory()) {
            return;
        }
        Minecraft[] minecrafts = Minecraft.getMinecrafts(new MinecraftDirectory(rootDir));
        versionList.getItems().addAll(minecrafts);
    }

    public void changeState(launcherState state) {
        State.setText(state.getStateString() + "。"); //TODO 使用栈或队列实现多状态显示
    }

    @FXML
    void windowButtonActive(MouseEvent event) {
        closeButton.setImage(new Image(getClass().getClassLoader().getResource("com/Sparrow/UI/JavaFX/imgs/WindowCloseButton_Active.png").toString()));
        minisizeButton.setImage(new Image(getClass().getClassLoader().getResource("com/Sparrow/UI/JavaFX/imgs/WindowMinsizeButton_Active.png").toString()));
    }

    @FXML
    void windowButtonCancel(MouseEvent event) {
        closeButton.setImage(new Image(getClass().getClassLoader().getResource("com/Sparrow/UI/JavaFX/imgs/WindowCloseButton.png").toString()));
        minisizeButton.setImage(new Image(getClass().getClassLoader().getResource("com/Sparrow/UI/JavaFX/imgs/WindowMinsizeButton.png").toString()));
    }

    @FXML
    void closeWindow(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void minsizeWindow(MouseEvent event) {
        launcherUI_JavaFX.primaryStage.setIconified(true);
    }

    @FXML
    void launch(MouseEvent event) {
        //TODO 实现在线启动
        versionList.getSelectionModel().getSelectedItem().launchOffline("xiaoli8848",true,true,0,0,1000,800,"");
    }
}


class minecraftCell extends ListCell<Minecraft> {

    private static boolean judgeContainsLetters(String cardNum) {
        String regex = ".*[a-zA-Z]+.*";
        Matcher m = Pattern.compile(regex).matcher(cardNum);
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
            Text date = new Text(launcherUI_JavaFX.DATE_FORMAT.format(new File(item.rootPath).lastModified()));
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

class savesCell extends ListCell<Minecraft.save> {
    @Override
    public void updateItem(Minecraft.save item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty && item != null) {
            BorderPane cell = new BorderPane();
            ImageView imageView = new ImageView(new Image("file:"+File.separator+item.image.toString()));
            HBox imageBox = new HBox(2);
            Pane space = new Pane();
            space.setPrefWidth(10);
            imageBox.getChildren().addAll(imageView, space);

            VBox textBox = new VBox(2);
            Label saveName = new Label(item.name);
            saveName.setFont(javafx.scene.text.Font.font("DengXian", FontWeight.BOLD, 14));
            Label modDate = new Label(launcherUI_JavaFX.DATE_FORMAT.format(new File(item.path).lastModified()));
            modDate.setFont(javafx.scene.text.Font.font("DengXian", FontWeight.NORMAL, 8));
            textBox.getChildren().addAll(saveName, modDate);

            cell.setCenter(textBox);
            cell.setLeft(imageBox);

            setGraphic(cell);
        } else if (empty) {
            setText(null);
            setGraphic(null);
        }
    }
}