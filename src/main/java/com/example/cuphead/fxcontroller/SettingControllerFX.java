package com.example.cuphead.fxcontroller;

import com.example.cuphead.ViewApplication;
import com.example.cuphead.model.Difficulty;
import com.example.cuphead.realcontroller.GameController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import com.example.cuphead.realcontroller.SettingController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingControllerFX implements Initializable {
    @FXML
    Text currentDifficultyText, bWText,
            primitiveCupHeadHealthText,
            gettingDamagedCoefficientText,
            makingDamageCoefficientText,
            devilModeText, soundText;

    @FXML
    Button easyButton, normalButton, hardButton;

    @FXML
    Pane pane;

    @FXML
    public void mainMenu() throws IOException {
        ViewApplication.sceneChanger("mainMenu.fxml");
    }

    @FXML
    public void cupHeadButton() {
        SettingController.setDifficulty(Difficulty.cupHead);
        updateDifficultyTexts();
    }

    @FXML
    public void plasticCupHeadButton() {
        SettingController.setDifficulty(Difficulty.plasticHead);
        updateDifficultyTexts();
    }

    @FXML
    public void noHeadButton() {
        SettingController.setDifficulty(Difficulty.noHead);
        updateDifficultyTexts();
    }

    @FXML
    public void devilButton() {
        SettingController.setDevilMode(!SettingController.isDevilMode());
        setDevilModeText();
    }

    @FXML
    public void soundButton() {
        SettingController.setIsSoundOn(!SettingController.isIsSoundOn());
        SettingController.getMediaPlayer().setMute(!SettingController.isIsSoundOn());
        setOnOffText(soundText, "Sound: ", SettingController.isIsSoundOn());
    }

    @FXML
    public void BWButton() {
        SettingController.setIsBW(!SettingController.isIsBW());
        setOnOffText(bWText, "Black and White: ", SettingController.isIsBW());
        SettingController.setEffect(pane);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDevilModeText();
        updateDifficultyTexts();
        setOnOffText(soundText, "Sound: ", SettingController.isIsBW());
        setOnOffText(bWText, "Black and White: ", SettingController.isIsBW());
        GameController.setPane(pane);
        SettingController.setEffect(pane);
    }

    private void setDevilModeText() {
        String string = "Devil Mode: ";
        String condition;
        double opacity;
        boolean bool;
        if (SettingController.isDevilMode()) {
            condition = "ON";
            opacity = 0.5;
            bool = true;
            SettingController.setDifficulty(Difficulty.noHead);
        } else {
            condition = "OFF";
            opacity = 1;
            bool = false;
        }
        devilModeText.setText(string + condition);
        currentDifficultyText.setOpacity(opacity);
        primitiveCupHeadHealthText.setOpacity(opacity);
        gettingDamagedCoefficientText.setOpacity(opacity);
        makingDamageCoefficientText.setOpacity(opacity);
        easyButton.setDisable(bool);
        normalButton.setDisable(bool);
        hardButton.setDisable(bool);
        updateDifficultyTexts();
    }

    private void setOnOffText(Text text, String string, boolean bool) {
        if (bool)
            text.setText(string + "ON");
        else text.setText(string + "OFF");
    }

    private void setText(Text text, String string, String percent1, String percent2, String percent3) {
        if (SettingController.getDifficulty() == Difficulty.cupHead)
            text.setText(string + percent1);
        else if (SettingController.getDifficulty() == Difficulty.plasticHead)
            text.setText(string + percent2);
        else text.setText(string + percent3);
    }

    private void updateDifficultyTexts() {
        setText(currentDifficultyText, "Current Difficulty: ", "Cup-Head", "PlasticCup-Head", "No-Head");
        setText(primitiveCupHeadHealthText, "PrimitiveCupHeadHealth: ", "10", "5", "2");
        setText(gettingDamagedCoefficientText, "GettingDamagedCoefficient: ", "50%", "100%", "150%");
        setText(makingDamageCoefficientText, "MakingDamageCoefficient: ", "150%", "100%", "50%");
    }
}
