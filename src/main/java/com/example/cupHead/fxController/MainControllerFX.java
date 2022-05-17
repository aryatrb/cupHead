package com.example.cupHead.fxController;

import com.example.cupHead.ViewApplication;
import com.example.cupHead.model.User;
import javafx.fxml.FXML;

import java.io.IOException;

public class MainControllerFX {
    @FXML
    public void profile() throws IOException {
        ViewApplication.sceneChanger("profileMenu.fxml");
    }

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
        ViewApplication.playMusic("gameMusic");
    }

    @FXML
    public void setting() throws IOException {
        ViewApplication.sceneChanger("settingMenu.fxml");
    }
}
