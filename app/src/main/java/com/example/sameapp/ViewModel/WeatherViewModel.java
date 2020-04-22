package com.example.sameapp.ViewModel;

import android.app.Application;
import android.location.Location;

import androidx.annotation.NonNull;

import com.bhavyadeeppurswani.mvvmframework.BaseViewModel.BaseMVVMViewModel;
import com.example.sameapp.Location.LocationAPI;
import com.example.sameapp.Model.WeatherDataContract;
import com.example.sameapp.Model.WeatherResponse;
import com.example.sameapp.Service.WeatherRepository;

import java.util.HashMap;

import io.reactivex.Observable;

/*
 * Created by bhavyadeeppurswani on 16/04/20.
 */
public class WeatherViewModel extends BaseMVVMViewModel<Observable<?>> {

	private WeatherRepository weatherRepository;
	private LocationAPI locationService;

	public WeatherViewModel(@NonNull Application application) {
		super(application);
		weatherRepository = WeatherRepository.getInstance();
		locationService = LocationAPI.getInstance(application.getApplicationContext());
	}

	@Override
	public HashMap<String, Observable<?>> getInitialLoadData() {
		HashMap<String, Observable<?>> map = new HashMap<>();
		map.put(WeatherDataContract.WeatherDataIdentifiers.LOCATION_RESPONSE, locationService.getLocationObservable());
		return map;
	}

	public Observable<WeatherResponse> getWeatherData(Location location) {
		return weatherRepository.getWeatherData(location);
	}

	public void getLocation() {
		locationService.getLocation();
	}

	public boolean checkLocationEnabled() {
		return locationService.checkLocationEnabled();
	}

	public String getImage(String id) {
		int unicode;
		switch(id) {
			case "01n":
			case "01d":unicode = 0x2600; break;
			case "02n":
			case "02d": unicode = 0x1F324; break;
			case "03n":
			case "03d":
			case "04n":
			case "04d":unicode = 0x2601; break;
			case "09n":
			case "09d":unicode = 0x1F327; break;
			case "10n":
			case "10d":unicode = 0x1F326; break;
			case "11n":
			case "11d":unicode = 0x1F329; break;
			case "13d":
			case "13n":unicode = 0x2744; break;
			case "50n":
			case "50d":unicode = 0x1F32B; break;
			default:unicode=0;
		}
		return getEmojiByUnicode(unicode);
	}

	private String getEmojiByUnicode(int unicode){
		return new String(Character.toChars(unicode));
	}
}
