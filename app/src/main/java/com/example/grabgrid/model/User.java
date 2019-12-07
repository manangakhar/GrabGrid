package com.example.grabgrid.model;

import java.io.Serializable;

public class User implements Serializable {
   private int userId;
   private String username;
   private String password;
   private int stepsRemaining;

    public User(){}

    public User(int userId, String username, String password, int stepsRemaining) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.stepsRemaining = stepsRemaining;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStepsRemaining() {
        return stepsRemaining;
    }

    public void setStepsRemaining(int stepsRemaining) {
        this.stepsRemaining = stepsRemaining;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", stepsRemaining=" + stepsRemaining +
                '}';
    }
}
