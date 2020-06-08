package com.example.androidexamapp.NewsModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//SerializedName describes the name in the JSON
//@Expose is used to allow or disallow serialization and deserialization
//The different variables is what we want to get from Source in the News Api

public class Source {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
