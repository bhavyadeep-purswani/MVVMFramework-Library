package com.example.sameapp.Retrofit;

import com.example.sameapp.Model.Constants;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit mRetrofit;

    public static Retrofit getClient() {
        if(mRetrofit==null) {
			HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
			interceptor.level(HttpLoggingInterceptor.Level.BODY);
			OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
            mRetrofit = new Retrofit.Builder()
					.client(client)
                    .baseUrl(Constants.API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
					.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

        }
        return mRetrofit;
    }
}
