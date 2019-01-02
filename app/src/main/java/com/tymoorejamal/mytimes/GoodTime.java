package com.tymoorejamal.mytimes;

public class GoodTime {
    private int tid;
    private String title;
    private String description;
    private int rating;
    private double latitude;
    private double longitude;
    private String stime;
    private String etime;

    public GoodTime(int db_tid, String title, String description, int rating, double latitude, double longitude, String stime, String etime) {
        this.tid = db_tid;
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.latitude = latitude;
        this.longitude = longitude;
        this.stime = stime;
        this.etime = etime;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

    public String getEtime() {
        return etime;
    }

    public void setEtime(String etime) {
        this.etime = etime;
    }

    @Override
    public String toString() {
        return this.tid + ", " + title + ", " + description + ", " + rating + ", " + latitude + ", " + longitude + ", " + stime + ", " + etime;
    }
}
