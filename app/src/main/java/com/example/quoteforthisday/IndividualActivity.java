package com.example.quoteforthisday;

import android.content.Intent;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class IndividualActivity extends AppCompatActivity {
    final static String QUOTE_OF_THE_DAY_HASHTAG = " #CitatNaDnesek";
    final static String WHO_GENERATED_IT = " Vygenerovala nejhorší citátová aplikace všech dob.";
    String extraText;


    @Override
    public boolean onNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getActionBar() != null) getActionBar().setDisplayHomeAsUpEnabled(true);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual);

        TextView showQuote = findViewById(R.id.tv_individual_activity);
        Intent intentThatStartedThis = getIntent();

        // TODO: This can be simplified to one IF
        if (intentThatStartedThis != null) {
            if (intentThatStartedThis.hasExtra(Intent.EXTRA_TEXT)) {
                extraText = intentThatStartedThis.getStringExtra(Intent.EXTRA_TEXT);
                showQuote.setText(extraText);
            }

        }


    }
    private Intent shareQuote() {
        Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setChooserTitle("Sharing options")
                .setText(extraText + QUOTE_OF_THE_DAY_HASHTAG + WHO_GENERATED_IT)
                .getIntent();
        return shareIntent;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_individual, menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);
        menuItem.setIntent(shareQuote());
        return true;
    }
}
