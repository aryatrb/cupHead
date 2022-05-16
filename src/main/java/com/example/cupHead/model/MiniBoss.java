package com.example.cupHead.model;

import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import realController.GameController;

import java.util.ArrayList;
import java.util.Random;

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
        yellowPhoto[0] = new Image("com/example/assets/miniBoss/yellow/1.png");
        yellowPhoto[1] = new Image("com/example/assets/miniBoss/yellow/2.png");
        yellowPhoto[2] = new Image("com/example/assets/miniBoss/yellow/3.png");
        yellowPhoto[3] = new Image("com/example/assets/miniBoss/yellow/4.png");

        purplePhoto[0] = new Image("com/example/assets/miniBoss/purple/1.png");
        purplePhoto[1] = new Image("com/example/assets/miniBoss/purple/2.png");
        purplePhoto[2] = new Image("com/example/assets/miniBoss/purple/3.png");
        purplePhoto[3] = new Image("com/example/assets/miniBoss/purple/4.png");
    }

    public MiniBoss(double x,double y, boolean isYellow) {
        imageView = new ImageView();
        imageView.setFitHeight(getHeight());
        imageView.setFitWidth(getWidth());
        imageView.setX(x);
        imageView.setY(y);
        this.setCycleDuration(Duration.millis(1000));
        this.setCycleCount(-1);
        this.play();
        this.isYellow= isYellow;
    }

    @Override
    protected void interpolate(double v) {
        Image[] images = purplePhoto;
        if(isYellow)
            images = yellowPhoto;
        frame++;
        if(frame%10!=0)
            return;
        if(isGoingUp)
        {
            imageFlyNum++;
            if(imageFlyNum>=images.length)
            {
                isGoingUp=false;
                imageFlyNum-=2;
            }
        }
        else
        {
            imageFlyNum--;
            if(imageFlyNum<0)
            {
                isGoingUp=true;
                imageFlyNum+=2;
            }
        }
        imageView.setImage(images[imageFlyNum]);
        imageView.setX(imageView.getX() - getSpeed());
        if (GameController.intersects(imageView, GameController.getCupHead().getImageView())) {
            GameController.getCupHead().getDamage(this);
            GameController.deleteMiniBuss(this);
            return;
        }
        for (Bullet bullet : GameController.getBullets())
            if(GameController.intersects(imageView,bullet.getImageView()))
                getDamage(bullet);
        if (imageView.getX() <0 || health<=0)
            GameController.deleteMiniBuss(this);
    }

    @Override
    public int getCapableDamage() {
        return 1;
    }

    @Override
    public int getSpeed() {
        return 8;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public static double getWidth()
    {
        return 116.5;
    }

    public static double getHeight()
    {
        return 80;
    }

    public void getDamage(Armament armament)
    {
        health-=armament.getCapableDamage();
        if(health<=0)
            GameController.deleteMiniBuss(this);
    }

}
