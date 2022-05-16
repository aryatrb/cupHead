package com.example.cupHead.model;

import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import realController.GameController;

public class Bullet extends Transition implements Armament {
    private int positionX;
    private final int positionY;
    private final ImageView imageView;

    public Bullet(int positionX, int positionY) {
        imageView = new ImageView();
        Image image = new Image("com/example/assets/bullet/bullet.png");
        imageView.setImage(image);
        this.positionX = positionX;
        this.positionY = positionY;
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
        positionX += getSpeed();
        imageView.setX(positionX);
        if (GameController.intersects(imageView, GameController.getBoss().getImageView())) {
            GameController.getBoss().getDamage(this);
            GameController.deleteBullet(this);
            return;
        }
        for (MiniBoss miniBoss : GameController.getMiniBosses()) {
            if(GameController.intersects(imageView,miniBoss.getImageView()))
            {
                miniBoss.getDamage(this);
                GameController.deleteBullet(this);
                return;
            }
        }

        if (positionX > GameController.getWindowWidth())
            GameController.deleteBullet(this);
    }

    public int getSpeed() {
        return 8;
    }

    public ImageView getImageView() {
        return imageView;
    }

    @Override
    public int getCapableDamage() {
        return 1;
    }

}
