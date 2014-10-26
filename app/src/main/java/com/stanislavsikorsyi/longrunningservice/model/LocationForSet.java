package com.stanislavsikorsyi.longrunningservice.model;

import android.location.Location;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by stanislavsikorsyi on 15.07.14.
 */
public class LocationForSet {

    private double latitude;
    private double longitude;
    private long time;

    public LocationForSet(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        time = location.getTime();
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LocationForSet that = (LocationForSet) o;

        if (Double.compare(that.latitude, latitude) != 0) return false;
        if (Double.compare(that.longitude, longitude) != 0) return false;
        if (time != that.time) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(latitude);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (int) (time ^ (time >>> 32));
        return result;
    }

    @Override
    public String toString() {
        Date date = new Date(time);
        SimpleDateFormat timeFormat = new SimpleDateFormat("dd MM yy hh:mm:ss");
        String time = timeFormat.format(date);
        return time + " " + ", long: " + longitude + ", lat: " + latitude;
    }
}
