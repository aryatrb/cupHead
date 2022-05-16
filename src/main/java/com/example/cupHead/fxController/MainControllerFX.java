package com.example.cupHead.fxController;

import com.example.cupHead.ViewApplication;
import javafx.fxml.FXML;
import javafx.scene.media.MediaPlayer;

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
        ViewApplication.sceneChanger("scoreboard.fxml");
    }

    @FXML
    public void newGame() throws IOException {
        ViewApplication.sceneChanger("gameMenu.fxml");
        ViewApplication.playMusic("gameMusic");
    }
}
