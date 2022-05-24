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
        for (int i = 0; i < getSpeed(); i+=2) {
            imageView.setX(imageView.getX() + 2);
            if (intersectBoss())
                return;
        }
        if (intersectBoss())
            return;
        if (intersectMiniBoss())
        {
            GameController.deleteBullet(this);
            return;
        }
        if (imageView.getX() > GameController.getWindowWidth())
        {
            GameController.deleteBullet(this);
            return;
        }
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

}
