package com.example.sameapp.Model.ApiResponseFields;

import com.google.gson.annotations.SerializedName;

public class MainInfo {

    @SerializedName("temp")
    private float mTemperature;

    @SerializedName("feels_like")
    private float mTemperatureFeelsLike;

    @SerializedName("temp_min")
    private float mMinTemperature;

    @SerializedName("temp_max")
    private float mMaxTemperature;

    @SerializedName("humidity")
    private float mHumidity;

    public float getmTemperature() {
        return mTemperature;
    }

    public void setmTemperature(float mTemperature) {
        this.mTemperature = mTemperature;
    }

    public float getmTemperatureFeelsLike() {
        return mTemperatureFeelsLike;
    }

    public void setmTemperatureFeelsLike(float mTemperatureFeelsLike) {
        this.mTemperatureFeelsLike = mTemperatureFeelsLike;
    }

    public float getmMinTemperature() {
        return mMinTemperature;
    }

    public void setmMinTemperature(float mMinTemperature) {
        this.mMinTemperature = mMinTemperature;
    }

    public float getmMaxTemperature() {
        return mMaxTemperature;
    }

    public void setmMaxTemperature(float mMaxTemperature) {
        this.mMaxTemperature = mMaxTemperature;
    }

    public float getmHumidity() {
        return mHumidity;
    }

    public void setmHumidity(float mHumidity) {
        this.mHumidity = mHumidity;
    }
}
