package com.example.quoteforthisday.api;

import com.example.quoteforthisday.model.Quote;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface QuoteService {

    @GET("?quotes=latest")
    Call<List<Quote>> getQoutes();
}
