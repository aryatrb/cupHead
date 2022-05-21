package com.example.cuphead.model;

import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import com.example.cuphead.realcontroller.GameController;
import com.example.cuphead.realcontroller.SettingController;

import java.io.File;
import java.io.IOException;

public class Bullet extends Transition implements Armament {
    private final static Image IMAGE = new Image("com/example/assets/bullet/bullet.png");
    private final static Media media = new Media(
            new File("src/main/resources/com/example/assets/music/shootSound.wav")
                    .toURI().toString());
    private final ImageView imageView;

    public Bullet(int positionX, int positionY) {
        imageView = new ImageView();
        imageView.setImage(IMAGE);
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
        if (intersectBoss())
            return;
        if (intersectMiniBoss())
            return;
        if (imageView.getX() > GameController.getWindowWidth())
            GameController.deleteBullet(this);
    }

    @Override
    public double getCapableDamage() {
        return 1 * SettingController.getDifficulty()
                .getMakingDamageCoefficient();
    }

    private boolean intersectBoss() {
        if (GameController.intersects(GameController.getBoss().getImageView(),
                imageView, GameController.getBoss())) {
            try {
                GameController.getBoss().getDamage(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
            GameController.deleteBullet(this);
            return true;
        }
        return false;
    }

    private boolean intersectMiniBoss() {
        for (MiniBoss miniBoss : GameController.getMiniBosses()) {
            if (GameController.intersects(miniBoss.getImageView(),
                    imageView, null)) {
                miniBoss.getDamage(this);
                GameController.deleteBullet(this);
                return true;
            }
        }
        return false;
    }

    public double getSpeed() {
        return 8;
    }

    public ImageView getImageView() {
        return imageView;
    }


    public static Media getMedia() {
        return media;
    }
}
