package com.example.cuphead.realcontroller;

import com.example.cuphead.ViewApplication;
import com.example.cuphead.model.Difficulty;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.util.Objects;

public class SettingController {
    private final static ColorAdjust GRAY_SCALE = new ColorAdjust();
    private final static ColorAdjust COLORFUL = new ColorAdjust();
    private static MediaPlayer mediaPlayer;
    private static Difficulty difficulty;
    private static boolean isDevilMode;
    private static boolean isSoundOn;
    private static boolean isBW = false;

    static {
        GRAY_SCALE.setSaturation(-1);
        COLORFUL.setSaturation(0);
    }

    public static Difficulty getDifficulty() {
        return difficulty;
    }

    public static void setEffect(Pane pane) {
        if (isBW)
            pane.setEffect(GRAY_SCALE);
        else pane.setEffect(COLORFUL);
    }

    public static void playMusic(String string) {

        if (mediaPlayer != null)
            mediaPlayer.stop();
        mediaPlayer = new MediaPlayer(new Media(Objects
                .requireNonNull(ViewApplication
                        .class.getResource("assets/music/" +
                        string + ".mp3")).toExternalForm()));
        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
        mediaPlayer.play();
        if (!SettingController.isIsSoundOn())
            mediaPlayer.setMute(true);
    }

    public static MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public static void setDifficulty(Difficulty difficulty) {
        SettingController.difficulty = difficulty;
    }

    public static boolean isDevilMode() {
        return isDevilMode;
    }

    public static void setDevilMode(boolean devilMode) {
        isDevilMode = devilMode;
    }

    public static boolean isIsSoundOn() {
        return isSoundOn;
    }

    public static void setIsSoundOn(boolean isSoundOn) {
        SettingController.isSoundOn = isSoundOn;
    }

    public static void setIsBW(boolean isBW) {
        SettingController.isBW = isBW;
    }

    public static boolean isIsBW() {
        return isBW;
    }
}
