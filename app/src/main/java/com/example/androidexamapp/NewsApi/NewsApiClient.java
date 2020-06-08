package com.example.androidexamapp.NewsApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsApiClient {


    //RETROFIT
    /*
    * Retrofit is a REST Client for Java and Android.
    * It makes it relatively easy to retrieve and upload JSON (or other structured data) via a REST based webservice.
    * We use GSon (a Google java library) to serialize and deserialize java objects/Json
    * In this app we only deserialize from the API
     * */

    //The url that we want to get the API from
    private static final String BASE_URL = "https://newsapi.org/v2/";

    //NewsApiClient
    private static NewsApiClient newsApiClient;

    //retrofit
    private static Retrofit retrofit;

    //This enables us to get the API via GsonConverterFactory which deserialize json code for us to be used in other classes.
    private NewsApiClient(){
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
    }

    //method that returns an instance of this class.
    public static synchronized NewsApiClient getInstance(){
        if (newsApiClient == null){
            newsApiClient = new NewsApiClient();
        }
        return newsApiClient;
    }

    //The Retrofit class generates an implementation of the NewsApiInterface interface.
    public NewsApiInterface getApi(){
        return retrofit.create(NewsApiInterface.class);
    }
}
