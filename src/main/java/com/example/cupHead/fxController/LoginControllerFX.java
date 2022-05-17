package com.example.cupHead.fxController;

import com.example.cupHead.ViewApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginControllerFX {
    @FXML
    private TextField username, password;

    @FXML
    private void register() {
        int newUser = realController.LoginController.createNewUser(username.getText(),
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
        int i = realController.LoginController.loginUser(username.getText(),
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
}