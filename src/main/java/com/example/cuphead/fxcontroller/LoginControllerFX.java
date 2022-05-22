package com.example.cuphead.fxcontroller;

import com.example.cuphead.ViewApplication;
import com.example.cuphead.realcontroller.GameController;
import com.example.cuphead.realcontroller.SettingController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginControllerFX implements Initializable {
    @FXML
    private TextField username, password;

    @FXML
    Pane pane;

    @FXML
    private void register() {
        int newUser = com.example.cuphead.realcontroller.LoginController.createNewUser(username.getText(),
                password.getText());
        if (newUser == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("what a dumb username");
            alert.setContentText("user created successfully");
            alert.showAndWait();
        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input not valid");
            errorAlert.setContentText("a user with this username already exists");
            errorAlert.showAndWait();
        }
    }

    @FXML
    private void login() throws IOException {
        int i = com.example.cuphead.realcontroller.LoginController.loginUser(username.getText(),
                password.getText());
        if (i == 0)
            ViewApplication.sceneChanger("mainMenu.fxml");
        else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input not valid");
            errorAlert.setContentText("username or password is incorrect");
            errorAlert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        GameController.setPane(pane);
        SettingController.setEffect(pane);
    }
}