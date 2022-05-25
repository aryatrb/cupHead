package com.example.cuphead.model;

import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import com.example.cuphead.realcontroller.GameController;
import com.example.cuphead.realcontroller.SettingController;

import java.io.IOException;

public class Bullet extends Transition implements Armament {
    private final static Image IMAGE = new Image("com/example/assets/bullet/bullet.png");
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
        if (intersectBoss() || intersectMiniBoss() ||
                imageView.getX() > GameController.getWindowWidth())
            GameController.deleteBullet(this);
    }

    @Override
    public double getCapableDamage() {
        return 1 * SettingController.getDifficulty()
                .getMakingDamageCoefficient();
    }

    private boolean intersectBoss() {
        if (GameController.intersectsTransParent(imageView, GameController.getBoss().getImageView())) {
            try {
                GameController.getBoss().getDamage(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    private boolean intersectMiniBoss() {
        for (MiniBoss miniBoss : GameController.getMiniBosses()) {
            if (GameController.intersects(miniBoss.getImageView(),
                    imageView)) {
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

}
