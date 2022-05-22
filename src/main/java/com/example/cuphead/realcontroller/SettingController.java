package com.example.cuphead.realcontroller;

import com.example.cuphead.model.Difficulty;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class SettingController {
    private final static ColorAdjust GRAY_SCALE = new ColorAdjust();
    private final static ColorAdjust COLORFUL = new ColorAdjust();
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

    public static void setEffect(Pane pane)
    {
        if(isBW)
            pane.setEffect(GRAY_SCALE);
        else pane.setEffect(COLORFUL);
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
