package com.example.cupHead.fxController;

import com.example.cupHead.ViewApplication;
import com.example.cupHead.model.Boss;
import com.example.cupHead.model.Bullet;
import com.example.cupHead.model.CupHead;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import realController.GameController;

import java.net.URL;
import java.util.Random;
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
        GameController.setCupHead(new CupHead());
        GameController.setBoss(new Boss());
        GameController.setPane(pane);
        bossImage = GameController.getBoss().getImageView();
        pane.getChildren().add(bossImage);
        updateCupHeadPosition();
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                background.setX((background.getX() - 5));
                if (background.getX() <= -3532)
                    background.setX(0);
                bossImage.setImage(GameController.getBoss().getImageView().getImage());

            }
        };
        animationTimer.start();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                cupHeadImage.requestFocus();
            }
        });
        cupHeadImage.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode().getName()) {
                    case "Left", "A":
                        GameController.changePosition('L');
                        break;
                    case "Right", "D":
                        GameController.changePosition('R');
                        break;
                    case "Up", "W":
                        GameController.changePosition('U');
                        break;
                    case "Down", "S":
                        GameController.changePosition('D');
                        break;
                    case "Space":
                        GameController.shootBullets();
                        pane.getChildren().add(GameController.getBullets().get(GameController.getBullets().size() - 1).getImageView());
                        break;
                }
                updateCupHeadPosition();
            }
        });
        GameController.getTimerToBuildMiniBoss().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    GameController.newMiniBosses();
                });
            }
        },2000,10000);
    }

    private void updateCupHeadPosition() {
        cupHeadImage.setX(GameController.getCupHead().getImageView().getX());
        cupHeadImage.setY(GameController.getCupHead().getImageView().getY());
    }
}
