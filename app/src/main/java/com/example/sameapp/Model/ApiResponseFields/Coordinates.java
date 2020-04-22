package com.example.sameapp.Model.ApiResponseFields;

import com.google.gson.annotations.SerializedName;

public class Coordinates {
    @SerializedName("lon")
    private float mLongitude;

    @SerializedName("lat")
    private float mLatitude;

    public float getmLongitude() {
        return mLongitude;
    }

    public void setmLongitude(float mLongitude) {
        this.mLongitude = mLongitude;
    }

    public float getmLatitude() {
        return mLatitude;
    }

    public void setmLatitude(float mLatitude) {
        this.mLatitude = mLatitude;
    }
}
