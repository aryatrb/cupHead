package com.example.cuphead.model;

import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import com.example.cuphead.realcontroller.GameController;
import com.example.cuphead.realcontroller.SettingController;

import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Boss extends Transition implements Armament, HealthyBeing {
    private static final Image[] FLY_IMAGES_PHASE_ONE = new Image[10];
    private static final Image[] FLY_IMAGES_PHASE_TWO = new Image[16];
    private static final Image[] FLY_IMAGES_PHASE_THREE = new Image[16];
    private static final Image[] SHOOT_IMAGES_PHASE_ONE = new Image[12];
    private static final Image[] SHOOT_IMAGES_PHASE_TWO = new Image[20];
    private static final Image[] SHOOT_IMAGES_PHASE_THREE = new Image[16];
    private static ImageView imageView = null;
    private final HealthBar healthBar;
    private double health = 1000;
    private double secondHealth = 750;
    private boolean isShooting;
    private int moveCycleX, moveCycleY;
    private int moveSGNX, moveSGNY;
    private int imageFlyNum;
    private int shootCycle;
    private int frame;
    private int phase;

    static {
        for (int i = 0; i < FLY_IMAGES_PHASE_ONE.length; i++)
            FLY_IMAGES_PHASE_ONE[i] = new Image("com/example/assets/boss/bossFly/" +
                    (i + 1) + ".png");
        for (int i = 0; i < SHOOT_IMAGES_PHASE_ONE.length; i++)
            SHOOT_IMAGES_PHASE_ONE[i] = new Image("com/example/assets/boss/bossShoot/" +
                    (i + 1) + ".png");
        for (int i = 0; i < FLY_IMAGES_PHASE_TWO.length; i++)
            FLY_IMAGES_PHASE_TWO[i] = new Image("com/example/assets/boss/phase2/fly/" +
                    (i + 1) + ".png");
        for (int i = 0; i < SHOOT_IMAGES_PHASE_TWO.length; i++)
            SHOOT_IMAGES_PHASE_TWO[i] = new Image("com/example/assets/boss/phase2/shoot/" +
                    (i + 1) + ".png");
        for (int i = 0; i < FLY_IMAGES_PHASE_THREE.length; i++)
            FLY_IMAGES_PHASE_THREE[i] = new Image("com/example/assets/boss/phase3/fly/" +
                    (i + 1) + ".png");
        for (int i = 0; i < SHOOT_IMAGES_PHASE_THREE.length; i++)
            SHOOT_IMAGES_PHASE_THREE[i] = new Image("com/example/assets/boss/phase3/shoot/" +
                    (i + 1) + ".png");
    }

    public Boss(ImageView imageView) {
        frame = 0;
        isShooting = false;
        imageFlyNum = 0;
        shootCycle = -1;
        moveCycleY = 0;
        Boss.imageView = imageView;
        Boss.imageView.setFitHeight(329);
        Boss.imageView.setFitWidth(517.1854);
        Boss.imageView.setX(851);
        Boss.imageView.setY(36);
        Boss.imageView.setImage(FLY_IMAGES_PHASE_ONE[imageFlyNum]);
//        imageView.preserveRatioProperty();
//        imageView.pickOnBoundsProperty();
        healthBar = new HealthBar(this, Boss.imageView, false);
        phase = 1;
        this.setCycleDuration(Duration.seconds(40));
        this.setCycleCount(-1);
        this.play();
    }

    @Override
    protected void interpolate(double v) {
        if (health <= 0 &&
                (secondHealth <= 0 || !SettingController.isDevilMode())) {
            bossIsDead();
            return;
        }
        frame++;
        if (frame % 10 != 0)
            return;
        Random random = new Random();
        if (bossShooting())
            return;
        if (phase == 1)
            changePhotoPerFrame(FLY_IMAGES_PHASE_ONE);
        if (phase == 2)
            changePhotoPerFrame(FLY_IMAGES_PHASE_TWO);
        if (phase == 3)
            changePhotoPerFrame(FLY_IMAGES_PHASE_THREE);
        crashWithCupHead();
        if (phase != 1)
            moveNAxis(random, true);
        if (phase != 3)
            moveNAxis(random, false);
        fixCoordinateFaults();
    }

    @Override
    public double getCapableDamage() {
        System.out.println(SettingController.getDifficulty()
                .getGettingDamagedCoefficient());
        return 1 * SettingController.getDifficulty()
                .getGettingDamagedCoefficient();
    }

    @Override
    public double getSpeed() {
        return 7;
    }

    @Override
    public double greenBarPercent() {
        return health / 1000;
    }

    @Override
    public double blueBarPercent() {
        return secondHealth / 750;
    }

    @Override
    public String getHealthDigit() {
        return health + "/" + 1000 + " | " +
                secondHealth + "/" + 750;
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
            double yDouble = GameController.getCupHead().getImageView().getY() +
                    GameController.getCupHead().getImageView().getFitHeight() / 2 -
                    imageView.getFitHeight() / 2 - imageView.getY();
            double xDouble = GameController.getCupHead().getImageView().getX() +
                    GameController.getCupHead().getImageView().getFitWidth() / 2 -
                    imageView.getFitWidth() * 0.69 - imageView.getX();
            if ((!isX && Math.abs(yDouble) > 10) ||
                    (phase == 3 && Math.abs(xDouble) > 8)) {
                if ((!isX && yDouble < 0) ||
                        (phase == 3 && xDouble < 0))
                    moveSGN--;
                else moveSGN++;
            }
            moveCycle = moveSGN * 4;
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

    private boolean bossShooting() {
        Image[] images = SHOOT_IMAGES_PHASE_ONE;
        if (phase == 2)
            images = SHOOT_IMAGES_PHASE_TWO;
        if (phase == 3)
            images = SHOOT_IMAGES_PHASE_THREE;
        if ((phase != 3 && !isShooting &&
                Math.abs(GameController.getCupHead().getImageView().getY() +
                        GameController.getCupHead().getImageView().getFitHeight() / 2
                        - imageView.getY() - imageView.getFitHeight() / 2) < 50) ||
                (phase == 3 && !isShooting &&
                        Math.abs(GameController.getCupHead().getImageView().getX() +
                                GameController.getCupHead().getImageView().getFitWidth() / 2
                                - imageView.getX() - imageView.getFitWidth() * 0.69) < 50)) {
            isShooting = true;
            shootCycle = -1;
            if (phase != 2)
                playSound();
        }
        if (isShooting) {
            shootCycle++;

            if (shootCycle == images.length)
                isShooting = false;
            else {
                if ((phase == 1 && shootCycle == 5) ||
                        (phase == 2 && shootCycle == 13) ||
                        (phase == 3 && shootCycle == 9)) {
                    if (phase == 2)
                        playSound();
                    GameController.shootEggs();
                }
                setImage(images[shootCycle]);
                return true;
            }
        }
        return false;
    }

    private void playSound() {
        if (Egg.getAudioClip().isPlaying())
            Egg.getAudioClip().stop();
        if (!SettingController.getMediaPlayer().isMute())
            Egg.getAudioClip().play();
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

    public void getDamage(Armament armament) throws IOException {
        double healthBefore = health;
        if (health > 0) {
            health -= armament.getCapableDamage();
            if (SettingController.isDevilMode() && health < 500 &&
                    healthBefore >= 500) {
                phase = 2;
                imageFlyNum = 0;
                shootCycle = 0;
                imageView.setImage(FLY_IMAGES_PHASE_TWO[0]);
                imageView.setFitHeight(200);
                imageView.setFitWidth(161.7021);
                Egg.setAudioClip("laser");
            }
            if (SettingController.isDevilMode() && health <= 0 && healthBefore > 0) {
                phase = 3;
                imageFlyNum = 0;
                shootCycle = 0;
                imageView.setImage(FLY_IMAGES_PHASE_THREE[0]);
                imageView.setFitHeight(300);
                imageView.setFitWidth(695.93);
                imageView.setX(GameController.getWindowWidth() * 2 / 5);
                imageView.setY(GameController.getWindowHeight() * 3 / 5);
                Egg.setAudioClip("eggSound");
            }
            if (health < 0)
                health = 0;
        } else
            secondHealth -= armament.getCapableDamage();
        if ((health <= 0) &&
                (secondHealth <= 0 || !SettingController.isDevilMode()))
            GameController.endTheGame(true);
    }

    private void bossIsDead() {
        try {
            GameController.endTheGame(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fixCoordinateFaults() {
        if (imageView.getY() < -60)
            imageView.setY(-60);
        if (imageView.getY() > GameController.getWindowHeight() - imageView.getFitHeight() / 2)
            imageView.setY(GameController.getWindowHeight() - imageView.getFitHeight() / 2);
        if ((imageView.getX() < GameController.getWindowWidth() * 3 / 5 && phase != 3))
            imageView.setX(GameController.getWindowWidth() * 3 / 5);
        if (imageView.getX() + imageView.getFitWidth() * 0.69 < 0 && phase == 3)
            imageView.setX(-imageView.getFitWidth() * 0.69);
        if (imageView.getX() > GameController.getWindowWidth() - imageView.getFitWidth() && phase != 3)
            imageView.setX(GameController.getWindowWidth() - imageView.getFitWidth());
        if (imageView.getX() + imageView.getFitWidth() * 0.69 > GameController.getWindowWidth())
            imageView.setX(GameController.getWindowWidth() - imageView.getFitWidth() * 0.69);
    }

    private void crashWithCupHead() {
        if (GameController.intersects(imageView,
                GameController.getCupHead().getImageView(), this)
                && GameController.getBlips() == 0) {
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
    }

    public double getHealth() {
        return health;
    }

    public int getPhase() {
        return phase;
    }

    public ImageView getImageView() {
        return imageView;
    }
}