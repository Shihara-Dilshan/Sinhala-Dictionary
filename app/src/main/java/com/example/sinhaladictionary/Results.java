package com.example.sinhaladictionary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;

public class Results extends AppCompatActivity {

    private TextView resultWord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        getSupportActionBar().setTitle("Results");
        getSupportActionBar().setElevation(0);

        resultWord = findViewById(R.id.result_word);
        resultWord.setText(getIntent().getStringExtra("word"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.results_menu, menu);

        return true;
    }
}