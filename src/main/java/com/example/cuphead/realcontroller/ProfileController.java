package com.example.cuphead.realcontroller;

import com.example.cuphead.model.User;


public class ProfileController {
    public static int changeUsername(String newUsername) {
        if (newUsername == null)
            return 2;
        User tempUser = User.findUser(newUsername);
        if (tempUser != null)
            return 1;
        LoginController.getLoggedUser().changeUsername(newUsername);
        return 0;
    }

    public static int changePassword(String newPassword) {
        if (newPassword == null)
            return 1;
        LoginController.getLoggedUser().changePassword(newPassword);
        return 0;
    }
}
