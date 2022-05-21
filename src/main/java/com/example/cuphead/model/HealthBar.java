package com.example.cuphead.model;

import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.util.Duration;
import com.example.cuphead.realcontroller.GameController;

public class HealthBar extends Transition {
    private static final Image[] IMAGES = new Image[3];
    private final ImageView format;
    private final ImageView greenBar;
    private final ImageView blueBar;
    private final HealthyBeing healthyBeing;
    private final ImageView healthyBeingImageView;
    private final Text text;
    private final boolean doChangePosition;


    static {
        IMAGES[0] = new Image("com/example/assets/healthbar/healthBar.png");
        IMAGES[1] = new Image("com/example/assets/healthbar/healthBarGreen.png");
        IMAGES[2] = new Image("com/example/assets/healthbar/healthBarBlue.png");
    }

    public HealthBar(HealthyBeing healthyBeing,
                     ImageView imageView,
                     boolean doChangePosition) {
        this.doChangePosition = doChangePosition;
        healthyBeingImageView = imageView;
        format = new ImageView();
        format.setImage(IMAGES[0]);
        greenBar = new ImageView();
        greenBar.setImage(IMAGES[1]);
        blueBar = new ImageView();
        blueBar.setImage(IMAGES[2]);
        this.healthyBeing = healthyBeing;
        GameController.getPane().getChildren().add(format);
        GameController.getPane().getChildren().add(greenBar);
        GameController.getPane().getChildren().add(blueBar);
        text = new Text();
        GameController.getPane().getChildren().add(text);
        if (!doChangePosition) {
            fixSizeFormat();
            format.setX(GameController.getWindowWidth() / 2 -
                    format.getFitWidth() / 2);
            format.setY(10);
            fixPositionsExceptFormat();
        }
        this.setCycleDuration(Duration.seconds(40));
        this.setCycleCount(-1);
        this.play();
    }

    @Override
    protected void interpolate(double v) {
        if (doChangePosition) {
            fixSizeFormat();
            format.setX(healthyBeingImageView.getX());
            format.setY(healthyBeingImageView.getY() -
                    format.getFitHeight());
        }
        greenBar.setFitWidth(getColorBarMaxWidth() *
                healthyBeing.greenBarPercent());
        greenBar.setFitHeight(format.getFitHeight());

        blueBar.setFitWidth(getColorBarMaxWidth() *
                healthyBeing.blueBarPercent());
        blueBar.setFitHeight(format.getFitHeight());

        greenBar.setOpacity(1);
        if (healthyBeing.greenBarPercent() == 0)
            greenBar.setOpacity(0);
        blueBar.setOpacity(1);
        if (healthyBeing.blueBarPercent() == 0)
            blueBar.setOpacity(0);
        text.setText(healthyBeing.getHealthDigit());
        if (doChangePosition)
            fixPositionsExceptFormat();
    }

    private void fixSizeFormat() {
        format.setFitWidth(healthyBeingImageView.getFitWidth());
        format.setFitHeight(healthyBeingImageView.getFitWidth() / 7.191489);
    }

    private void fixPositionsExceptFormat() {
        greenBar.setX(format.getX() + format.getFitWidth() * 0.017751);
        greenBar.setY(format.getY());
        blueBar.setX(format.getX() + format.getFitWidth() * 0.017751);
        blueBar.setY(format.getY());
        text.setX(format.getX() + format.getFitWidth() / 2 -
                text.getLayoutBounds().getWidth() / 2);
        text.setY(format.getY());
    }

    public double getColorBarMaxWidth() {
        return this.format.getFitWidth()
                - this.format.getFitWidth() * 0.017751;
    }

    public ImageView getFormat() {
        return format;
    }

    public ImageView getGreenBar() {
        return greenBar;
    }

    public ImageView getBlueBar() {
        return blueBar;
    }

    public Text getText() {
        return text;
    }
}
