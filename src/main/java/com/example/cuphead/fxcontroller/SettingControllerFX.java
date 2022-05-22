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
        ViewApplication.getMediaPlayer().setMute(!SettingController.isIsSoundOn());
        setSoundText();
    }

    @FXML
    public void BWButton() {
        SettingController.setIsBW(!SettingController.isIsBW());
        setBWText();
        SettingController.setEffect(pane);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDevilModeText();
        updateDifficultyTexts();
        setSoundText();
        setBWText();
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

    private void setSoundText() {
        String string = "Sound: ";
        if (SettingController.isIsSoundOn())
            soundText.setText(string + "ON");
        else soundText.setText(string + "OFF");
    }

    private void setBWText() {
        String string = "Black and White: ";
        if (SettingController.isIsBW())
            bWText.setText(string + "ON");
        else bWText.setText(string + "OFF");
    }

    private void setCurrentDifficultyText() {
        String string = "Current Difficulty: ";
        if (SettingController.getDifficulty() == Difficulty.cupHead)
            currentDifficultyText.setText(string + "Cup-Head");
        else if (SettingController.getDifficulty() == Difficulty.plasticHead)
            currentDifficultyText.setText(string + "PlasticCup-Head");
        else currentDifficultyText.setText(string + "No-Head");
    }

    private void setPrimitiveCupHeadHealthText() {
        String string = "PrimitiveCupHeadHealth: ";
        if (SettingController.getDifficulty() == Difficulty.cupHead)
            primitiveCupHeadHealthText.setText(string + "10");
        else if (SettingController.getDifficulty() == Difficulty.plasticHead)
            primitiveCupHeadHealthText.setText(string + "5");
        else primitiveCupHeadHealthText.setText(string + "2");
    }

    private void setGettingDamagedCoefficientText() {
        String string = "GettingDamagedCoefficient: ";
        if (SettingController.getDifficulty() == Difficulty.cupHead)
            gettingDamagedCoefficientText.setText(string + "50%");
        else if (SettingController.getDifficulty() == Difficulty.plasticHead)
            gettingDamagedCoefficientText.setText(string + "100%");
        else
            gettingDamagedCoefficientText.setText(string + "150%");
    }

    private void setMakingDamageCoefficientText() {
        String string = "MakingDamageCoefficient: ";
        if (SettingController.getDifficulty() == Difficulty.cupHead)
            makingDamageCoefficientText.setText(string + "150%");
        else if (SettingController.getDifficulty() == Difficulty.plasticHead)
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
