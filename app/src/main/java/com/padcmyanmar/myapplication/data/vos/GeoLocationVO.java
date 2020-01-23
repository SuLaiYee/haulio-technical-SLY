package com.padcmyanmar.myapplication.data.vos;

import com.google.gson.annotations.SerializedName;

public class GeoLocationVO {

    @SerializedName("latitude")
    private double latitude;

    @SerializedName("longitude")
    private double longitude;

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
