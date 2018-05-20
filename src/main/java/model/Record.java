package model;

import com.fasterxml.jackson.databind.JsonNode;

public class Record {

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPace() {
        return pace;
    }

    public void setPace(double pace) {
        this.pace = pace;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getAscent() {
        return ascent;
    }

    public void setAscent(double ascent) {
        this.ascent = ascent;
    }

    public double getDescent() {
        return descent;
    }

    public void setDescent(double descent) {
        this.descent = descent;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public Record(JsonNode source) {
        this.user_id = source.get("user_id") != null ? source.get("user_id").asText() : "";
        this.activity_id = source.get("activity_id") != null ? source.get("activity_id").asText() : "";
        this.start = source.get("start") != null ? source.get("start").asText() : "";
        this.end = source.get("end") != null ? source.get("end").asText() : "";
        this.type = source.get("type") != null ? source.get("type").asText() : "";
        this.pace = source.get("pace") != null ? source.get("pace").asDouble() : -1.0d;
        this.distance = source.get("distance") != null ? source.get("distance").asDouble() : -1.0d;
        this.steps = source.get("steps") != null ? source.get("steps").asInt() : -1;
        this.speed = source.get("speed") != null ? source.get("speed").asDouble() : -1.0d;
        this.ascent = source.get("ascent") != null ? source.get("ascent").asDouble() : -1.0d;
        this.calories = source.get("calories") != null ? source.get("calories").asInt() : -1;
        this.descent = source.get("descent") != null ? source.get("descent").asDouble() : -1.0d;
    }

    private String user_id;
    private String activity_id;
    private String start;
    private String end;
    private String type;

    private double pace;
    private double distance;
    private double speed;
    private double ascent;
    private double descent;

    private int steps;
    private int calories;
}
