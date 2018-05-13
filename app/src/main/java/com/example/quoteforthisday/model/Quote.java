package com.example.quoteforthisday.model;

import com.google.gson.annotations.SerializedName;

public class Quote {

    @SerializedName("quote")
    String text;

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
