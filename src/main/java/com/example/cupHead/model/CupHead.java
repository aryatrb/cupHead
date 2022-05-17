package com.example.cupHead.model;

import javafx.scene.image.ImageView;
import realController.GameController;
import realController.SettingController;

import java.io.IOException;

public class CupHead implements HealthyBeing {
    private final ImageView imageView;
    private double health;
    private final HealthBar healthBar;

    public CupHead(ImageView imageViewFX) {
        imageView = imageViewFX;
        imageView.setFitWidth(109);
        imageView.setFitHeight(91);
        imageView.setX(90);
        imageView.setY(67);
        health = SettingController.getDifficulty().getPrimitiveCupHeadHealth();
        healthBar = new HealthBar(this, imageView);
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

    public void getDamage(Armament armament) throws IOException {
        health -= armament.getCapableDamage();
        if (health <= 0)
            GameController.endTheGame(false);
    }

    @Override
    public double greenBarPercent() {
        return health / SettingController.getDifficulty().getPrimitiveCupHeadHealth();
    }

    @Override
    public double blueBarPercent() {
        return 1;
    }

    @Override
    public String getHealthDigit() {
        return health + "/" + SettingController.getDifficulty().getPrimitiveCupHeadHealth();
    }

    public double getHealth() {
        return health;
    }
}
