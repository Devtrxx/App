package com.dadash.easeride;

public class Ride {
    private String to;
    private String from;
    private String date;
    private String time;
    private String distance;
    private String vehicleType;
    private String fare;

    // Constructors
    public Ride() {
        // Default constructor required for Firebase
    }

    public Ride(String to, String from, String date, String time, String distance, String vehicleType, String fare) {
        this.to = to;
        this.from = from;
        this.date = date;
        this.time = time;
        this.distance = distance;
        this.vehicleType = vehicleType;
        this.fare = fare;
    }

    // Getters and Setters
    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getFare() {
        return fare;
    }

    public void setFare(String fare) {
        this.fare = fare;
    }
}