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
    public Listing(boolean campaign, String gameName, String environment,String day, String startTime, String endTime,
            String difficulty, String role, String userProfileId) {
        this.campaign = campaign;
        this.gameName = gameName;
        this.environment = environment;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.difficulty = difficulty;
        this.role = new HashMap<>();
        this.role.put("Tank",0);
        this.role.put("DPS",0);
        this.role.put("Face",0);
        this.role.put("Healer",0);
        this.role.put("Support",0);
        this.userProfileId = userProfileId;
    }

    //passed in json object
    public Listing(JSONObject json){
        this.campaign = json.get("campaign").toString().equals("1");
        this.gameName = json.get("gameName").toString();
        this.environment = json.get("environment").toString();
        this.day = json.get("day").toString();
        this.startTime = json.get("startTime").toString();
        this.endTime = json.get("endTime").toString();
        this.difficulty = json.get("difficulty").toString();
        this.role = new HashMap<>();
        JSONParser parser = new JSONParser();
        try {
            JSONObject roleJson = (JSONObject) parser.parse(json.get("role").toString());
            this.role.put("Tank", Integer.parseInt(roleJson.get("Tank").toString()));
            this.role.put("DPS", Integer.parseInt(roleJson.get("DPS").toString()));
            this.role.put("Face", Integer.parseInt(roleJson.get("Face").toString()));
            this.role.put("Healer", Integer.parseInt(roleJson.get("Healer").toString()));
            this.role.put("Support", Integer.parseInt(roleJson.get("Support").toString()));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        this.userProfileId = json.get("userProfileId").toString();
    }

    //passed in json string
    public Listing(String jsonString){
        JSONParser parser = new JSONParser();
        try{
            JSONObject json = (JSONObject) parser.parse(jsonString);
            this.campaign = json.get("campaign").toString().equals("1");
            this.gameName = json.get("gameName").toString();
            this.environment = json.get("environment").toString();
            this.day = json.get("day").toString();
            this.startTime = json.get("startTime").toString();
            this.endTime = json.get("endTime").toString();
            this.difficulty = json.get("difficulty").toString();
            this.role = new HashMap<>();
            JSONObject roleJson = (JSONObject) parser.parse(json.get("role").toString());
            this.role.put("DPS", Integer.parseInt(roleJson.get("DPS").toString()));
            this.role.put("healer", Integer.parseInt(roleJson.get("healer").toString()));
            this.role.put("tank", Integer.parseInt(roleJson.get("tank").toString()));
            this.userProfileId = json.get("userProfileId").toString();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public String toJson() {
        JSONObject json = new JSONObject();
        json.put("campaign", this.campaign ? 1 : 0);
        json.put("gameName", this.gameName);
        json.put("environment", this.environment);
        json.put("day", this.day);
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

    public int getRoleValue(String key){
        return this.role.get(key);
    }

    public void setRoleValue(String key, int value){
        this.role.put(key,value);
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
