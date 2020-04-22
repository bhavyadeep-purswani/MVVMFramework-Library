package com.example.sameapp.Model;

import com.example.sameapp.Model.ApiResponseFields.Clouds;
import com.example.sameapp.Model.ApiResponseFields.Coordinates;
import com.example.sameapp.Model.ApiResponseFields.MainInfo;
import com.example.sameapp.Model.ApiResponseFields.Sys;
import com.example.sameapp.Model.ApiResponseFields.WeatherInfo;
import com.example.sameapp.Model.ApiResponseFields.Wind;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherResponse {

    @SerializedName("coord")
    private Coordinates mCoordinates;

    @SerializedName("weather")
    private List<WeatherInfo> mWeatherInfo;

    @SerializedName("base")
    private String mBase;

    @SerializedName("main")
    private MainInfo mMainInfo;

    @SerializedName("visibility")
    private long mVisibility;

    @SerializedName("wind")
    private Wind mWind;

    @SerializedName("clouds")
    private Clouds mClouds;

    @SerializedName("dt")
    private long mDate;

    @SerializedName("sys")
    private Sys mSys;

    @SerializedName("timezone")
    private long mTimezone;

    @SerializedName("id")
    private long mId;

    @SerializedName("name")
    private String mCityName;

    @SerializedName("cod")
    private int mCod;

    public Coordinates getmCoordinates() {
        return mCoordinates;
    }

    public void setmCoordinates(Coordinates mCoordinates) {
        this.mCoordinates = mCoordinates;
    }

    public List<WeatherInfo> getmWeatherInfo() {
        return mWeatherInfo;
    }

    public void setmWeatherInfo(List<WeatherInfo> mWeatherInfo) {
        this.mWeatherInfo = mWeatherInfo;
    }

    public String getmBase() {
        return mBase;
    }

    public void setmBase(String mBase) {
        this.mBase = mBase;
    }

    public MainInfo getmMainInfo() {
        return mMainInfo;
    }

    public void setmMainInfo(MainInfo mMainInfo) {
        this.mMainInfo = mMainInfo;
    }

    public long getmVisibility() {
        return mVisibility;
    }

    public void setmVisibility(long mVisibility) {
        this.mVisibility = mVisibility;
    }

    public Wind getmWind() {
        return mWind;
    }

    public void setmWind(Wind mWind) {
        this.mWind = mWind;
    }

    public Clouds getmClouds() {
        return mClouds;
    }

    public void setmClouds(Clouds mClouds) {
        this.mClouds = mClouds;
    }

    public long getmDate() {
        return mDate;
    }

    public void setmDate(long mDate) {
        this.mDate = mDate;
    }

    public Sys getmSys() {
        return mSys;
    }

    public void setmSys(Sys mSys) {
        this.mSys = mSys;
    }

    public long getmTimezone() {
        return mTimezone;
    }

    public void setmTimezone(long mTimezone) {
        this.mTimezone = mTimezone;
    }

    public long getmId() {
        return mId;
    }

    public void setmId(long mId) {
        this.mId = mId;
    }

    public String getmCityName() {
        return mCityName;
    }

    public void setmCityName(String mCityName) {
        this.mCityName = mCityName;
    }

    public int getmCod() {
        return mCod;
    }

    public void setmCod(int mCod) {
        this.mCod = mCod;
    }
}
