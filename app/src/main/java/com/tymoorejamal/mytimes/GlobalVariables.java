package com.tymoorejamal.mytimes;

import android.app.Application;

public class GlobalVariables extends Application {
    private boolean canUseLocation = false;
    private double latitude = 0;
    private double longitude = 0;

    public boolean getCanUseLocation() {
        return canUseLocation;
    }

    public void setCanUseLocation(boolean canUseLocation) {
        this.canUseLocation = canUseLocation;
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
}
