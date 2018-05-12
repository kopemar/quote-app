package com.example.quoteforthisday;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


public class MainActivity extends AppCompatActivity implements QuoteAdapter.ListItemClickListener {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        quotesRecyclerView = findViewById(R.id.rv_show_list);
        QuoteAdapter quotesListAdapter = new QuoteAdapter(listOfItems,this );
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        quotesRecyclerView.setLayoutManager(manager);

        quotesRecyclerView.setAdapter(quotesListAdapter);
    }

    @Override
    public void onListItemClick(int position) {
        Intent intent = new Intent(this, com.example.quoteforthisday.IndividualActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, listOfItems[position]);
        startActivity(intent);
    }
}