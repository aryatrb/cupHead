package com.example.cupHead.model;

import javafx.scene.image.ImageView;

public class CupHead {
    private final ImageView imageView;
    private int health = 10;

    public CupHead() {
        imageView = new ImageView();
        imageView.setFitWidth(109);
        imageView.setFitHeight(91);
        imageView.setX(90);
        imageView.setY(67);
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

    public void getDamage(Armament armament) {
        health -= armament.getCapableDamage();
    }
}
