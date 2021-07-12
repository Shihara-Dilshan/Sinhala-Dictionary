package com.example.sinhaladictionary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;

import com.example.sinhaladictionary.fragments.DictionaryResultFragment;

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

        String definitions = getIntent().getStringExtra("definitions");
        //String[] definitionsArr = definitions.split(",");

//        for(String definition: definitionsArr){
//            System.out.println(definition.replace("[" , "").replace("]","").replace("\"", ""));
//        }

        displayOfflineDictionaryResult(definitions);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.results_menu, menu);
        return true;
    }

    public void displayOfflineDictionaryResult(String definitions){
        Bundle bundle = new Bundle();
        DictionaryResultFragment dictionaryResultFragment = new DictionaryResultFragment();
        dictionaryResultFragment.setArguments(bundle);

        bundle.putString("definitions", definitions);
        androidx.fragment.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.result_screen, dictionaryResultFragment)
                .commit();
    }
}