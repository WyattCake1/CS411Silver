package com.rolecall;

import java.text.SimpleDateFormat;

public class Listing {
    private int id;
    private String name;
    private String environment;
    private String startTime;
    private String endTime;
    private String difficulty;
    private String role;

    public Listing(){
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEnvironment() {
        return environment;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getRole() {
        return role;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
