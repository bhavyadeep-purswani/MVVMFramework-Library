package com.bhavyadeeppurswani.mvvmframework.BaseFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bhavyadeeppurswani.mvvmframework.BaseViewModel.BaseMVVMViewModel;
import com.bhavyadeeppurswani.mvvmframework.Model.Response;
import com.bhavyadeeppurswani.mvvmframework.Model.States;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/*
 * Created by bhavyadeeppurswani on 14/04/20.
 */
public abstract class BaseMVVMFragment<V extends BaseMVVMViewModel> extends Fragment {

	private static final String TAG = BaseMVVMFragment.class.getSimpleName();
	private View view;
	private CompositeDisposable compositeDisposable;
	private HashMap<String, LiveData<Response<?>>> liveDataMap;
	private HashMap<String, States> initialLoadDataTags;

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		this.view = view;
		compositeDisposable = new CompositeDisposable();
		initMembers();
		loadInitialData();
		super.onViewCreated(view, savedInstanceState);

	}

	private void initMembers() {
		liveDataMap = new HashMap<>();
		initialLoadDataTags = new HashMap<>();
	}

	@Override
	public void onDestroyView() {
		compositeDisposable.dispose();
		super.onDestroyView();
	}

	protected void loadInitialData() {
		HashMap<String, ?> dataObjects = getViewModel().getInitialLoadData();
		if (dataObjects == null || dataObjects.size() == 0) {
			return;
		}
		for (Map.Entry<String, ?> entry : dataObjects.entrySet()) {
			initialLoadDataTags.put(entry.getKey(), States.LOADING);
			switch (getType(entry.getValue())) {
				case 0:
					startObserving((Observable<?>) entry.getValue(), entry.getKey());
					break;
				case 1:
					startObserving((LiveData<?>) entry.getValue(), entry.getKey());
					break;
				case -1:
				default:
					Log.e(TAG, "Invalid Initial load data type provided, expected of type: Observable or LiveData");
			}
		}
	}

	private <T> int getType(T parameter) {
		if (parameter instanceof Observable<?>) {
			return 0;
		} else if (parameter instanceof LiveData<?>) {
			return 1;
		} else {
			return -1;
		}
	}

	private void processResponse(Response<?> response) {
		boolean uiUpdateRequired = true;
		if (response.state != States.LOADING && initialLoadDataTags != null && initialLoadDataTags.size() > 0 && initialLoadDataTags.get(response.dataTag) != null) {
			uiUpdateRequired = processResponseForInitialData(response);
		}
		if (uiUpdateRequired) {
			view.findViewById(getLoadingViewId(response.dataTag)).setVisibility(View.GONE);
			view.findViewById(getContentViewId(response.dataTag)).setVisibility(View.GONE);
			view.findViewById(getErrorViewId(response.dataTag)).setVisibility(View.GONE);
			switch (response.state) {
				case LOADING:
					view.findViewById(getLoadingViewId(response.dataTag)).setVisibility(View.VISIBLE);
					break;
				case SUCCESS:
					setContent(response.dataTag);
					view.findViewById(getContentViewId(response.dataTag)).setVisibility(View.VISIBLE);
					break;
				case ERROR:
					view.findViewById(getErrorViewId(response.dataTag)).setVisibility(View.VISIBLE);
					onError(response.error, response.dataTag);
					break;
			}
		}
	}

	private boolean processResponseForInitialData(Response<?> response) {
		boolean uiUpdateRequired = true;
		initialLoadDataTags.put(response.dataTag, response.state);
		if (response.state == States.SUCCESS) {
			for (Map.Entry<String, States> initialData : initialLoadDataTags.entrySet()) {
				if (!response.dataTag.equalsIgnoreCase(initialData.getKey()) && getContentViewId(response.dataTag) == getContentViewId(initialData.getKey()) && initialData.getValue() != States.SUCCESS) {
					uiUpdateRequired = false;
					setContent(response.dataTag);
					break;
				}
			}
		}
		boolean isAllInitialDataProcessed = true;
		for (Map.Entry<String, States> initialData : initialLoadDataTags.entrySet()) {
			if (initialData.getValue() == States.LOADING) {
				isAllInitialDataProcessed = false;
				break;
			}
		}
		if (isAllInitialDataProcessed) {
			initialLoadDataTags = new HashMap<>();
		}
		return uiUpdateRequired;
	}

	protected void startObserving(Observable<?> observable, String dataTag) {
		MutableLiveData<Response<?>> liveData = new MutableLiveData<>();
		liveDataMap.put(dataTag, liveData);
		liveData.observe(this, this::processResponse);
		compositeDisposable.add(observable.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.doOnSubscribe(__ -> liveData.setValue(new Response<>(States.LOADING, null, null, dataTag)))
				.subscribe(
						response -> liveData.setValue(new Response<>(States.SUCCESS, response, null, dataTag)),
						throwable -> liveData.setValue(new Response<>(States.ERROR, null, (Throwable) throwable, dataTag))
				));
	}

	protected void startObserving(LiveData<?> liveData, String dataTag) {
		MutableLiveData<Response<?>> liveDataWrapper = new MutableLiveData<>();
		liveData.observe(this,
				response -> liveDataWrapper.setValue(new Response<>(States.SUCCESS, response, null, dataTag))
		);
		liveDataMap.put(dataTag, liveDataWrapper);
	}

	protected <T> T getData(String dataTag) {
		if (liveDataMap.get(dataTag) == null || liveDataMap.get(dataTag).getValue() == null) {
			return null;
		}
		else {
			return (T) liveDataMap.get(dataTag).getValue().data;
		}
	}

	protected abstract V getViewModel();

	protected abstract int getLoadingViewId(String dataTag);

	protected abstract int getContentViewId(String dataTag);

	protected abstract int getErrorViewId(String dataTag);

	protected abstract void onError(Throwable throwable, String dataTag);

	protected abstract void setContent(String dataTag);

}
