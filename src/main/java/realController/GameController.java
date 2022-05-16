package realController;

import com.example.cupHead.model.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;

public class GameController {
    private static CupHead cupHead;
    private static Pane pane;
    private static Boss boss;
    private static Timer timerToBuildMiniBoss= new Timer();
    private static final ArrayList<Bullet> bullets = new ArrayList<>();
    private static final ArrayList<MiniBoss> miniBosses = new ArrayList<>();
    private static final ArrayList<Egg> eggs = new ArrayList<>();
    private int score=0;

    public static void changePosition(char direction) {
        int changeDirectionPerClick = 10;
        switch (direction) {
            case 'R':
                cupHead.getImageView().setX(cupHead.getImageView().getX() + changeDirectionPerClick);
                break;
            case 'L':
                cupHead.getImageView().setX(cupHead.getImageView().getX() - changeDirectionPerClick);
                break;
            case 'U':
                cupHead.getImageView().setY(cupHead.getImageView().getY() - changeDirectionPerClick);
                break;
            case 'D':
                cupHead.getImageView().setY(cupHead.getImageView().getY() + changeDirectionPerClick);
                break;
        }
        if (cupHead.getImageView().getX() < 0)
            cupHead.getImageView().setX(0);
        if (cupHead.getImageView().getX() > 1280 - cupHead.getWidth())
            cupHead.getImageView().setX(1280 - cupHead.getWidth());
        if (cupHead.getImageView().getY() < 0)
            cupHead.getImageView().setY(0);
        if (cupHead.getImageView().getY() > 775 - cupHead.getHeight())
            cupHead.getImageView().setY(775 - cupHead.getHeight());
    }

    public static CupHead getCupHead() {
        return cupHead;
    }

    public static void setCupHead(CupHead cupHead) {
        GameController.cupHead = cupHead;
    }

    public static void shootBullets()
    {
        Random random = new Random();
        Bullet bullet = new Bullet((int)cupHead.getImageView().getX() + cupHead.getWidth()/2,
                (int)cupHead.getImageView().getY()+random.nextInt(cupHead.getHeight()));
        bullets.add(bullet);
    }

    public static void newMiniBosses()
    {
        Random random = new Random();
        int randInt = random.nextInt(4000)%(getWindowHeight() - (int)MiniBoss.getHeight());
        boolean isYellow = random.nextInt(2) % 2 == 0;
        MiniBoss miniBoss1 = new MiniBoss(GameController.getWindowWidth(),randInt,isYellow);
        MiniBoss miniBoss2 = new MiniBoss(GameController.getWindowWidth() + MiniBoss.getWidth(),randInt,isYellow);
        MiniBoss miniBoss3 = new MiniBoss(GameController.getWindowWidth() + MiniBoss.getWidth()*2,randInt,isYellow);
        miniBosses.add(miniBoss1);
        miniBosses.add(miniBoss2);
        miniBosses.add(miniBoss3);
        pane.getChildren().add(miniBoss1.getImageView());
        pane.getChildren().add(miniBoss2.getImageView());
        pane.getChildren().add(miniBoss3.getImageView());
    }

    public static void shootEggs()
    {
        Egg egg = new Egg(boss.getImageView().getX(),boss.getImageView().getY()+boss.getImageView().getFitHeight()/3);
        eggs.add(egg);
        pane.getChildren().add(egg.getImageView());
    }

    public static ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public static int getWindowHeight() {
        return 800;
    }

    public static int getWindowWidth() {
        return 1280;
    }

    public static void deleteBullet(Bullet bullet)
    {
        bullet.getImageView().setImage(null);
        bullet.stop();
        bullets.remove(bullet);
    }

    public static void deleteMiniBuss(MiniBoss miniBoss)
    {
        miniBoss.getImageView().setImage(null);
        miniBoss.stop();
        miniBosses.remove(miniBoss);
    }

    public static Boss getBoss() {
        return boss;
    }

    public static void setBoss(Boss boss) {
        GameController.boss = boss;
        boss.play();
    }

    public static boolean intersects(ImageView imageView1, ImageView imageView2)
    {
        double middleX1 = imageView1.getX() + imageView1.getFitWidth()/2;
        double middleX2 = imageView2.getX() + imageView2.getFitWidth()/2;
        if(Math.abs(middleX1-middleX2)>(imageView1.getFitWidth()+imageView2.getFitWidth())/2)
            return false;
        double middleY1 = imageView1.getY() + imageView1.getFitHeight()/2;
        double middleY2 = imageView2.getY() + imageView2.getFitHeight()/2;
        if(Math.abs(middleY1-middleY2)>(imageView1.getFitHeight()+imageView2.getFitHeight())/2)
            return false;
        return true;
    }

    public static void deleteEgg(Egg egg)
    {
        egg.getImageView().setImage(null);
        egg.stop();
        eggs.remove(egg);
    }

    public static ArrayList<Egg> getEggs() {
        return eggs;
    }

    public static void setPane(Pane pane) {
        GameController.pane = pane;
    }

    public static void bossStartShooting() {
        boss.getImageView().setX(boss.getImageView().getX() - 92.5);
        boss.getImageView().setFitWidth(boss.getImageView().getFitWidth() + 92.5);
    }

    public static void bossEndShooting() {
        boss.getImageView().setX(boss.getImageView().getX() + 92.5);
        boss.getImageView().setFitWidth(boss.getImageView().getFitWidth() - 92.5);
        GameController.getBoss().setShootCycle(-1);
    }


    public static Timer getTimerToBuildMiniBoss() {
        return timerToBuildMiniBoss;
    }

    public static void setTimerToBuildMiniBoss(Timer timerToBuildMiniBoss) {
        GameController.timerToBuildMiniBoss = timerToBuildMiniBoss;
    }

    public static ArrayList<MiniBoss> getMiniBosses() {
        return miniBosses;
    }
}
