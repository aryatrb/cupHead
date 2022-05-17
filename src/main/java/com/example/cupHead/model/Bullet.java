package com.example.cupHead.model;

import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import realController.GameController;
import realController.SettingController;

import java.io.IOException;

public class Bullet extends Transition implements Armament {
    private final ImageView imageView;

    public Bullet(int positionX, int positionY) {
        imageView = new ImageView();
        Image image = new Image("com/example/assets/bullet/bullet.png");
        imageView.setImage(image);
        imageView.setX(positionX);
        imageView.setY(positionY);
        imageView.setFitHeight(15);
        imageView.setFitWidth(72);
        this.setCycleDuration(Duration.millis(1000));
        this.setCycleCount(-1);
        this.play();
    }

    @Override
    protected void interpolate(double v) {
        imageView.setX(imageView.getX() + getSpeed());
        if (GameController.intersects(GameController.getBoss().getImageView(),
                imageView, GameController.getBoss())) {
            try {
                GameController.getBoss().getDamage(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
            GameController.deleteBullet(this);
            return;
        }
        for (MiniBoss miniBoss : GameController.getMiniBosses()) {
            if (GameController.intersects(miniBoss.getImageView(),
                    imageView, null)) {
                miniBoss.getDamage(this);
                GameController.deleteBullet(this);
                return;
            }
        }

        if (imageView.getX() > GameController.getWindowWidth())
            GameController.deleteBullet(this);
    }

    public double getSpeed() {
        return 8;
    }

    public ImageView getImageView() {
        return imageView;
    }

    @Override
    public double getCapableDamage() {
        return 1 * SettingController.getDifficulty()
                .getMakingDamageCoefficient();
    }

}
