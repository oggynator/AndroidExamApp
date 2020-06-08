package com.example.androidexamapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androidexamapp.Adapter.Adapter;
import com.example.androidexamapp.NewsApi.NewsApiClient;
import com.example.androidexamapp.NewsModel.Articles;
import com.example.androidexamapp.NewsModel.Headlines;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    //"If your app needs to display a scrolling list of elements based on large data sets (or data that frequently changes), you should use RecyclerView as described on this page." -Android development guide
    RecyclerView recyclerView;

    //The Swipe to refresh layout
    SwipeRefreshLayout swipeRefreshLayout;

    EditText searchEdit;
    Button searchButton;
    Dialog dialog;

    //my API key for the news app
    final String API_KEY = "cd8ebc0f40f646da93791d470096abfb";

    Adapter adapter;
    List<Articles>  articles = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        recyclerView = findViewById(R.id.recyclerView);

        searchEdit = findViewById(R.id.searchEdit);
        searchButton = findViewById(R.id.searchButton);
        dialog = new Dialog(MainActivity.this);

        //Set the layout of the content in the recycleview
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final String country = getCountry();


        //If the user refreshes --> do this (retrieve new news)
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                retrieveJson("",country,API_KEY);
            }
        });
        //Retrieves top headlines, without any query, uses default locale country (US), uses the API key stated above
        retrieveJson("",country,API_KEY);

        //Onclick for the search function.
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!searchEdit.getText().toString().equals("")){ //<-- if there is something in the searchEdit Text, the retrieveJson() method will be called with that query
                    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            retrieveJson(searchEdit.getText().toString(),country,API_KEY); //<-- new news with that query will be called on refresh too
                        }
                    });
                    retrieveJson(searchEdit.getText().toString(),country,API_KEY);
                }else{
                    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            retrieveJson("",country,API_KEY); //<-- if nothing searched, this will be called on refresh
                        }
                    });
                    retrieveJson("",country,API_KEY); //<-- Get top headlines if no search
                }
            }
        });



    }
    //Gets the actual news via the news api client
    public void retrieveJson(String query ,String country, String apiKey){

        //notifies the swipeRefreshLayout that the refresh state has been changed. (Show progress animation)
        swipeRefreshLayout.setRefreshing(true);

        //create a http request to the webserver (news api) asking to get headlines
        Call<Headlines> call;
        if (!searchEdit.getText().toString().equals("")){
            call= NewsApiClient.getInstance().getApi().getSpecificData(query,apiKey); //<-- query the webserver with the searched user input. (If there is one)
        }else{
            call= NewsApiClient.getInstance().getApi().getHeadlines(country,apiKey); //<-- get everything if no input
        }

        //Asynchronously send the request and notify callback of its response or if an error occurred talking to the server, creating the request, or processing the response.
        call.enqueue(new Callback<Headlines>() {
            @Override
            public void onResponse(Call<Headlines> call, Response<Headlines> response) { //<-- if there is a response and the response is not empty
                if (response.isSuccessful() && response.body().getArticles() != null){
                    swipeRefreshLayout.setRefreshing(false);
                    articles.clear();
                    articles = response.body().getArticles();
                    adapter = new Adapter(MainActivity.this,articles);
                    recyclerView.setAdapter(adapter);
                }
            }
            //If there is no response or the response is empty, stop refresh and creates a toast (getLocalizedMessage gets an error message from the locale specific content)
            @Override
            public void onFailure(Call<Headlines> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //This method returns default Locale set by the Java Virtual Machine. This is static method so it can be called without creating object of the class Locale.
    public static String getCountry(){
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        return country.toLowerCase();
    }

    //Goes to the account page
    public void onAccountPressed(View view){
        Intent intent = new Intent(MainActivity.this, AccountActivity.class);
        startActivity(intent);
    }


}
