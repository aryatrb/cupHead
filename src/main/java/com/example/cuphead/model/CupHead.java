package com.example.cuphead.model;

import javafx.scene.image.ImageView;
import com.example.cuphead.realcontroller.GameController;
import com.example.cuphead.realcontroller.SettingController;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class CupHead implements HealthyBeing {
    private final ImageView imageView;
    private double health;
    private final HealthBar healthBar;

    public CupHead(ImageView imageViewFX) {
        imageView = imageViewFX;
        imageView.setFitWidth(109);
        imageView.setFitHeight(91);
        imageView.setX(90);
        imageView.setY(100);
        health = SettingController.getDifficulty().getPrimitiveCupHeadHealth();
        healthBar = new HealthBar(this, imageView, true);
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
        if(GameController.getBlips()!=0)
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

}
