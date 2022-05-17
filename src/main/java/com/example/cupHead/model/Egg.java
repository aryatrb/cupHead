package com.example.cupHead.model;

import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import realController.GameController;
import realController.SettingController;

import java.io.IOException;

public class Egg extends Transition implements Armament {
    private final ImageView imageView;

    public Egg(double positionX, double positionY) {
        imageView = new ImageView();
        Image image = new Image("com/example/assets/boss/egg.png");
        imageView.setImage(image);
        imageView.setX(positionX);
        imageView.setY(positionY);
        imageView.setFitHeight(58);
        imageView.setFitWidth(50);
        this.setCycleDuration(Duration.millis(1000));
        this.setCycleCount(-1);
        this.play();
    }

    @Override
    public double getCapableDamage() {
        return 1 * SettingController.getDifficulty()
                .getGettingDamagedCoefficient();
    }

    @Override
    public double getSpeed() {
        return 10;
    }

    @Override
    protected void interpolate(double v) {
        imageView.setX(imageView.getX() - getSpeed());
        if (GameController.intersects(imageView,
                GameController.getCupHead().getImageView(),
                null) && GameController.getBlips() == 0) {
            try {
                GameController.getCupHead().getDamage(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
            GameController.deleteEgg(this);
            return;
        }
        if (imageView.getX() < 0)
            GameController.deleteEgg(this);
    }

    public ImageView getImageView() {
        return imageView;
    }
}
