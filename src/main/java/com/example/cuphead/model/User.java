package com.example.cuphead.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.example.cuphead.realcontroller.LoginController;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class User {
    private final static int SIZE_OF_AVATARS = 3;
    private static ArrayList<User> listOfUsers = new ArrayList<>();
    private String username;
    private String password;
    private long time;
    private int score;
    private int avatarNumber;

    static {
        try {
            String json = new String(Files.readAllBytes(Paths.get("dataBase/users.json")));
            listOfUsers = new Gson().fromJson(json, new TypeToken<List<User>>() {
            }.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.score = 0;
        time = 0;
        Random random = new Random();
        this.avatarNumber = random.nextInt(SIZE_OF_AVATARS - 1);
        listOfUsers.add(this);
        saveData();
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

    public static User findUser(String string) {
        if (listOfUsers == null)
            return null;
        for (User listOfUser : listOfUsers)
            if (Objects.equals(listOfUser.username, string))
                return listOfUser;
        return null;
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
            return Long.compare(o1.time, o2.time);
        }
    }

    public void changeUsername(String newUsername) {
        this.username = newUsername;
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

    public void changeProfile(boolean isNext) {
        int next = -1;
        if (isNext)
            next = 1;
        avatarNumber = (avatarNumber + next) % SIZE_OF_AVATARS;
        if (avatarNumber < 0)
            avatarNumber += SIZE_OF_AVATARS;
        saveData();
    }

    public void setScore(int score) {
        this.score = score;
        saveData();
    }

    public void setTime(long time) {
        this.time = time;
        saveData();
    }

    public int getAvatarNumber() {
        return avatarNumber;
    }

    public static ArrayList<User> getListOfUsers() {
        return listOfUsers;
    }

    public String getUsername() {
        return username;
    }

    public long getTime() {
        return time;
    }

    public int getScore() {
        return score;
    }
}
