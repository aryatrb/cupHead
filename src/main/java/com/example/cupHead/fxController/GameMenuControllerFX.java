package com.example.cupHead.fxController;

import com.example.cupHead.model.Boss;
import com.example.cupHead.model.CupHead;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import realController.GameController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.TimerTask;

public class GameMenuControllerFX implements Initializable {

    @FXML
    VBox vbox;
    @FXML
    AnchorPane pane;
    @FXML
    private ImageView background, cupHeadImage, bossImage;

    AnimationTimer animationTimer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        GameController.setPane(pane);
        GameController.setCupHead(new CupHead(cupHeadImage));
        GameController.setBoss(new Boss());
        bossImage = GameController.getBoss().getImageView();
        pane.getChildren().add(bossImage);
        updateCupHeadPosition();
        GameController.setStart(System.currentTimeMillis());
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                background.setX((background.getX() - 5));
                if (background.getX() <= -3532)
                    background.setX(0);
                bossImage.setImage(GameController.getBoss().getImageView().getImage());
                if (GameController.getCupHead().getHealth() <= 0) {
                    try {
                        GameController.endTheGame(false);
                    } catch (IOException ignored) {

                    }
                }
            }
        };
        animationTimer.start();
        Platform.runLater(() -> cupHeadImage.requestFocus());
        cupHeadImage.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode().getName()) {
                case "Left", "A" -> GameController.changePosition('L');
                case "Right", "D" -> GameController.changePosition('R');
                case "Up", "W" -> GameController.changePosition('U');
                case "Down", "S" -> GameController.changePosition('D');
                case "Space" -> {
                    GameController.shootBullets();
                    pane.getChildren().add(GameController.getBullets()
                            .get(GameController.getBullets().size() - 1).getImageView());
                }
            }
            updateCupHeadPosition();
        });
        GameController.getTimerToBuildMiniBoss().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(GameController::newMiniBosses);
            }
        }, 2000, 10000);
    }

    private void updateCupHeadPosition() {
        cupHeadImage.setX(GameController.getCupHead().getImageView().getX());
        cupHeadImage.setY(GameController.getCupHead().getImageView().getY());
    }
}
