package com.example.androidexamapp.NewsApi;

import com.example.androidexamapp.NewsModel.Headlines;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

//Interface that describes what we want to get from the News Api
//the queries define the get request "parameters"
public interface NewsApiInterface {

    //For when there is nothing searched, we just want the top headlines
    @GET("top-headlines")
    Call<Headlines> getHeadlines(
            @Query("country") String country,
            @Query("apiKey") String apiKey
    );

    //For when the user searches something, we want everything that matches our search query
    @GET("everything")
    Call<Headlines> getSpecificData(
            @Query("q") String query,
            @Query("apiKey") String apiKey
    );



}
