package com.example.cuphead.fxcontroller;

import com.example.cuphead.model.Boss;
import com.example.cuphead.model.CupHead;
import com.example.cuphead.realcontroller.SettingController;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import com.example.cuphead.realcontroller.GameController;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class GameMenuControllerFX implements Initializable {

    private static AnimationTimer animationTimer;

    @FXML
    VBox vbox;

    @FXML
    AnchorPane pane;

    @FXML
    private ImageView background;

    private final boolean[] secondKeys = new boolean[5];
    private final Text timerText = new Text();
    private long spaceKeyTime;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startTheGame();
        makeAnimationTimer();
        Platform.runLater(() -> GameController.getCupHead().getImageView().requestFocus());
        GameController.getCupHead().getImageView().setOnKeyPressed(keyEvent ->
                pressedOrReleased(keyEvent.getCode().getName(), true));
        GameController.getCupHead().getImageView().setOnKeyReleased(keyEvent ->
                pressedOrReleased(keyEvent.getCode().getName(),
                        false));
        GameController.getTimerToBuildMiniBoss().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(GameController::newMiniBosses);
            }
        }, 2000, 10000);
    }

    private void spaceClick(boolean isForced) {
        if (GameController.shootBullets(isForced))
            pane.getChildren().add(GameController.getBullets()
                    .get(GameController.getBullets().size() - 1).getImageView());
    }

    private void startTheGame() {
        GameController.setTimerToBuildMiniBoss(new Timer());
        GameController.setEnd(0);
        Arrays.fill(secondKeys, false);
        GameController.setPane(pane);
        SettingController.setEffect(pane);
        GameController.setCupHead(new CupHead());
        GameController.setBoss(new Boss());
        GameController.setStart(System.currentTimeMillis());
        timerText.setText(GameController.getTimerText());
        timerText.setX(10);
        timerText.setY(GameController.getWindowHeight() * 0.97);
        timerText.setFill(Paint.valueOf("WHITE"));
        timerText.setFont(new Font(57));
        pane.getChildren().add(timerText);
    }

    private void makeAnimationTimer() {
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                background.setX((background.getX() - 5));
                if (background.getX() <= -3645.9354)
                    background.setX(0);
                timerText.setText(GameController.getTimerText());
                if (GameController.getCupHead().getHealth() <= 0) {
                    try {
                        GameController.endTheGame(false);
                    } catch (IOException ignored) {
                    }
                }
                for (int i = 0; i < secondKeys.length - 1; i++)
                    if (secondKeys[i])
                        GameController.changeCupHeadPosition(i);
                if (secondKeys[4] && System.currentTimeMillis() - spaceKeyTime > 200)
                    spaceClick(false);
                //thanks to the link shared by Erfan Jafari - gitHub: Jefri021
                // you can also find him on instagram: erf.jafari
            }
        };
        animationTimer.start();
    }

    private void pressedOrReleased(String name, boolean bool) {
        switch (name) {
            case "Left", "A" -> secondKeys[0] = bool;
            case "Right", "D" -> secondKeys[1] = bool;
            case "Up", "W" -> flyAnimationSetup(bool, 2);
            case "Down", "S" -> flyAnimationSetup(bool, 3);
            case "Space" -> {
                if (bool && !secondKeys[4]) {
                    spaceClick(true);
                    spaceKeyTime = System.currentTimeMillis();
                }
                secondKeys[4] = bool;
            }
        }
    }

    private void flyAnimationSetup(boolean bool, int arr) {
        int moving = 1;
        if (arr == 3)
            moving = -1;
        if (bool && !secondKeys[arr]) {
            GameController.getCupHead().setMoving(moving);
            GameController.getCupHead().setIsTurning(true);
            GameController.getCupHead().setStopping(false);
            GameController.getCupHead().setMovingCycle(0);
        } else if (!bool && secondKeys[arr]) {
            GameController.getCupHead().setStopping(true);
            if (!GameController.getCupHead().isTurning())
                GameController.getCupHead().setMovingCycle(10);
            else
                GameController.getCupHead().setIsTurning(false);
        }
        secondKeys[arr] = bool;
    }

    public static AnimationTimer getAnimationTimer() {
        return animationTimer;
    }
}
