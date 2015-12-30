package com.challenge.androidchallenge.Retrofit;

import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by Ryan on 12/22/2015.
 */
public class ServiceClient {

    private static ServiceAPI SERVICE_CLIENT;
    final static String ROOT = "https://challenge2015.myriadapps.com/api/v1";

    static {
        setupServiceClient();
    }

    private ServiceClient() {}

    public static ServiceAPI get() {
        return SERVICE_CLIENT;
    }

    private static void setupServiceClient() {
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(ROOT)
                .setClient(new OkClient(new OkHttpClient()));

        RestAdapter restAdapter = builder.build();
        SERVICE_CLIENT = restAdapter.create(ServiceAPI.class);
    }
}
