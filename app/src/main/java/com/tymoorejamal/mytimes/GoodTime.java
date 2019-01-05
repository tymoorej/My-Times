package com.tymoorejamal.mytimes;

import java.util.ArrayList;

public class GoodTime {
    private int tid;
    private String title;
    private String description;
    private int rating;
    private double latitude;
    private double longitude;
    private String stime;
    private String etime;
    private ArrayList<byte[]> images;

    public GoodTime(int tid, String title, String description, int rating, double latitude, double longitude, String stime, String etime, ArrayList<byte[]> images) {
        this.tid = tid;
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.latitude = latitude;
        this.longitude = longitude;
        this.stime = stime;
        this.etime = etime;
        this.images = images;
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

    public ArrayList<byte[]> getImages() {
        return images;
    }

    public void setImages(ArrayList<byte[]> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return this.tid + ", " + title + ", " + description + ", " + rating + ", " + latitude + ", " + longitude + ", " + stime + ", " + etime +". Number of Images: " + images.size();
    }
}
