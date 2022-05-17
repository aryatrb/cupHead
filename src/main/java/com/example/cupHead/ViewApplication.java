package com.example.cupHead;

import com.example.cupHead.model.Difficulty;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import realController.SettingController;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class ViewApplication extends Application {
    private static Stage globeStage;
    private static Scene scene;
    private static Pane pane;
    private static MediaPlayer mediaPlayer;
    @Override
    public void start(Stage stage) throws IOException {
        SettingController.setDifficulty(new Difficulty(2));
        SettingController.setDevilMode(false);
        globeStage = stage;
        SettingController.setIsSoundOn(true);
        sceneChanger("loginMenu.fxml");
        playMusic("menuMusic");
    }

    public static void sceneChanger(String fxmlName) throws IOException {
        globeStage.close();
        pane = FXMLLoader.load(Objects
                .requireNonNull(ViewApplication.class.getResource(fxmlName)));
        scene = new Scene(pane);
        globeStage.setTitle("CupHead");
        globeStage.setScene(scene);
        globeStage.show();
    }
    public static void main(String[] args) {
        launch();
    }

    public static Scene getScene() {
        return scene;
    }

    public static void playMusic(String string)
    {

        if(mediaPlayer!=null)
            mediaPlayer.stop();
        mediaPlayer = new MediaPlayer(new Media(
                new File("src/main/resources/com/example/assets/music/" +
                string + ".mp3").toURI().toString()));
        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
        mediaPlayer.play();
        if(!SettingController.isIsSoundOn())
            mediaPlayer.setMute(true);
    }

    public static MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }
}