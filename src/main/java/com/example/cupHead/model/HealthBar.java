package com.example.cupHead.model;

import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.util.Duration;
import realController.GameController;

public class HealthBar extends Transition {
    private final ImageView format;
    private final ImageView greenBar;
    private final ImageView blueBar;
    private final HealthyBeing healthyBeing;
    private final ImageView healthyBeingImageView;
    private final Text text;


    private static final Image[] images = new Image[3];

    static {
        images[0] = new Image("com/example/assets/healthBar/healthBar.png");
        images[1] = new Image("com/example/assets/healthBar/healthBarGreen.png");
        images[2] = new Image("com/example/assets/healthBar/healthBarBlue.png");
    }

    public HealthBar(HealthyBeing healthyBeing, ImageView imageView) {
        this.healthyBeingImageView = imageView;
        this.format = new ImageView();
        this.format.setImage(images[0]);
        this.format.setFitWidth(imageView.getFitWidth());
        this.format.setFitHeight(imageView.getFitWidth() / 7.191489);
        this.format.setX(imageView.getX());
        this.format.setY(imageView.getY() - format.getFitHeight());

        this.greenBar = new ImageView();
        this.greenBar.setImage(images[1]);
        this.greenBar.setX(this.format.getX() + this.format.getFitWidth() * 0.017751);
        this.greenBar.setY(this.format.getY());
        this.greenBar.setFitWidth(getColorBarMaxWidth());
        this.greenBar.setFitHeight(this.format.getFitHeight());

        this.blueBar = new ImageView();
        this.blueBar.setImage(images[2]);
        this.blueBar.setX(this.format.getX() + this.format.getFitWidth() * 0.017751);
        this.blueBar.setY(this.format.getY());
        this.blueBar.setFitWidth(getColorBarMaxWidth());
        this.blueBar.setFitHeight(this.format.getFitHeight());

        this.healthyBeing = healthyBeing;

        GameController.getPane().getChildren().add(format);
        GameController.getPane().getChildren().add(greenBar);
        GameController.getPane().getChildren().add(blueBar);
        text = new Text();
        text.setText(healthyBeing.getHealthDigit());
        text.setX(format.getX() + format.getFitWidth() / 2 - text.getLayoutBounds().getWidth() / 2);
        text.setY(format.getY() - 10);
        GameController.getPane().getChildren().add(text);
        this.setCycleDuration(Duration.seconds(40));
        this.setCycleCount(-1);
        this.play();
    }

    public double getColorBarMaxWidth() {
        return this.format.getFitWidth()
                - this.format.getFitWidth() * 0.017751;
    }

    @Override
    protected void interpolate(double v) {
        format.setFitWidth(healthyBeingImageView.getFitWidth());
        greenBar.setFitWidth(getColorBarMaxWidth() * healthyBeing.greenBarPercent());
        if (healthyBeing.greenBarPercent() == 0)
            greenBar.setOpacity(0);
        blueBar.setFitWidth(getColorBarMaxWidth() * healthyBeing.blueBarPercent());
        if (healthyBeing.blueBarPercent() == 0)
            blueBar.setOpacity(0);
        format.setX(healthyBeingImageView.getX());
        format.setY(healthyBeingImageView.getY() - format.getFitHeight());
        greenBar.setX(format.getX() + format.getFitWidth() * 0.017751);
        greenBar.setY(format.getY());
        blueBar.setX(format.getX() + format.getFitWidth() * 0.017751);
        blueBar.setY(format.getY());
        text.setText(healthyBeing.getHealthDigit());
        text.setX(format.getX() + format.getFitWidth() / 2 - text.getLayoutBounds().getWidth() / 2);
        text.setY(format.getY());
    }
}
