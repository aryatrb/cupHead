package com.example.cupHead.model;

import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import realController.GameController;

import java.util.Random;

public class Boss extends Transition implements Armament{
    private final ImageView imageView;
    private int imageFlyNum;
    private boolean isGoingUp;
    private int frame;
    private int health = 1000;
    private int moveCycle;
    private int moveSGN;
    private boolean isShooting;
    private int shootCycle;
    private static final Image[] flyImages = new Image[6];
    private static final Image[] shootImages = new Image[12];
    static {
        flyImages[0] = new Image("com/example/assets/boss/bossFly/1.png");
        flyImages[1] = new Image("com/example/assets/boss/bossFly/2.png");
        flyImages[2] = new Image("com/example/assets/boss/bossFly/3.png");
        flyImages[3] = new Image("com/example/assets/boss/bossFly/4.png");
        flyImages[4] = new Image("com/example/assets/boss/bossFly/5.png");
        flyImages[5] = new Image("com/example/assets/boss/bossFly/6.png");

        shootImages[0] = new Image("com/example/assets/boss/bossShoot/1.png");
        shootImages[1] = new Image("com/example/assets/boss/bossShoot/2.png");
        shootImages[2] = new Image("com/example/assets/boss/bossShoot/3.png");
        shootImages[3] = new Image("com/example/assets/boss/bossShoot/4.png");
        shootImages[4] = new Image("com/example/assets/boss/bossShoot/5.png");
        shootImages[5] = new Image("com/example/assets/boss/bossShoot/6.png");
        shootImages[6] = new Image("com/example/assets/boss/bossShoot/7.png");
        shootImages[7] = new Image("com/example/assets/boss/bossShoot/8.png");
        shootImages[8] = new Image("com/example/assets/boss/bossShoot/9.png");
        shootImages[9] = new Image("com/example/assets/boss/bossShoot/10.png");
        shootImages[10] = new Image("com/example/assets/boss/bossShoot/11.png");
        shootImages[11] = new Image("com/example/assets/boss/bossShoot/12.png");
    }
    public Boss()
    {
        frame=0;
        isGoingUp=true;
        isShooting = false;
        imageFlyNum=0;
        shootCycle=-1;
        moveCycle=0;
        imageView = new ImageView();
        imageView.setFitHeight(329);
        imageView.setFitWidth(404);
        imageView.setX(851);
        imageView.setY(36);
        imageView.setImage(flyImages[imageFlyNum]);
        imageView.preserveRatioProperty();
        imageView.pickOnBoundsProperty();
        this.setCycleDuration(Duration.seconds(40));
        this.setCycleCount(-1);
        this.play();
    }

    @Override
    protected void interpolate(double v) {
        frame++;
        if(frame%10!=0)
            return;
        Random random = new Random();
        if(!isShooting && random.nextInt(40000)%80==0)
        {
            isShooting=true;
            shootCycle=-1;
            GameController.bossStartShooting();
        }
        if(isShooting)
        {
            shootCycle++;

            if(shootCycle==12)
            {
                isShooting=false;
                GameController.bossEndShooting();
            }
            else {
                if(shootCycle==5)
                    GameController.shootEggs();
                imageView.setImage(shootImages[shootCycle]);
                return;
            }
        }
        if(isGoingUp)
        {
            imageFlyNum++;
            if(imageFlyNum>=flyImages.length)
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
        imageView.setImage(flyImages[imageFlyNum]);
        if (GameController.intersects(imageView, GameController.getCupHead().getImageView()))
            GameController.getCupHead().getDamage(this);
        if(moveCycle==0)
        {
            moveSGN = random.nextInt(49)%3 -1;
            moveCycle = moveSGN*10;
        }
        else{
            imageView.setY(imageView.getY() + moveSGN*getSpeed());
            if(moveCycle<0)
                moveCycle++;
            else
                moveCycle--;
        }
        if (imageView.getY() < 0)
           imageView.setY(0);
        if (imageView.getY() > 775 - imageView.getFitHeight())
            imageView.setY(775 - imageView.getFitHeight());
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void getDamage(Armament armament)
    {
        health -=armament.getCapableDamage();
//        if(health<0)

    }

    public void setShootCycle(int shootCycle) {
        this.shootCycle = shootCycle;
    }

    @Override
    public int getCapableDamage() {
        return 1;
    }

    @Override
    public int getSpeed() {
        return 5;
    }
}
