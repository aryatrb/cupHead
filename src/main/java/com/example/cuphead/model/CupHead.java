package com.example.cuphead.model;

import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import com.example.cuphead.realcontroller.GameController;
import com.example.cuphead.realcontroller.SettingController;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class CupHead extends Transition implements HealthyBeing {
    private static final Image[] FLY_IMAGES_UP = new Image[4];
    private static final Image[] FLY_IMAGES_STRAIGHT = new Image[4];
    private static final Image[] FLY_IMAGES_DOWN = new Image[4];
    private static final Image[] FLY_TRANSITION_UP = new Image[11];
    private static final Image[] FLY_TRANSITION_DOWN = new Image[11];
    private ImageView imageView;
    private double health;
    private int moving = 0; //-1 down | 0 straight | 1 up
    private boolean isTurning = false;
    private boolean isStopping;
    private int movingCycle = 0;
    private int rate = 0;

    static {
        for (int i = 0; i < FLY_IMAGES_UP.length; i++)
            FLY_IMAGES_UP[i] = new Image("com/example/assets/cuphead/moving/up/" +
                    (i + 1) + ".png");
        for (int i = 0; i < FLY_IMAGES_STRAIGHT.length; i++)
            FLY_IMAGES_STRAIGHT[i] = new Image("com/example/assets/cuphead/moving/straight/" +
                    (i + 1) + ".png");
        for (int i = 0; i < FLY_IMAGES_DOWN.length; i++)
            FLY_IMAGES_DOWN[i] = new Image("com/example/assets/cuphead/moving/down/" +
                    (i + 1) + ".png");
        for (int i = 0; i < FLY_TRANSITION_UP.length; i++)
            FLY_TRANSITION_UP[i] = new Image("com/example/assets/cuphead/moving/turning/up/" +
                    (i + 1) + ".png");
        for (int i = 0; i < FLY_TRANSITION_DOWN.length; i++)
            FLY_TRANSITION_DOWN[i] = new Image("com/example/assets/cuphead/moving/turning/down/" +
                    (i + 1) + ".png");
    }

    public CupHead() {
        imageView = new ImageView();
        imageView.setImage(FLY_IMAGES_STRAIGHT[0]);
        imageView.setY(25);
        health = SettingController.getDifficulty().getPrimitiveCupHeadHealth();
        new HealthBar(this, imageView, true);
        GameController.getPane().getChildren().add(imageView);
        this.setCycleDuration(Duration.seconds(40));
        this.setCycleCount(-1);
        this.play();
    }

    @Override
    public double greenBarPercent() {
        return health /
                SettingController.getDifficulty().getPrimitiveCupHeadHealth();
    }

    @Override
    public double blueBarPercent() {
        return 1;
    }

    @Override
    public String getHealthDigit() {
        return health + "/" + SettingController.getDifficulty().getPrimitiveCupHeadHealth();
    }

    public void getDamage(Armament armament) throws IOException {
        if (GameController.getBlips() != 0)
            return;
        health -= armament.getCapableDamage();
        if (health <= 0)
            GameController.endTheGame(false);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                GameController.blip(timer);
            }
        }, 200, 400);
    }

    public int getWidth() {
        return 109;
    }

    public int getHeight() {
        return 91;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public double getHealth() {
        return health;
    }

    @Override
    protected void interpolate(double v) {
        rate++;
        if (rate < 4)
            return;
        rate = 0;
        if (isTurning || isStopping) {
            if (moving == 1) imageView.setImage(FLY_TRANSITION_UP[movingCycle]);
            else imageView.setImage(FLY_TRANSITION_DOWN[movingCycle]);
            if (isTurning) movingCycle++;
            else movingCycle--;
            if (movingCycle > 10) {
                isTurning = false;
                movingCycle = 0;
                return;
            }
            if (movingCycle < 0) {
                isStopping = false;
                movingCycle = 0;
                moving = 0;
                return;
            }
            return;
        }
        if (moving == 1)
            imageView.setImage(FLY_IMAGES_UP[movingCycle]);
        else if (moving == 0)
            imageView.setImage(FLY_IMAGES_STRAIGHT[movingCycle]);
        else
            imageView.setImage(FLY_IMAGES_DOWN[movingCycle]);
        movingCycle = (movingCycle + 1) % 4;
    }

    public void setIsTurning(boolean isTurning) {
        this.isTurning = isTurning;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public void setStopping(boolean stopping) {
        this.isStopping = stopping;
    }

    public void setMovingCycle(int movingCycle) {
        this.movingCycle = movingCycle;
    }

    public void setMoving(int moving) {
        this.moving = moving;
    }

    public boolean isTurning() {
        return isTurning;
    }
}
