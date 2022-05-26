package com.example.cuphead.model;

import com.example.cuphead.ViewApplication;
import com.example.cuphead.realcontroller.GameController;
import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.Objects;

public class HitDust extends Transition {
    private static final Image[] IMAGES = new Image[12];
    private final ImageView imageView = new ImageView();
    private int imageNumber = 0;
    private int rate = 0;

    static {
        for (int i = 0; i < IMAGES.length; i++)
            IMAGES[i] = new Image(Objects.requireNonNull(ViewApplication
                    .class.getResource("assets/boss/hitdust/" +
                    (i + 1) + ".png")).toExternalForm());
    }

    public HitDust(ImageView bulletView) {
        imageView.setImage(IMAGES[0]);
        imageView.setX(bulletView.getX() + bulletView.getFitWidth());
        imageView.setY(bulletView.getY());
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
        GameController.getPane().getChildren().add(imageView);
        this.setCycleDuration(Duration.seconds(40));
        this.setCycleCount(-1);
        this.play();

    }

    @Override
    protected void interpolate(double v) {
        rate++;
        if (rate < 7)
            return;
        imageView.setImage(IMAGES[imageNumber]);
        imageNumber++;
        if (imageNumber > IMAGES.length - 1)
            GameController.deleteDust(this);
    }

    public ImageView getImageView() {
        return imageView;
    }
}
