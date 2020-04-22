package com.example.sameapp.Model.ApiResponseFields;

import com.google.gson.annotations.SerializedName;

public class Sys {

    @SerializedName("type")
    private int mType;

    @SerializedName("id")
    private long mId;

    @SerializedName("country")
    private String country;

    @SerializedName("sunrise")
    private long mSunrise;

    @SerializedName("sunset")
    private long mSunset;

    public int getmType() {
        return mType;
    }

    public void setmType(int mType) {
        this.mType = mType;
    }

    public long getmId() {
        return mId;
    }

    public void setmId(long mId) {
        this.mId = mId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public long getmSunrise() {
        return mSunrise;
    }

    public void setmSunrise(long mSunrise) {
        this.mSunrise = mSunrise;
    }

    public long getmSunset() {
        return mSunset;
    }

    public void setmSunset(long mSunset) {
        this.mSunset = mSunset;
    }
}
