package com.Sparrow.UI.JavaFX;

import com.Sparrow.Utils.MinecraftJFX;
import com.Sparrow.Utils.user.offlineUser;
import com.Sparrow.Utils.user.onlineUser;
import com.Sparrow.Utils.user.user;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListCell;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.List;

public class launcherUI_JavaFX_controlFrame_Controller {
    @FXML
    private JFXComboBox<user> characterChooser;

    @FXML
    private ImageView headTexture;

    @FXML
    private ImageView gameIcon;

    @FXML
    private Label gameVersion;

    @FXML
    private Label gameCotitle;

    @FXML
    private Button mcVersionChooseButton;

    private final launcherUI_JavaFX_Controller controller = launcherUI_JavaFX.controller;

    protected void install(){
        characterChooser.setCellFactory(param -> new userCell());
        characterChooser.selectionModelProperty().addListener(
                (observableValue, userSingleSelectionModel, t1) ->
                headTexture.setImage(characterChooser.getSelectionModel().getSelectedItem().getTexture().getHeadTexture())
        );
        controller.controller_versionList.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends MinecraftJFX> observable, MinecraftJFX oldValue, MinecraftJFX newValue) ->{
            if(newValue.version == newValue.versionName){
                gameVersion.setText(newValue.versionName);
                gameCotitle.setText(newValue.saves.size() + "个存档，" + newValue.mods.size() + "个模组。");
            }else {
                gameVersion.setText(newValue.versionName);
                gameCotitle.setText(newValue.version);
            }
        });
    }

    protected user getSelectedItem(){
        return characterChooser.getSelectionModel().getSelectedItem();
    }

    protected void addItem(user user){
        characterChooser.getItems().add(user);
    }

    protected void addItems(List<? extends user> users){
        characterChooser.getItems().addAll(users);
    }

    protected boolean isEmpty(){
        return characterChooser.getSelectionModel().isEmpty();
    }

    @FXML
    void gotoVersionList(){
        controller.Goto(controller.page_versionList);
    }
}

class userCell extends JFXListCell<user> {
    @Override
    public void updateItem(user item, boolean empty) {
        super.updateItem(item, empty);
        setText("");
        if (!empty && item != null) {
            BorderPane cell = new BorderPane();

            VBox textBox = new VBox(2);
            Text version = new Text(item.getUserName()); //如果版本号含有字母则标记为NotRelease
            version.setFont(launcherUI_JavaFX_Controller.FONT_COTITLE);
            Text info = new Text(item instanceof offlineUser ? "离线登录" : item instanceof onlineUser ? "在线登录" : "外置登录");
            textBox.getChildren().addAll(version, info);

            HBox iconBox = new HBox(2);
            ImageView icon = null;
            try {
                ImageView icon1 = new ImageView(item.getTexture().getHeadTexture());
                icon1.setFitWidth(40);
                icon1.setFitHeight(40);
                icon = icon1;
            } catch (Exception e) {
                e.printStackTrace();
            }
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