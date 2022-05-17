package com.example.cupHead.model;

import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import realController.GameController;
import realController.SettingController;

import java.io.IOException;

public class MiniBoss extends Transition implements Armament {
    private int frame;
    private final ImageView imageView;
    private int health = 2;
    private final boolean isYellow;
    private int imageFlyNum;
    private boolean isGoingUp;
    private static final Image[] yellowPhoto = new Image[4];
    private static final Image[] purplePhoto = new Image[4];

    static {
        for (int i = 0; i < yellowPhoto.length; i++)
            yellowPhoto[i] = new Image("com/example/assets/miniBoss/yellow/"
                    + (i + 1) + ".png");

        for (int i = 0; i < purplePhoto.length; i++)
            purplePhoto[i] = new Image("com/example/assets/miniBoss/purple/"
                    + (i + 1) + ".png");
    }

    public MiniBoss(double x, double y, boolean isYellow) {
        imageView = new ImageView();
        imageView.setFitHeight(getHeight());
        imageView.setFitWidth(getWidth());
        imageView.setX(x);
        imageView.setY(y);
        this.setCycleDuration(Duration.millis(1000));
        this.setCycleCount(-1);
        this.play();
        this.isYellow = isYellow;
    }

    @Override
    protected void interpolate(double v) {
        Image[] images = purplePhoto;
        if (isYellow)
            images = yellowPhoto;
        frame++;
        if (frame % 10 != 0)
            return;
        if (isGoingUp) {
            imageFlyNum++;
            if (imageFlyNum >= images.length) {
                isGoingUp = false;
                imageFlyNum -= 2;
            }
        } else {
            imageFlyNum--;
            if (imageFlyNum < 0) {
                isGoingUp = true;
                imageFlyNum += 2;
            }
        }
        imageView.setImage(images[imageFlyNum]);
        imageView.setX(imageView.getX() - getSpeed());
        if (GameController.intersects(imageView,
                GameController.getCupHead().getImageView(),
                null) && GameController.getBlips() == 0) {
            try {
                GameController.getCupHead().getDamage(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
            GameController.deleteMiniBuss(this);
            return;
        }
        for (Bullet bullet : GameController.getBullets())
            if (GameController.intersects(imageView, bullet.getImageView(), null))
                getDamage(bullet);
        if (imageView.getX() + imageView.getFitWidth() < 0 || health <= 0)
            GameController.deleteMiniBuss(this);
    }

    @Override
    public double getCapableDamage() {
        return 1 * SettingController.getDifficulty()
                .getGettingDamagedCoefficient();
    }

    @Override
    public double getSpeed() {
        return 8;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public static double getWidth() {
        return 116.5;
    }

    public static double getHeight() {
        return 80;
    }

    public void getDamage(Armament armament) {
        health -= armament.getCapableDamage();
        if (health <= 0) {
            GameController.deleteMiniBuss(this);
            GameController.setScore(GameController.getScore() + 2);
        }
    }

}
