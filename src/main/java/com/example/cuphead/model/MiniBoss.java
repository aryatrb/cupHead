package com.example.cuphead.model;

import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import com.example.cuphead.realcontroller.GameController;
import com.example.cuphead.realcontroller.SettingController;

import java.io.IOException;

public class MiniBoss extends Transition implements Armament, HealthyBeing {
    private static final Image[] YELLOW_PHOTO = new Image[6];
    private static final Image[] PURPLE_PHOTO = new Image[6];
    private final ImageView imageView;
    private final boolean isYellow;
    private final HealthBar healthBar;
    private double health = 2;
    private int imageFlyNum;
    private int frame;

    static {
        for (int i = 0; i < YELLOW_PHOTO.length; i++)
            YELLOW_PHOTO[i] = new Image("com/example/assets/miniboss/yellow/"
                    + (i + 1) + ".png");

        for (int i = 0; i < PURPLE_PHOTO.length; i++)
            PURPLE_PHOTO[i] = new Image("com/example/assets/miniboss/purple/"
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
        healthBar = new HealthBar(this,
                imageView, true);
        if(isYellow)
            imageView.setImage(YELLOW_PHOTO[0]);
        else
            imageView.setImage(PURPLE_PHOTO[0]);
    }

    @Override
    public double greenBarPercent() {
        return health / 2;
    }

    @Override
    public double blueBarPercent() {
        return 0;
    }

    @Override
    public String getHealthDigit() {
        return health + "/" + 2;
    }

    @Override
    protected void interpolate(double v) {
        Image[] images = PURPLE_PHOTO;
        if (isYellow)
            images = YELLOW_PHOTO;
        frame++;
        if (frame % 10 != 0)
            return;
        imageFlyNum = (imageFlyNum + 1) % images.length;
        imageView.setImage(images[imageFlyNum]);
        imageView.setX(imageView.getX() - getSpeed());
        if (interactsCupHead())
            return;
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

    private boolean interactsCupHead() {
        if (GameController.intersects(imageView,
                GameController.getCupHead().getImageView(),
                null) && GameController.getBlips() == 0) {
            try {
                GameController.getCupHead().getDamage(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
            GameController.deleteMiniBuss(this);
            return true;
        }
        return false;
    }

    public static double getWidth() {
        return 116.5;
    }

    public static double getHeight() {
        return 80;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void getDamage(Armament armament) {
        health -= armament.getCapableDamage();
        if (health <= 0) {
            GameController.deleteMiniBuss(this);
            GameController.setScore(GameController.getScore() + 2);
        }
    }

    public HealthBar getHealthBar() {
        return healthBar;
    }
}
