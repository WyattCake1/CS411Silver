package com.rolecall;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.parser.*;
import org.json.simple.JSONObject;

/** @noinspection ALL*/
public class Listing implements Serializable {
    private boolean campaign;
    private String listingId;
    private String gameName;
    private String environment;
    private String day;
    private String startTime;
    private String endTime;
    private String difficulty;
    private Map<String, Integer> role;
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
        this.role = new HashMap<>();
        this.role.put("dps",0);
        this.role.put("healer",0);
        this.role.put("tank",0);
        this.userProfileId = userProfileId;
    }

    //passed in json
    public Listing(JSONObject json){
        this.campaign = json.get("campaign").toString().equals("1");
        this.gameName = json.get("gameName").toString();
        this.environment = json.get("environment").toString();
        this.startTime = json.get("startTime").toString();
        this.endTime = json.get("endTime").toString();
        this.difficulty = json.get("difficulty").toString();
        this.role = new HashMap<>();
        JSONParser parser = new JSONParser();
        try {
            JSONObject roleJson = (JSONObject) parser.parse(json.get("role").toString());
            this.role.put("dps", Integer.parseInt(roleJson.get("dps").toString()));
            this.role.put("healer", Integer.parseInt(roleJson.get("healer").toString()));
            this.role.put("tank", Integer.parseInt(roleJson.get("tank").toString()));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        this.userProfileId = json.get("userProfileId").toString();

    }

    public String toJson() {
        JSONObject json = new JSONObject();
        json.put("campaign", this.campaign ? 1 : 0);
        json.put("gameName", this.gameName);
        json.put("environment", this.environment);
        json.put("startTime", this.startTime);
        json.put("endTime", this.endTime);
        json.put("difficulty", this.difficulty);
        json.put("userProfileId", this.userProfileId);

        JSONObject roleJson = new JSONObject(this.role);
        json.put("role", roleJson.toString());

        return json.toJSONString();
    }

    public boolean isCampaign() {
        return campaign;
    }

    public String getListingId(){
        return listingId;
    }

    public String getGameName() {
        return gameName;
    }

    public String getEnvironment() {
        return environment;
    }

    public String getDay() {
        return day;
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

    public Map<String, Integer> getRole() {
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

    public void setDay(String day) {
        this.day = day;
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

    public void setRole(Map<String,Integer> role) {
        this.role = role;
    }

    public void setUserProfileId(String userProfileId){
        this.userProfileId = userProfileId;
    }
}
