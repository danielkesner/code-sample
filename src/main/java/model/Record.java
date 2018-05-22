package model;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Comparator;

public class Record implements Comparator<Record> {

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

    public String getStart() { return start; }

    public void setStart(String start) { this.start = start; }

    public String getEnd() { return end; }

    public void setEnd(String end) { this.end = end; }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(String activity_id) {
        this.activity_id = activity_id;
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

    public Record() { }

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

    public String getSimpleStartDate() {
        return this.start.substring(0, 10);
    }

    private boolean equals(Record r1, Record r2) {
        return r1.getStart().equalsIgnoreCase(r2.getStart())
                && r1.getUser_id().equalsIgnoreCase(r2.getUser_id());
    }

    public int compare(Record r1, Record r2) {
        if (r1 == null || r2 == null) {
            throw new IllegalArgumentException("One or more of the arguments passed to RecordComparator.compare() is null");
        }

        DateTime first = new DateTime(r1.getStart());
        DateTime second = new DateTime(r2.getStart());

        if (equals(r1, r2)) {
            return 0;
        }

        // Sort in ascending order of date [earliest date first]
        if (first.isBefore(second)) {
            return -1;
        }
        else {
            return 1;
        }
    }
}
