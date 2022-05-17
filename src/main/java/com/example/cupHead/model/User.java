package com.example.cupHead.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import realController.LoginController;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class User {
    private static ArrayList<User> listOfUsers = new ArrayList<>();
    final static int sizeOfAvatars = 3;
    private String username;
    private String password;
    private int score;
    private int avatarNumber;
    private int time;

    static {
        try {
            String json = new String(Files.readAllBytes(Paths.get("dataBase/users.json")));
            listOfUsers = new Gson().fromJson(json, new TypeToken<List<User>>() {
            }.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveData() {
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter("dataBase/users.json");
            fileWriter.write(new Gson().toJson(listOfUsers));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeUsername(String newUsername) {
        this.username = newUsername;
        saveData();
    }

    public static User findUser(String string) {
        if (listOfUsers == null)
            return null;
        for (User listOfUser : listOfUsers)
            if (Objects.equals(listOfUser.username, string))
                return listOfUser;
        return null;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.score = 0;
        time = 0;
        Random random = new Random();
        this.avatarNumber = random.nextInt(sizeOfAvatars - 1);
        listOfUsers.add(this);
        saveData();
    }

    public boolean isPasswordCorrect(String password) {
        return this.password.equals(password);
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
        saveData();
    }

    public void deleteAccount() {
        listOfUsers.remove(LoginController.getLoggedUser());
        saveData();
    }

    public int getAvatarNumber() {
        return avatarNumber;
    }

    public void changeProfile(boolean isNext) {
        int next = -1;
        if (isNext)
            next = 1;
        avatarNumber = (avatarNumber + next) % sizeOfAvatars;
        if (avatarNumber < 0)
            avatarNumber += sizeOfAvatars;
    }

    public static void sort() {
        listOfUsers.sort(new scoreCompare());
    }

    public static class scoreCompare implements Comparator<User> {

        @Override
        public int compare(User o1, User o2) {
            if (o1.score < o2.score)
                return 1;
            if (o1.score > o2.score)
                return -1;
            return Integer.compare(o1.time, o2.time);
        }
    }

    public static ArrayList<User> getListOfUsers() {
        return listOfUsers;
    }

    public String getUsername() {
        return username;
    }

    public int getTime() {
        return time;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
