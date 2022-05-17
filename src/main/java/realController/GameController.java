package realController;

import com.example.cupHead.ViewApplication;
import com.example.cupHead.model.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameController {
    private static CupHead cupHead;
    private static Pane pane;
    private static Boss boss;
    private static final Timer timerToBuildMiniBoss = new Timer();
    private static final ArrayList<Bullet> bullets = new ArrayList<>();
    private static final ArrayList<MiniBoss> miniBosses = new ArrayList<>();
    private static final ArrayList<Egg> eggs = new ArrayList<>();
    private static int score = 0;
    private static long start;
    private static long end = 0;
    private static int blips = 0;


    public static void changePosition(char direction) {
        int changeDirectionPerClick = 10;
        switch (direction) {
            case 'R' -> cupHead.getImageView().setX(cupHead.getImageView().getX()
                    + changeDirectionPerClick);
            case 'L' -> cupHead.getImageView().setX(cupHead.getImageView().getX()
                    - changeDirectionPerClick);
            case 'U' -> cupHead.getImageView().setY(cupHead.getImageView().getY()
                    - changeDirectionPerClick);
            case 'D' -> cupHead.getImageView().setY(cupHead.getImageView().getY()
                    + changeDirectionPerClick);
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

    public static void shootBullets() {
        Random random = new Random();
        Bullet bullet = new Bullet((int) cupHead.getImageView().getX() +
                cupHead.getWidth() / 2,
                (int) cupHead.getImageView().getY() +
                        random.nextInt(cupHead.getHeight()));
        bullets.add(bullet);
    }

    public static void newMiniBosses() {
        Random random = new Random();
        int randInt = random.nextInt(4000) %
                (getWindowHeight() - (int) MiniBoss.getHeight());
        boolean isYellow = random.nextInt(2) % 2 == 0;
        MiniBoss miniBoss1 = new MiniBoss(GameController.getWindowWidth(),
                randInt, isYellow);
        MiniBoss miniBoss2 = new MiniBoss(GameController.getWindowWidth() +
                MiniBoss.getWidth(), randInt, isYellow);
        MiniBoss miniBoss3 = new MiniBoss(GameController.getWindowWidth() +
                MiniBoss.getWidth() * 2, randInt, isYellow);
        miniBosses.add(miniBoss1);
        miniBosses.add(miniBoss2);
        miniBosses.add(miniBoss3);
        pane.getChildren().add(miniBoss1.getImageView());
        pane.getChildren().add(miniBoss2.getImageView());
        pane.getChildren().add(miniBoss3.getImageView());
    }


    public static void blip(Timer timer) {
        blips++;
        if (blips == 3)
            timer.cancel();
        cupHead.getImageView().setOpacity(0);
        Timer timer2 = new Timer();
        timer2.schedule(new TimerTask() {

            @Override
            public void run() {
                GameController.unBlip(timer2);
            }
        }, 200, 400);
    }

    public static void unBlip(Timer timer) {
        if (blips == 3) {
            timer.cancel();
            blips = 0;
        }
        cupHead.getImageView().setOpacity(1);
    }

    public static void shootEggs() {
        Egg egg = new Egg(boss.getImageView().getX(),
                boss.getImageView().getY() +
                        boss.getImageView().getFitHeight() / 3);
        eggs.add(egg);
        pane.getChildren().add(egg.getImageView());
    }

    public static ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public static int getWindowHeight() {
        return 800;
    }

    public static double getWindowWidth() {
        return 1280;
    }

    public static void deleteBullet(Bullet bullet) {
        bullet.getImageView().setImage(null);
        bullet.stop();
        bullets.remove(bullet);
    }

    public static void deleteMiniBuss(MiniBoss miniBoss) {
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

    public static boolean intersects(ImageView imageView1,
                                     ImageView imageView2,
                                     Armament armament1) {
        double width1 = imageView1.getFitWidth();
        double width2 = imageView2.getFitWidth();
        double x1 = imageView1.getX();
        double x2 = imageView2.getX();
        if (armament1 instanceof Boss) {
            if (boss.getPhase() == 1)
                width1 *= 0.7811;
            else if (boss.getPhase() == 2)
                width1 *= 0.8092;
            x1 += imageView1.getFitWidth() - width1;
        }
        double middleX1 = x1 + width1 / 2;
        double middleX2 = x2 + width2 / 2;
        if (Math.abs(middleX1 - middleX2) > (width1 + width2) / 2)
            return false;

        double height1 = imageView1.getFitHeight();
        double heigth2 = imageView2.getFitHeight();
        double y1 = imageView1.getY();
        double y2 = imageView2.getY();
        if (armament1 instanceof Boss) {
            if (boss.getPhase() == 1)
                height1 *= 0.8844;
            if (boss.getPhase() == 2)
                height1 *= 0.8082;
        }
        double middleY1 = y1 + height1 / 2;
        double middleY2 = y2 + heigth2 / 2;
        if (Math.abs(middleY1 - middleY2) > (height1 + heigth2) / 2)
            return false;
        return true;
    }

    public static void deleteEgg(Egg egg) {
        egg.getImageView().setImage(null);
        egg.stop();
        eggs.remove(egg);
    }

    public static void setPane(Pane pane) {
        GameController.pane = pane;
    }

    public static Timer getTimerToBuildMiniBoss() {
        return timerToBuildMiniBoss;
    }

    public static ArrayList<MiniBoss> getMiniBosses() {
        return miniBosses;
    }

    public static Pane getPane() {
        return pane;
    }

    public static int getScore() {
        return score;
    }

    public static void setScore(int score) {
        GameController.score = score;
    }

    public static void endTheGame(boolean didWin) throws IOException {
        if (end != 0)
            return;
        if (didWin)
            score += cupHead.getHealth() * 100 + 10000;
        else
            score += (1000 - boss.getHealth()) * 0.6;
        end = System.currentTimeMillis();
        if (LoginController.getLoggedUser().getScore() < score) {
            LoginController.getLoggedUser().setScore(score);
            LoginController.getLoggedUser().setTime((int) end / 1000);
        }
        boss.stop();
        for (Bullet bullet : bullets)
            bullet.stop();
        for (Egg egg : eggs)
            egg.stop();
        for (MiniBoss miniBoss : miniBosses)
            miniBoss.stop();
        ViewApplication.sceneChanger("gameEndMenu.fxml");
    }

    public static long getStart() {
        return start;
    }

    public static void setStart(long start) {
        GameController.start = start;
    }

    public static long getEnd() {
        return end;
    }

    public static int getBlips() {
        return blips;
    }
}
