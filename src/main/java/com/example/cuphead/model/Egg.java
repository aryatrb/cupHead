package com.example.cuphead.model;

import com.example.cuphead.ViewApplication;
import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;
import com.example.cuphead.realcontroller.GameController;
import com.example.cuphead.realcontroller.SettingController;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Egg extends Transition implements Armament {
    private final static Image IMAGE = new Image(Objects.requireNonNull(ViewApplication
            .class.getResource("assets/boss/egg.png")).toExternalForm());
    private static AudioClip audioClip = new AudioClip(
            Objects.requireNonNull(ViewApplication
                            .class.getResource("assets/music/eggSound.wav"))
                    .toExternalForm());

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
        isHorizontal = GameController.getBoss().getPhase() != 3;
        if (!isHorizontal) {
            imageView.setX(imageView.getX() +
                    GameController.getBoss().getImageView().getImage().getWidth() * 0.69);
            imageView.setY(imageView.getY() +
                    GameController.getBoss().getImageView().getImage().getHeight() * 0.3);
        } else if (GameController.getBoss().getPhase() == 1)
            imageView.setY(positionY + GameController.getBoss()
                    .getImageView().getImage().getHeight() * 0.45);
        else
            imageView.setY(positionY + GameController.getBoss()
                    .getImageView().getImage().getHeight() * 0.30);

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
        if (isHorizontal)
            imageView.setX(imageView.getX() - getSpeed());
        else
            imageView.setY(imageView.getY() - getSpeed());
        if (GameController.intersects(imageView,
                GameController.getCupHead().getImageView()) && GameController.getBlips() == 0) {
            try {
                GameController.getCupHead().getDamage(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
            GameController.deleteEgg(this);
            return;
        }
        if ((isHorizontal && imageView.getX() < 0) ||
                (!isHorizontal && imageView.getY() + imageView.getFitHeight() < 0))
            GameController.deleteEgg(this);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public static AudioClip getAudioClip() {
        return audioClip;
    }

    public static void setAudioClip(String name) {
        audioClip = new AudioClip(Objects.requireNonNull(ViewApplication.class.getResource(
                "src/main/resources/com/example/assets/music/" + name + ".wav")).toExternalForm());
        audioClip.setVolume(0.5);
    }
}
