package com.example.cupHead.fxController;

import com.example.cupHead.ViewApplication;
import com.example.cupHead.model.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import realController.LoginController;
import realController.ProfileController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfileControllerFX implements Initializable {
    @FXML
    private TextField newUsername, newPassword;

    @FXML
    private ImageView imageView;

    @FXML
    public void changeUsername() {
        Alert alert;
        switch (ProfileController.changeUsername(newUsername.getText())) {
            case 0 -> {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("what a shitty username");
                alert.setContentText("username changed successfully");
                alert.showAndWait();
            }
            case 1 -> {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Input not valid");
                alert.setContentText("this username is already taken");
                alert.showAndWait();
            }
            case 2 -> {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Input not valid");
                alert.setContentText("actually there is no input");
                alert.showAndWait();
            }
        }
    }

    @FXML
    public void changePassword() {
        if (ProfileController.changePassword(newPassword.toString()) == 1) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input not valid");
            errorAlert.setContentText("actually there is no input");
            errorAlert.showAndWait();
            return;
        }
        ProfileController.changePassword(newUsername.getText());
        Alert confirmationAlert = new Alert(Alert.AlertType.INFORMATION);
        confirmationAlert.setHeaderText("what a dumb password");
        confirmationAlert.setContentText("password changed successfully");
        confirmationAlert.showAndWait();
    }

    @FXML
    public void deleteAccount() throws IOException {
        LoginController.getLoggedUser().deleteAccount();
        Alert confirmationAlert = new Alert(Alert.AlertType.INFORMATION);
        confirmationAlert.setHeaderText("oh no");
        confirmationAlert.setContentText("you deleted your account");
        confirmationAlert.showAndWait();
        exitAccount();
    }

    @FXML
    public void mainMenu() throws IOException {
        ViewApplication.sceneChanger("mainMenu.fxml");
    }

    @FXML
    public void exitAccount() throws IOException {
        ViewApplication.sceneChanger("loginMenu.fxml");
    }

    @FXML
    public void nextProfile() {
        LoginController.getLoggedUser().changeProfile(true);
        loadProfile(LoginController.getLoggedUser(), imageView);
    }

    @FXML
    public void lastProfile() {
        LoginController.getLoggedUser().changeProfile(false);
        loadProfile(LoginController.getLoggedUser(), imageView);
    }

    public static void loadProfile(User user, ImageView newImageView) {
        newImageView.setImage(new Image("com/example/assets/profilePictures/" +
                user.getAvatarNumber() + ".png"));
    }

    public void initialize(URL location, ResourceBundle resources) {
        loadProfile(LoginController.getLoggedUser(), imageView);
    }
}

