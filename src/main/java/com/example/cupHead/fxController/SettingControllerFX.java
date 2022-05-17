package com.example.cupHead.fxController;

import com.example.cupHead.ViewApplication;
import com.example.cupHead.model.Difficulty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import realController.SettingController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingControllerFX implements Initializable {
    @FXML
    Text currentDifficultyText, primitiveCupHeadHealthText,
            gettingDamagedCoefficientText,
            makingDamageCoefficientText, devilModeText, soundText;

    @FXML
    Button easyButton, normalButton, hardButton;

    @FXML
    public void mainMenu() throws IOException {
        ViewApplication.sceneChanger("mainMenu.fxml");
    }

    @FXML
    public void cupHeadButton() {
        SettingController.setDifficulty(new Difficulty(1));
        updateDifficultyTexts();
    }

    @FXML
    public void plasticCupHeadButton() {
        SettingController.setDifficulty(new Difficulty(2));
        updateDifficultyTexts();
    }

    @FXML
    public void noHeadButton() {
        SettingController.setDifficulty(new Difficulty(3));
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
        ViewApplication.getMediaPlayer().setMute(!SettingController.isIsSoundOn());
        setSoundText();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDevilModeText();
        updateDifficultyTexts();
        setSoundText();
    }

    private void setDevilModeText() {
        String string = "Devil Mode: ";
        if (SettingController.isDevilMode()) {
            devilModeText.setText(string + "ON");

            currentDifficultyText.setOpacity(0.5);
            primitiveCupHeadHealthText.setOpacity(0.5);
            gettingDamagedCoefficientText.setOpacity(0.5);
            makingDamageCoefficientText.setOpacity(0.5);
            easyButton.setDisable(true);
            normalButton.setDisable(true);
            hardButton.setDisable(true);
        } else {
            devilModeText.setText(string + "OFF");
            currentDifficultyText.setOpacity(1);
            primitiveCupHeadHealthText.setOpacity(1);
            gettingDamagedCoefficientText.setOpacity(1);
            makingDamageCoefficientText.setOpacity(1);
            easyButton.setDisable(false);
            normalButton.setDisable(false);
            hardButton.setDisable(false);
        }
        SettingController.setDifficulty(new Difficulty(3));
        updateDifficultyTexts();
    }

    private void setSoundText() {
        String string = "Sound: ";
        if (SettingController.isIsSoundOn())
            soundText.setText(string + "ON");
        else soundText.setText(string + "OFF");
    }

    private void setCurrentDifficultyText() {
        String string = "Current Difficulty: ";
        if (SettingController.getDifficulty().getType() == 1)
            currentDifficultyText.setText(string + "Cup-Head");
        else if (SettingController.getDifficulty().getType() == 2)
            currentDifficultyText.setText(string + "PlasticCup-Head");
        else currentDifficultyText.setText(string + "No-Head");
    }

    private void setPrimitiveCupHeadHealthText() {
        String string = "PrimitiveCupHeadHealth: ";
        if (SettingController.getDifficulty().getType() == 1)
            primitiveCupHeadHealthText.setText(string + "10");
        else if (SettingController.getDifficulty().getType() == 2)
            primitiveCupHeadHealthText.setText(string + "5");
        else primitiveCupHeadHealthText.setText(string + "2");
    }

    private void setGettingDamagedCoefficientText() {
        String string = "GettingDamagedCoefficient: ";
        if (SettingController.getDifficulty().getType() == 1)
            gettingDamagedCoefficientText.setText(string + "50%");
        else if (SettingController.getDifficulty().getType() == 2)
            gettingDamagedCoefficientText.setText(string + "100%");
        else
            gettingDamagedCoefficientText.setText(string + "150%");
    }

    private void setMakingDamageCoefficientText() {
        String string = "MakingDamageCoefficient: ";
        if (SettingController.getDifficulty().getType() == 1)
            makingDamageCoefficientText.setText(string + "150%");
        else if (SettingController.getDifficulty().getType() == 2)
            makingDamageCoefficientText.setText(string + "100%");
        else makingDamageCoefficientText.setText(string + "50%");
    }

    private void updateDifficultyTexts() {
        setCurrentDifficultyText();
        setPrimitiveCupHeadHealthText();
        setGettingDamagedCoefficientText();
        setMakingDamageCoefficientText();
    }
}
