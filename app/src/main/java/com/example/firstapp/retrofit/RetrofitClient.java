package com.example.firstapp.retrofit;

import static com.example.firstapp.utility.Const.BASE_URL;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*
  //Retrofit library
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'

    //Gson converter dependency
    implementation 'com.squareup.retrofit2:converter-gson:2.9.0'

    **/
public class RetrofitClient {
    private static Retrofit retrofit;

    public static Retrofit getRetrofitClientInterface() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .hostnameVerifier((hostname, session) -> true)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    public static DataService getDataService() {
        return getRetrofitClientInterface().create(DataService.class);
    }
}
