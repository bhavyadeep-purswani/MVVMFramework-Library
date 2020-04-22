package com.bhavyadeeppurswani.mvvmframework.BaseViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.HashMap;

/*
 * Created by bhavyadeeppurswani on 14/04/20.
 */
public abstract class BaseMVVMViewModel<T> extends AndroidViewModel {

	public BaseMVVMViewModel(@NonNull Application application) {
		super(application);
	}

	public abstract HashMap<String, T> getInitialLoadData();
}
