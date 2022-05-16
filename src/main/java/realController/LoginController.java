package realController;

import com.example.cupHead.model.User;

public class LoginController {
    private static User loggedUser;

    public static int createNewUser(String username,
                                    String password) {
        if (User.findUser(username) != null)
            return 1;
        new User(username, password);
        return 0;
    }

    public static int loginUser(String username, String password) {
        User tempUser = User.findUser(username);
        if (tempUser == null)
            return 1;
        if (!tempUser.isPasswordCorrect(password))
            return 2;
        loggedUser = tempUser;
        return 0;
    }

    public static int changePassword(String currentPassword,
                                     String newPassword) {
        if (!loggedUser.isPasswordCorrect(currentPassword))
            return 1;
        if (loggedUser.isPasswordCorrect(newPassword))
            return 2;
        loggedUser.changePassword(newPassword);
        return 0;
    }

    public static User getLoggedUser() {
        return loggedUser;
    }

}
