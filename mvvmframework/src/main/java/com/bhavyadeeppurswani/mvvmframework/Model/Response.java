package com.bhavyadeeppurswani.mvvmframework.Model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/*
 * Created by bhavyadeeppurswani on 14/04/20.
 */
public class Response<V> {

	@NonNull
	public final States state;

	@Nullable
	public final V data;

	@Nullable
	public final Throwable error;

	@NonNull
	public final String dataTag;

	public Response(@NonNull States state, @Nullable V data, @Nullable Throwable error, @NonNull String dataTag) {
		this.state = state;
		this.data = data;
		this.error = error;
		this.dataTag = dataTag;
	}

}
