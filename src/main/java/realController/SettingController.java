package realController;

import com.example.cupHead.model.Difficulty;

public class SettingController {
    private static Difficulty difficulty;
    private static boolean isDevilMode;
    private static boolean isSoundOn;


    public static Difficulty getDifficulty() {
        return difficulty;
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

}
