package com.example.quoteforthisday.model;

import com.google.gson.annotations.SerializedName;

public class Quote {

    @SerializedName("quote")
    String text;

    @SerializedName("author")
    String author;

    @SerializedName("cat")
    String category;

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
        public String getAuthor() {return author; }
    public String getCategory() {return category; }
}
