package com.example.quoteforthisday;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.quoteforthisday.api.QuoteService;
import com.example.quoteforthisday.model.Quote;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.quoteforthisday.R.id.action_reload;

public class MainActivity extends AppCompatActivity implements QuoteAdapter.ListItemClickListener {

    private RecyclerView quotesRecyclerView;
    private ProgressBar progressBar;
    Retrofit retrofit;
    QuoteService service;
    List<Quote> quoteList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRetrofitService();

        progressBar = findViewById(R.id.pb_loading_indicator);
        quotesRecyclerView = findViewById(R.id.rv_show_list);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        quotesRecyclerView.setLayoutManager(manager);

        if (QuoteHolder.cachedQuotes.isEmpty()) {
            downloadQuoutes();
        }
        else {
            quoteList = QuoteHolder.cachedQuotes;
            QuoteAdapter quotesListAdapter = new QuoteAdapter(quoteList, MainActivity.this);
            quotesRecyclerView.setAdapter(quotesListAdapter);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onListItemClick(int position) {
        String quote = quoteList.get(position).getText();
        String author = quoteList.get(position).getAuthor();
        String cat = quoteList.get(position).getCategory();
        Intent intent = new Intent(this, com.example.quoteforthisday.IndividualActivity.class);

        Bundle extras = new Bundle();
        extras.putString("QUOTE", quote);
        extras.putString("AUTHOR", author);
        extras.putString("CAT", cat);
        intent.putExtras(extras);

        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case action_reload:
                downloadQuoutes();
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    private void initRetrofitService() {
        Gson gson = new Gson().newBuilder().setLenient().create();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://talaikis.com/api/")
                .client(getClientWithInterceptor())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        service = retrofit.create(QuoteService.class);
    }

    private void downloadQuoutes() {
        progressBar.setVisibility(View.VISIBLE);
        Call<List<Quote>> qouteCall = service.getQoutes();

        qouteCall.enqueue(new Callback<List<Quote>>() {

            @Override
            public void onResponse(Call<List<Quote>> call, Response<List<Quote>> response) {
                quoteList = response.body();
                QuoteAdapter quotesListAdapter = new QuoteAdapter(quoteList, MainActivity.this);
                quotesRecyclerView.setAdapter(quotesListAdapter);

                QuoteHolder.cachedQuotes = response.body();
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<List<Quote>> call, Throwable t) {
                t.getLocalizedMessage();
            }
        });
    }

    private OkHttpClient getClientWithInterceptor() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                okhttp3.Response response = chain.proceed(chain.request());
                //   Log.w("Retrofit@Response", response.body().string().toString());
                return response;
            }
        });
        return httpClient.build();
    }
}