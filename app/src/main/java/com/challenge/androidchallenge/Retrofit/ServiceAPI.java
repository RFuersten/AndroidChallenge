package com.challenge.androidchallenge.Retrofit;

import com.challenge.androidchallenge.Retrofit.POJO.DetailedKingdom;
import com.challenge.androidchallenge.Retrofit.POJO.Kingdom;
import com.challenge.androidchallenge.Retrofit.POJO.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Ryan on 12/22/2015.
 */
public interface ServiceAPI {
    @POST("subscribe")
    Call<User> registerUser(@Body User user);

    @GET("kingdoms")
    Call<List<Kingdom>> getKingdoms();

    @GET("kingdoms/{id}")
    Call<DetailedKingdom> getKingdom(@Path("id") int id);
}
