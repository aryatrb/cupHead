package com.example.cuphead.fxcontroller;

import com.example.cuphead.ViewApplication;
import com.example.cuphead.realcontroller.SettingController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import com.example.cuphead.realcontroller.GameController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameEndControllerFX implements Initializable {
    @FXML
    Text time, score;

    @FXML
    Pane pane;

    @FXML
    public void backToMenu() throws IOException {
        ViewApplication.sceneChanger("mainMenu.fxml");
        SettingController.playMusic("menuMusic");
    }

    @FXML
    public void replay() throws IOException {
        ViewApplication.sceneChanger("gameMenu.fxml");
        SettingController.playMusic("gameMusic");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        long timeCount = (GameController.getEnd() - GameController.getStart()) / 1000;
        time.setText("Time: " + timeCount + " seconds");
        time.setX(time.getX() - time.getLayoutBounds().getWidth() / 2
                + GameController.getWindowWidth() * 0.03);
        score.setText("Score: " + GameController.getScore());
        score.setX(score.getX() - score.getLayoutBounds().getWidth() / 2
                + GameController.getWindowWidth() * 0.03);
        GameController.setPane(pane);
        SettingController.setEffect(pane);
    }
}
