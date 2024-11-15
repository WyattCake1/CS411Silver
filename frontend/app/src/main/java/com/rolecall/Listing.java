package com.rolecall;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Locale;


public class Listing {
    private boolean campaign;
    private String gameName;
    private String environment;
    private String startTime;
    private String endTime;
    private String difficulty;
    private String role;
    private String userProfileId;

    //Empty
    public Listing(){
    }
    
    //Passed in values (when making new ones)
    public Listing(boolean campaign, String gameName, String environment, String startTime, String endTime,
            String difficulty, String role, String userProfileId) {
        this.campaign = campaign;
        this.gameName = gameName;
        this.environment = environment;
        this.startTime = startTime;
        this.endTime = endTime;
        this.difficulty = difficulty;
        this.role = role;
        this.userProfileId = userProfileId;
    }

    //passed in json
    public Listing(String json){
        String cleanJson = json.substring(1,json.length()-1).trim();
        String[] attributes = cleanJson.split(",\n");
        for (String attribute : attributes){
            attribute = attribute.trim();
            String[] splitAttribute = attribute.split(":",2);
            String id = splitAttribute[0].substring(1,splitAttribute[0].length()-1).trim();
            String value = splitAttribute[1].trim();
            switch (id){
                case "campaign":
                    this.campaign = Integer.parseInt(value) == 1;
                    break;
                case "gameName":
                    this.gameName = value.substring(1,value.length()-1);
                    break;
                case "environment":
                    this.environment = value.substring(1,value.length()-1);
                    break;
                case "startTime":
                    this.startTime = value.substring(1,value.length()-1);
                    break;
                case "endTime":
                    this.endTime = value.substring(1,value.length()-1);
                    break;
                case "difficulty":
                    this.difficulty = value.substring(1,value.length()-1);
                    break;
                case "role":
                    this.role = value.substring(1,value.length()-1);
                    break;
                case "userProfileId":
                    this.userProfileId = value;
                    break;
            }
        }
    }

    public String toJson() {

        return "{" +
                "\"campaign\": " + campaign + ", " +
                "\"gameName\": \"" + gameName + "\", " +
                "\"environment\": \"" + environment + "\", " +
                "\"startTime\": \"" + convertToTimeStamp(startTime) + "\", " +
                "\"endTime\": \"" + convertToTimeStamp(endTime) + "\", " +
                "\"difficulty\": \"" + difficulty + "\", " +
                "\"role\": \"" + role + "\", " +
                "\"userProfileId\": \"" + userProfileId + "\"" +
                "}";
    }

    public Timestamp convertToTimeStamp(String time){
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z", Locale.getDefault());
        try {
            System.out.println(time);
            Date parsedDate = dateFormat.parse(time);
            System.out.println(parsedDate);
            return new Timestamp(parsedDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    public boolean isCampaign() {
        return campaign;
    }

    public String getGameName() {
        return gameName;
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

    public String getUserProfileId(){
        return this.userProfileId;
    }

    public void setCampaign(boolean campaign) {
        this.campaign = campaign;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
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

    public void setUserProfileId(String userProfileId){
        this.userProfileId = userProfileId;
    }
}
