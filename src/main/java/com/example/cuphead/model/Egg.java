package com.example.cuphead.model;

import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;
import com.example.cuphead.realcontroller.GameController;
import com.example.cuphead.realcontroller.SettingController;

import java.io.File;
import java.io.IOException;

public class Egg extends Transition implements Armament {
    private final static Image IMAGE = new Image("com/example/assets/boss/egg.png");
    private static AudioClip audioClip = new AudioClip(
            new File("src/main/resources/com/example/assets/music/eggSound.wav")
                    .toURI().toString());
    static {
        audioClip.setVolume(0.5);
    }
    private final ImageView imageView;
    private final boolean isHorizontal;

    public Egg(double positionX, double positionY) {
        imageView = new ImageView();
        imageView.setImage(IMAGE);
        imageView.setX(positionX);
        imageView.setY(positionY);
        imageView.setFitHeight(58);
        imageView.setFitWidth(50);
        isHorizontal= GameController.getBoss().getPhase() != 3;
        if(!isHorizontal)
            imageView.setX(imageView.getX() + GameController.getBoss().getImageView().getFitWidth() * 0.69);
        this.setCycleDuration(Duration.millis(1000));
        this.setCycleCount(-1);
        this.play();
    }

    @Override
    public double getCapableDamage() {
        return 1 * SettingController.getDifficulty()
                .getGettingDamagedCoefficient();
    }

    @Override
    public double getSpeed() {
        return 10;
    }

    @Override
    protected void interpolate(double v) {
        if(isHorizontal)
            imageView.setX(imageView.getX() - getSpeed());
        else
            imageView.setY(imageView.getY()-getSpeed());
        if (GameController.intersects(imageView,
                GameController.getCupHead().getImageView(),
                null) && GameController.getBlips() == 0) {
            try {
                GameController.getCupHead().getDamage(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
            GameController.deleteEgg(this);
            return;
        }
        if ((isHorizontal && imageView.getX() < 0) ||
                (!isHorizontal && imageView.getY() + imageView.getFitHeight()<0))
            GameController.deleteEgg(this);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public static AudioClip getAudioClip() {
        return audioClip;
    }

    public static void setAudioClip(String name)
    {
        audioClip = new AudioClip(
                new File("src/main/resources/com/example/assets/music/" + name + ".wav")
                        .toURI().toString());
        audioClip.setVolume(0.5);
    }
}
