package com.example.cupHead.fxController;

import com.example.cupHead.model.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import realController.LoginController;

import java.net.URL;
import java.util.ResourceBundle;

public class ScoreboardControllerFX implements Initializable {


    @FXML
    private ImageView firstPP,secondPP,thirdPP;

    @FXML
    private Text text0,text1,text2,text3,text4,text5,text6,text7,text8,text9;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ProfileControllerFX.loadProfile(User.getListOfUsers().get(0),firstPP);
        ProfileControllerFX.loadProfile(User.getListOfUsers().get(1),secondPP);
        ProfileControllerFX.loadProfile(User.getListOfUsers().get(2),thirdPP);
        text0.setText(getDetails(0));
        text1.setText(getDetails(1));
        text2.setText(getDetails(2));
        text3.setText(getDetails(3));
        text4.setText(getDetails(4));
        text5.setText(getDetails(5));
        text6.setText(getDetails(6));
        text7.setText(getDetails(7));
        text8.setText(getDetails(8));
        text9.setText(getDetails(9));

    }

    private String getDetails(int i)
    {
        String rank;
        if(i<9)
            rank = "#0" + Integer.toString(i+1);
        else
            rank = "#" + Integer.toString(i+1);
        return rank + ". " + User.getListOfUsers().get(i).getUsername() + " | score: " +
                User.getListOfUsers().get(i).getScore() + " | time: " + User.getListOfUsers().get(i).getTime();
    }
}