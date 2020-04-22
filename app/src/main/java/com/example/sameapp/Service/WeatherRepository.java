package com.example.sameapp.Service;

import android.location.Location;

import com.example.sameapp.Model.Constants;
import com.example.sameapp.Model.WeatherResponse;
import com.example.sameapp.Retrofit.ApiClient;
import com.example.sameapp.Retrofit.ApiInterface;

import io.reactivex.Observable;

/*
 * Created by bhavyadeeppurswani on 16/04/20.
 */
public class WeatherRepository {

	private static WeatherRepository instance;
	private ApiInterface service;

	private WeatherRepository() {
		service = ApiClient.getClient().create(ApiInterface.class);
	}

	public static WeatherRepository getInstance() {
		if (instance == null) {
			instance = new WeatherRepository();
		}
		return instance;
	}

	public Observable<WeatherResponse> getWeatherData(Location location) {
		 return service.getWeatherInfo(location.getLatitude(), location.getLongitude(), Constants.API_KEY, "metric");
	}
}
