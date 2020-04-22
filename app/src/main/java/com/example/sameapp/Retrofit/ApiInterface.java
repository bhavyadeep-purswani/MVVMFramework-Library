package com.example.sameapp.Retrofit;

import com.example.sameapp.Model.WeatherResponse;

import io.reactivex.Observable;
import io.reactivex.subjects.Subject;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("weather")
	Observable<WeatherResponse> getWeatherInfo(@Query("lat") double latitude, @Query("lon") double longitude, @Query("APPID") String apiKey, @Query("units") String unit);
}
