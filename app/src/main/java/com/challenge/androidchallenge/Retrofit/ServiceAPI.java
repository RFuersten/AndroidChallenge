package com.challenge.androidchallenge.Retrofit;

import com.challenge.androidchallenge.Retrofit.POJO.DetailedKingdom;
import com.challenge.androidchallenge.Retrofit.POJO.Kingdom;
import com.challenge.androidchallenge.Retrofit.POJO.User;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by Ryan on 12/22/2015.
 */
public interface ServiceAPI {
    @POST("/subscribe")
    void registerUser(@Body User user, Callback<User> callback);

    @GET("/kingdoms")
    void getKingdoms(Callback<List<Kingdom>> callback);

    @GET("/kingdoms/{id}")
    void getKingdom(@Path("id") int id, Callback<DetailedKingdom> callback);
}
