package com.example.cupHead.model;

import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import realController.GameController;
import realController.SettingController;

import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Boss extends Transition implements Armament, HealthyBeing {
    private final ImageView imageView;
    private int imageFlyNum;
    private int frame;
    private double health = 1000;
    private int moveCycleX, moveCycleY;
    private int moveSGNX, moveSGNY;
    private boolean isShooting;
    private int shootCycle;
    private static final Image[] flyImagesPhaseOne = new Image[10];
    private static final Image[] flyImagesPhaseTwo = new Image[16];
    private static final Image[] shootImagesPhaseOne = new Image[12];
    private static final Image[] shootImagesPhaseTwo = new Image[20];
    private int phase;
    private final HealthBar healthBar;

    static {
        for (int i = 0; i < flyImagesPhaseOne.length; i++)
            flyImagesPhaseOne[i] = new Image("com/example/assets/boss/bossFly/" + (i + 1) + ".png");
        for (int i = 0; i < shootImagesPhaseOne.length; i++)
            shootImagesPhaseOne[i] = new Image("com/example/assets/boss/bossShoot/" + (i + 1) + ".png");
        for (int i = 0; i < flyImagesPhaseTwo.length; i++)
            flyImagesPhaseTwo[i] = new Image("com/example/assets/boss/phase2/fly/" + (i + 1) + ".png");
        for (int i = 0; i < shootImagesPhaseTwo.length; i++)
            shootImagesPhaseTwo[i] = new Image("com/example/assets/boss/phase2/shoot/" + (i + 1) + ".png");
    }

    public Boss() {
        frame = 0;
        isShooting = false;
        imageFlyNum = 0;
        shootCycle = -1;
        moveCycleY = 0;
        imageView = new ImageView();
        imageView.setFitHeight(329);
        imageView.setFitWidth(517.1854);
        imageView.setX(851);
        imageView.setY(36);
        imageView.setImage(flyImagesPhaseOne[imageFlyNum]);
        imageView.preserveRatioProperty();
        imageView.pickOnBoundsProperty();
        healthBar = new HealthBar(this, imageView);
        phase = 1;
        this.setCycleDuration(Duration.seconds(40));
        this.setCycleCount(-1);
        this.play();
    }

    @Override
    protected void interpolate(double v) {
        if (health <= 0) {
            try {
                GameController.endTheGame(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        frame++;
        if (frame % 10 != 0)
            return;
        Random random = new Random();
        if (bossShooting(random))
            return;
        if (phase == 1)
            changePhotoPerFrame(flyImagesPhaseOne);
        if (phase == 2)
            changePhotoPerFrame(flyImagesPhaseTwo);
        if (GameController.intersects(imageView,
                GameController.getCupHead().getImageView(), this) && GameController.getBlips() == 0) {
            try {
                GameController.getCupHead().getDamage(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
            GameController.getCupHead().getImageView().setX(10);
            GameController.getCupHead().getImageView().setY(10);
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    GameController.blip(timer);
                }
            }, 200, 400);
        }
        if (phase == 2)
            moveNAxis(random, true);
        moveNAxis(random, false);
        if (imageView.getY() < 25)
            imageView.setY(25);
        if (imageView.getY() > 775 - imageView.getFitHeight())
            imageView.setY(775 - imageView.getFitHeight());
        if (imageView.getX() < GameController.getWindowWidth())
            imageView.setX(GameController.getWindowWidth());
        if (imageView.getX() > 1280 - imageView.getFitWidth())
            imageView.setX(1280 - imageView.getFitWidth());

    }

    public void moveNAxis(Random random, boolean isX) {
        int moveCycle = moveCycleY;
        int moveSGN = moveSGNY;
        if (isX) {
            moveCycle = moveCycleX;
            moveSGN = moveSGNX;
        }
        if (moveCycle == 0) {
            moveSGN = random.nextInt(49) % 3 - 1;
            moveCycle = moveSGN * 10;
        } else {
            if (isX)
                imageView.setX(imageView.getX() + moveSGN * getSpeed());
            else
                imageView.setY(imageView.getY() + moveSGN * getSpeed());
            if (moveCycle < 0)
                moveCycle++;
            else
                moveCycle--;
        }
        if (isX) {
            moveCycleX = moveCycle;
            moveSGNX = moveSGN;
        } else {
            moveCycleY = moveCycle;
            moveSGNY = moveSGN;
        }

    }

    private boolean bossShooting(Random random) {
        Image[] images = shootImagesPhaseOne;
        if (phase == 2)
            images = shootImagesPhaseTwo;
        if (!isShooting && random.nextInt(40000) % 60 == 0) {
            isShooting = true;
            shootCycle = -1;
        }
        if (isShooting) {
            shootCycle++;

            if (shootCycle == images.length)
                isShooting = false;
            else {
                if ((phase == 1 && shootCycle == 5) ||
                        (phase == 2 && shootCycle == 13))
                    GameController.shootEggs();
                setImage(images[shootCycle]);

                return true;
            }
        }
        return false;
    }

    private void setImage(Image image) {
        if (phase == 2) {
            double balancedWidth = image.getWidth() / image.getHeight() * 200;
            double difference = balancedWidth - imageView.getFitWidth();
            imageView.setX(imageView.getX() - difference);
            imageView.setFitWidth(imageView.getFitWidth() + difference);
        }

        imageView.setImage(image);

    }

    private void changePhotoPerFrame(Image[] images) {
        imageFlyNum = (imageFlyNum + 1) % images.length;
        setImage(images[imageFlyNum]);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void getDamage(Armament armament) throws IOException {
        double healthBefore = health;
        health -= armament.getCapableDamage();
        if (SettingController.isDevilMode() && health < 500 && healthBefore >= 500) {
            phase = 2;
            imageFlyNum = 0;
            shootCycle = 0;
            imageView.setImage(flyImagesPhaseTwo[0]);
            imageView.setFitHeight(200);
            imageView.setFitWidth(161.7021);
        }
        if (health <= 0)
            GameController.endTheGame(true);
    }

    @Override
    public double getCapableDamage() {
        return 1 * SettingController.getDifficulty().getGettingDamagedCoefficient();
    }

    @Override
    public double getSpeed() {
        return 5;
    }

    @Override
    public double greenBarPercent() {
        return health / 1000;
    }

    @Override
    public double blueBarPercent() {
        return 1;
    }

    @Override
    public String getHealthDigit() {
        return health + "/1000";
    }

    public double getHealth() {
        return health;
    }

    public int getPhase() {
        return phase;
    }
}
