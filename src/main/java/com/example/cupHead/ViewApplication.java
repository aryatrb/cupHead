package com.example.cupHead;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

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
        globeStage = stage;
        sceneChanger("loginMenu.fxml");
        playMusic("menuMusic");
    }

    public static void sceneChanger(String fxmlName) throws IOException {
        pane = FXMLLoader.load(Objects.requireNonNull(ViewApplication.class.getResource(fxmlName)));
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

    public static Pane getPane() {
        return pane;
    }

    public static void playMusic(String string)
    {
        if(mediaPlayer!=null)
            mediaPlayer.stop();
        mediaPlayer = new MediaPlayer(new Media(new File("src/main/resources/com/example/assets/" + string + ".mp3").toURI().toString()));
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            public void run() {
                mediaPlayer.seek(Duration.ZERO);
            }
        });
        mediaPlayer.play();
    }
}