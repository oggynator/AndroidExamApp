package com.example.androidexamapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.androidexamapp.DetailedActivity;
import com.example.androidexamapp.NewsModel.Articles;
import com.example.androidexamapp.R;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

//Creates a view for the items in the recycleview
public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    //used to get the context
    private Context context;

    //Create a list containing article objects
    private List<Articles> articles;


    public Adapter(Context context, List<Articles> articles) {
        this.context = context;
        this.articles = articles;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items,parent,false);
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    // - get element from the dataset at this position
    // - replace the contents of the view with that element

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {


        final Articles a = articles.get(position);

        String imageUrl = a.getUrlToImage();
        String url = a.getUrl();

        Glide.with(context).load(imageUrl).into(holder.imageView);
       // Picasso.with(context).load(imageUrl).into(holder.imageView); <-- CRASHES THE APP IF HARDWARE ACCELERATION IS ON, i think it is due to the images being to large

        holder.newsTitle.setText(a.getTitle());
        holder.newsSource.setText(a.getSource().getName());
        holder.newsDate.setText("\u2022"+dateTime(a.getPublishedAt()));

        //Adds a onlick for each item, when a news article is clicked, it open the detailed activity class and proved the class with data
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailedActivity.class);
                intent.putExtra("title",a.getTitle());
                intent.putExtra("source",a.getSource().getName());
                intent.putExtra("time",dateTime(a.getPublishedAt()));
                intent.putExtra("desc",a.getDescription());
                intent.putExtra("imageUrl",a.getUrlToImage());
                intent.putExtra("url",a.getUrl());
                context.startActivity(intent);
            }
        });

    }

    //return the number of articles
    @Override
    public int getItemCount() {
        return articles.size();
    }

    // Provide a reference to the views for each data item
    //we create variables for each thing that need to be updated every data item
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView newsTitle,newsSource,newsDate;
        ImageView imageView;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            newsTitle = itemView.findViewById(R.id.newsTitle);
            newsSource = itemView.findViewById(R.id.newsSource);
            newsDate = itemView.findViewById(R.id.newsDate);
            imageView = itemView.findViewById(R.id.image);
            cardView = itemView.findViewById(R.id.cardView);

        }
    }

    //Using the Prettytime time formatting library, we can get the publish time from the News Api and then print it as "X hours ago".
    public String dateTime(String t){
        PrettyTime prettyTime = new PrettyTime(new Locale(getCountry()));
        String time = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:",Locale.ENGLISH);
            Date date = simpleDateFormat.parse(t);
            time = prettyTime.format(date);
        }catch (ParseException e) {
            e.printStackTrace();
        }
        return time;

    }

    //This method returns default Locale set by the Java Virtual Machine. This is static method so it can be called without creating object of the class Locale.
    public String getCountry(){
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        return country.toLowerCase();
    }
}
