package com.example.sameapp.View;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import com.bhavyadeeppurswani.mvvmframework.BaseFragment.BaseMVVMFragment;
import com.example.sameapp.Model.ApiResponseFields.MainInfo;
import com.example.sameapp.Model.ApiResponseFields.WeatherInfo;
import com.example.sameapp.Model.Constants;
import com.example.sameapp.Model.WeatherDataContract;
import com.example.sameapp.Model.WeatherResponse;
import com.example.sameapp.R;
import com.example.sameapp.ViewModel.WeatherViewModel;

import java.text.DecimalFormat;
import java.util.List;

/*
 * Created by bhavyadeeppurswani on 16/04/20.
 */
public class WeatherFragment extends BaseMVVMFragment<WeatherViewModel> {

	private WeatherViewModel weatherViewModel;
	private TextView tempView;
	private TextView minTempView;
	private TextView maxTempView;
	private TextView cityView;
	private TextView weatherImageView;
	private ProgressBar loadingView;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_weather,container, false);
		tempView = view.findViewById(R.id.weatherTemperature);
		minTempView = view.findViewById(R.id.weatherTempMin);
		maxTempView = view.findViewById(R.id.weatherTempMax);
		cityView = view.findViewById(R.id.weatherCity);
		weatherImageView = view.findViewById(R.id.weatherImage);
		loadingView = view.findViewById(R.id.loadingView);
		weatherViewModel = ViewModelProviders.of(this).get(WeatherViewModel.class);
		requestPermission();
		return view;
	}

	@Override
	protected WeatherViewModel getViewModel() {
		return weatherViewModel;
	}

	@Override
	protected int getLoadingViewId(String dataTag) {
		return R.id.loadingView;
	}

	@Override
	protected int getContentViewId(String dataTag) {
		return R.id.contentView;
	}

	@Override
	protected int getErrorViewId(String dataTag) {
		return R.id.errorView;
	}

	@Override
	protected void onError(Throwable throwable, String dataTag) {

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (!weatherViewModel.checkLocationEnabled()) {
			Toast.makeText(getContext(),"Please enable Location!", Toast.LENGTH_LONG).show();
			loadingView.setVisibility(View.GONE);
		}
		else {
			weatherViewModel.getLocation();
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		switch (requestCode) {
			case Constants.REQUEST_LOCATION_ACCESS_CODE: {
				if (grantResults.length > 0
						&& grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					onPermissionGranted();
				} else {
					requestPermission();
				}
			}

		}
	}

	private void requestPermission() {
		if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.INTERNET) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(
					getActivity(),
					new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.INTERNET},
					Constants.REQUEST_LOCATION_ACCESS_CODE);
		}
		else {
			onPermissionGranted();
		}
	}

	@Override
	protected void setContent(String dataTag) {
		switch (dataTag) {
			case WeatherDataContract.WeatherDataIdentifiers.RESPONSE:
				WeatherResponse response = getData(dataTag);
				MainInfo mainInfo = response.getmMainInfo();
				String city = response.getmCityName();
				List<WeatherInfo> weatherInfo = response.getmWeatherInfo();
				cityView.setText(city);
				DecimalFormat df = new DecimalFormat("#.#");
				tempView.setText(df.format(mainInfo.getmTemperature()));
				maxTempView.setText(df.format(mainInfo.getmMaxTemperature()));
				minTempView.setText(df.format(mainInfo.getmMinTemperature()));
				weatherImageView.setText(weatherViewModel.getImage(weatherInfo.get(0).getmIcon()));
				break;
			case WeatherDataContract.WeatherDataIdentifiers.LOCATION_RESPONSE:
				Location location = getData(dataTag);
				startObserving(weatherViewModel.getWeatherData(location), WeatherDataContract.WeatherDataIdentifiers.RESPONSE);
		}
	}

	private void onPermissionGranted() {
		if (weatherViewModel.checkLocationEnabled()) {
			weatherViewModel.getLocation();
		} else {
			Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			startActivityForResult(intent, Constants.REQUEST_LOCATION_ON_CODE);
		}
	}
}
