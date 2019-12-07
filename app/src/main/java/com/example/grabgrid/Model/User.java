package com.example.grabgrid.Model;

import java.io.Serializable;

public class User implements Serializable {
   private int userId;
   private String username;
   private String password;
   private int stepsRemaining;
   private int chiLvl;

    public User(){}

    public User(int userId, String username, String password, int stepsRemaining, int chiLvl) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.stepsRemaining = stepsRemaining;
        this.chiLvl = chiLvl;
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

    public int getChiLvl() {
        return chiLvl;
    }

    public void setChiLvl(int chiLvl) {
        this.chiLvl = chiLvl;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", stepsRemaining=" + stepsRemaining +
                ", chiLvl=" + chiLvl +
                '}';
    }
}
