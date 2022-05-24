package com.example.cuphead;

import com.example.cuphead.model.Difficulty;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import com.example.cuphead.realcontroller.SettingController;

import java.io.IOException;
import java.util.Objects;

public class ViewApplication extends Application {
    private static Stage globeStage;
    private static Scene scene;
    private static Pane pane;

    @Override
    public void start(Stage stage) throws IOException {
        SettingController.setDifficulty(Difficulty.plasticHead);
        SettingController.setDevilMode(false);
        globeStage = stage;
        SettingController.setIsSoundOn(true);
        sceneChanger("loginMenu.fxml");
        SettingController.playMusic("menuMusic");
    }

    public static void main(String[] args) {
        launch();
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

    public static Scene getScene() {
        return scene;
    }

}