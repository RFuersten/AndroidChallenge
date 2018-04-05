package com.challenge.androidchallenge.Retrofit;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ryan on 12/22/2015.
 */
public class ServiceClient {

    private static ServiceAPI SERVICE_CLIENT;
    final static String ROOT = "https://challenge2015.myriadapps.com/api/v1/";

    static {
        setupServiceClient();
    }

    private ServiceClient() {}

    public static ServiceAPI get() {
        return SERVICE_CLIENT;
    }

    private static void setupServiceClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ROOT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SERVICE_CLIENT = retrofit.create(ServiceAPI.class);
    }
}
