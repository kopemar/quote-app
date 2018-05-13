package com.example.quoteforthisday;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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


public class MainActivity extends AppCompatActivity implements QuoteAdapter.ListItemClickListener {

    //TODO: 1.Add authors to quotes

    /**
     * You can see the response which we download here: https://talaikis.com/api/quotes/
     * If you look at it, you can easily understand how to change Quote class so it will contains also the author.
     *
     */

    //TODO: 2. Add also category variable to quotes and display both author and category texts on quote detail screen
    //TODO: 3. Format the code - it is messy - there is a plenty of blank lines and spaces
    //TODO: 4. Enhance the app design - make it even more beautiful - f.e. add "Ripple background" to items, change font etc.
    //TODO: 5. There is a multiple quotes for one category. Sort the items to be displayed grouped in categories on main screen - it should be done inside QouteAdapter
    //TODO: 6. (May be challenge :) ) - If you sort the items to categories you cann also add header for each group in recycler view.
    //TODO: 7. Find some GIT/Github tutorials and create pull request on your master branch on github. Dont merge it, I will do a code review for you :)
    //TODO: 8. Add more cool function to app :)

    /*
     * Jak k tomu pridat autory?
     * */
    String[] listOfItems = {
            "Hodně budeš někde.",
            "Odpouštěj svým nepřátelům - ničím je nenaštveš víc.",
            "Podepsat knížku od vyžírky Kmenty? #nasratjako",
            "To nebude fungovat.",
            "John Lennon once said: Life is what happens when you're busy making other plans.",
            "Manon je motýl\nManon je včela\nManon je růže vhozená do kostela\nManon je vše, co neztratí nikdy svůj pel\nManon je motýl, který mi uletěl",
            "Za tatalitě bylo to zakázaný.",
            "Já tady vidím velký špatný",
            "Vy někomu sdělujete své příjmy? Sorry jako.",
            "Běda rusalko bledá!",
            "Hello world.",
            "Toto je možné jen v Rusku! Neuvěříte vlastním očím.",
            "Stařec je stařec a moře je moře.",
            "Nezlobte se na mě, já mám 39 horečku.",
            "Já jsem tři dny nespal.",
            "Úloha osobnosti v dějinách sestává prakticky z ochoty dotyčné osobnosti zemřít nebo nechat se zabít dříve, než stačila odvolat.",
            "Přílišná oddanost ideálům končívá tím, že v rámci boje za potírání alkoholismu zastřelíš dítě se sáčkem rumových pralinek."
    };
    RecyclerView quotesRecyclerView;
    Retrofit retrofit;
    QuoteService service;
    List<Quote> quoteList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRetrofitService();
        downloadQuoutes();
        quotesRecyclerView = findViewById(R.id.rv_show_list);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        quotesRecyclerView.setLayoutManager(manager);

    }

    @Override
    public void onListItemClick(int position) {
        Intent intent = new Intent(this, com.example.quoteforthisday.IndividualActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, listOfItems[position]);
        startActivity(intent);
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
        Call<List<Quote>> qouteCall = service.getQoutes();

        qouteCall.enqueue(new Callback<List<Quote>>() {


            @Override
            public void onResponse(Call<List<Quote>> call, Response<List<Quote>> response) {
                quoteList = response.body();
                QuoteAdapter quotesListAdapter = new QuoteAdapter(quoteList, MainActivity.this);
                quotesRecyclerView.setAdapter(quotesListAdapter);

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