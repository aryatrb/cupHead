package com.example.cuphead.realcontroller;

import com.example.cuphead.ViewApplication;
import com.example.cuphead.fxcontroller.GameMenuControllerFX;
import com.example.cuphead.model.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameController {
    private static final ArrayList<Bullet> BULLETS = new ArrayList<>();
    private static final ArrayList<MiniBoss> miniBosses = new ArrayList<>();
    private static final ArrayList<Egg> EGGS = new ArrayList<>();
    private static Timer timerToBuildMiniBoss = new Timer();
    private static CupHead cupHead;
    private static Pane pane;
    private static Boss boss;
    private static int score = 0;
    private static long start;
    private static long end = 0;
    private static int blips = 0;
    private static int bulletRate = 0;

    public static void changeCupHeadPosition(int direction) {
        int changeDirectionPerClick = 3;
        switch (direction) {
            case 1 -> cupHead.getImageView().setX(cupHead.getImageView().getX()
                    + changeDirectionPerClick);
            case 0 -> cupHead.getImageView().setX(cupHead.getImageView().getX()
                    - changeDirectionPerClick);
            case 2 -> cupHead.getImageView().setY(cupHead.getImageView().getY()
                    - changeDirectionPerClick);
            case 3 -> cupHead.getImageView().setY(cupHead.getImageView().getY()
                    + changeDirectionPerClick);
        }
        if (cupHead.getImageView().getX() < 0)
            cupHead.getImageView().setX(0);
        if (cupHead.getImageView().getX() > GameController.getWindowWidth() - cupHead.getWidth())
            cupHead.getImageView().setX(GameController.getWindowWidth() - cupHead.getWidth());
        if (cupHead.getImageView().getY() < 25)
            cupHead.getImageView().setY(25);
        if (cupHead.getImageView().getY() > GameController.getWindowHeight() - cupHead.getHeight())
            cupHead.getImageView().setY(GameController.getWindowHeight() - cupHead.getHeight());
    }

    public static boolean shootBullets(boolean isForced) {
        if (!isForced) {
            bulletRate = (bulletRate + 1) % 10;
            if (bulletRate != 0)
                return false;
        }
        Random random = new Random();
        Bullet bullet = new Bullet((int) cupHead.getImageView().getX() +
                cupHead.getWidth() / 2,
                (int) cupHead.getImageView().getY() +
                        random.nextInt(cupHead.getHeight()));
        BULLETS.add(bullet);
        return true;
    }

    public static void newMiniBosses() {
        Random random = new Random();
        int randInt = random.nextInt(4000) %
                ((int) getWindowHeight() - (int) MiniBoss.getHeight());
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
        }, 300, 600);
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
        EGGS.add(egg);
        pane.getChildren().add(egg.getImageView());
    }

    public static void deleteBullet(Bullet bullet) {
        bullet.getImageView().setImage(null);
        bullet.stop();
        BULLETS.remove(bullet);
    }

    public static void deleteMiniBuss(MiniBoss miniBoss) {
        miniBoss.getHealthBar().getText().setOpacity(0);
        miniBoss.stop();
        miniBoss.getImageView().setImage(null);
        miniBosses.remove(miniBoss);
        miniBoss.getHealthBar().stop();
        miniBoss.getHealthBar().getText().setText(null);
        miniBoss.getHealthBar().getFormat().setImage(null);
        miniBoss.getHealthBar().getGreenBar().setImage(null);
        miniBoss.getHealthBar().getBlueBar().setImage(null);
    }

    public static boolean intersectsTransParent(ImageView imageView1, ImageView imageView2) {
        if (imageView1.getImage() == null ||
                !intersects(imageView1, imageView2, null))
            return false;
        for (int i = 0; i < imageView1.getImage().getWidth(); i += (int) imageView1.getImage().getWidth() - 1)
            for (int j = (int) imageView1.getImage().getHeight() - 1; j >= 0; j--)
                if (transParentOperation(imageView1, imageView2, i, j))
                    return true;
        for (int j = 0; j < imageView1.getImage().getHeight(); j += (int) imageView1.getImage().getHeight() - 1)
            for (int i = (int) imageView1.getImage().getWidth() - 1; i >= 0; i--)
                if (transParentOperation(imageView1, imageView2, i, j))
                    return true;
        System.out.println();
        return false;
    }

    private static boolean transParentOperation(ImageView imageView1, ImageView imageView2, int i, int j) {
        if (imageView1.getImage().getPixelReader().getColor(i, j).getOpacity() == 0)
            return false;
        int wx = (int) (imageView1.getX() + i - imageView2.getX());
        int wy = (int) (imageView1.getY() + j - imageView2.getY());

        if (wx < 0 ||
                wx >= imageView2.getImage().getWidth() ||
                wy < 0 ||
                wy >= imageView2.getImage().getHeight())
            return false;
        double op = imageView2.getImage().getPixelReader().getColor(wx, wy).getOpacity();
        return op != 0;
    }

    public static boolean intersects(ImageView imageView1,
                                     ImageView imageView2,
                                     Armament armament1) {
        double width1 = imageView1.getImage().getWidth(),
                width2 = imageView2.getImage().getWidth();
        double x1 = imageView1.getX(), x2 = imageView2.getX();
        if (armament1 instanceof Boss) {
            if (boss.getPhase() == 1)
                width1 *= 0.7211;
            else if (boss.getPhase() == 2)
                width1 *= 0.8092;
            x1 += imageView1.getImage().getWidth() - width1;
        }
        double middleX1 = x1 + width1 / 2, middleX2 = x2 + width2 / 2;
        if (Math.abs(middleX1 - middleX2) > (width1 + width2) / 2)
            return false;
        double height1 = imageView1.getImage().getHeight(), height2 = imageView2.getImage().getHeight();
        double y1 = imageView1.getY(), y2 = imageView2.getY();
        if (armament1 instanceof Boss) {
            if (boss.getPhase() == 1)
                height1 *= 0.8844;
            if (boss.getPhase() == 2)
                height1 *= 0.8082;
        }
        double middleY1 = y1 + height1 / 2, middleY2 = y2 + height2 / 2;
        return !(Math.abs(middleY1 - middleY2) > (height1 + height2) / 2);
    }

    public static void deleteEgg(Egg egg) {
        egg.getImageView().setImage(null);
        egg.stop();
        EGGS.remove(egg);
    }

    public static void endTheGame(boolean didWin) throws IOException {
        if (end != 0)
            return;
        end = System.currentTimeMillis();
        if (didWin) {
            score += cupHead.getHealth() * 100 + 10000;
            if (boss.getPhase() == 3)
                score += cupHead.getHealth() * 100 + 10000;
            int seconds = (int) (end - start) / 1000;
            for (int i = 10; i > 0; i--)
                if (seconds < 1100 - 100 * i) {
                    score += (1100 - 100 * i - seconds) * i;
                    break;
                }
        } else
            score += (1000 - boss.getHealth()) * 0.6;
        if (LoginController.getLoggedUser().getScore() < score) {
            LoginController.getLoggedUser().setScore(score);
            LoginController.getLoggedUser().setTime((GameController.getEnd() - GameController.getStart()) / 1000);
        }
        boss.stop();
        for (Bullet bullet : BULLETS)
            bullet.stop();
        for (Egg egg : EGGS)
            egg.stop();
        for (MiniBoss miniBoss : miniBosses)
            miniBoss.stop();
        GameMenuControllerFX.getAnimationTimer().stop();
        getTimerToBuildMiniBoss().cancel();
        ViewApplication.sceneChanger("gameEndMenu.fxml");
    }

    public static void setBoss(Boss boss) {
        GameController.boss = boss;
        boss.play();
    }

    public static void setCupHead(CupHead cupHead) {
        GameController.cupHead = cupHead;
    }

    public static void setStart(long start) {
        GameController.start = start;
    }

    public static void setTimerToBuildMiniBoss(Timer timerToBuildMiniBoss) {
        GameController.timerToBuildMiniBoss = timerToBuildMiniBoss;
    }

    public static String getTimerText() {
        long time = (System.currentTimeMillis() - start) / 1000;
        return String.format("%02d", (int) time / 60) + ":" + String.format("%02d", (int) time % 60);
    }

    public static void setPane(Pane pane) {
        GameController.pane = pane;
    }

    public static void setScore(int score) {
        GameController.score = score;
    }

    public static void setEnd(long end) {
        GameController.end = end;
    }

    public static Boss getBoss() {
        return boss;
    }

    public static long getStart() {
        return start;
    }

    public static long getEnd() {
        return end;
    }

    public static int getBlips() {
        return blips;
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

    public static ArrayList<Bullet> getBullets() {
        return BULLETS;
    }

    public static double getWindowHeight() {
        return 800;
    }

    public static double getWindowWidth() {
        return 1280;
    }

    public static CupHead getCupHead() {
        return cupHead;
    }
}
