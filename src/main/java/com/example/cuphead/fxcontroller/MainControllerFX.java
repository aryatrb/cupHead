package com.example.cuphead.fxcontroller;

import com.example.cuphead.ViewApplication;
import com.example.cuphead.model.User;
import com.example.cuphead.realcontroller.GameController;
import com.example.cuphead.realcontroller.SettingController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainControllerFX implements Initializable {
    @FXML
    public void profile() throws IOException {
        ViewApplication.sceneChanger("profileMenu.fxml");
    }

    @FXML
    Pane pane;

    @FXML
    public void exit() throws IOException {
        ViewApplication.sceneChanger("loginMenu.fxml");
    }

    @FXML
    public void scoreboard() throws IOException {
        User.sort();
        ViewApplication.sceneChanger("scoreboard.fxml");
    }

    @FXML
    public void newGame() throws IOException {
        ViewApplication.sceneChanger("gameMenu.fxml");
        SettingController.playMusic("gameMusic");
    }

    @FXML
    public void setting() throws IOException {
        ViewApplication.sceneChanger("settingMenu.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        GameController.setPane(pane);
        SettingController.setEffect(pane);
    }
}
