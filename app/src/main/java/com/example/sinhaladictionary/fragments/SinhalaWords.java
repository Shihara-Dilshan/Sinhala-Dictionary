package com.example.sinhaladictionary.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.sinhaladictionary.R;
import com.example.sinhaladictionary.Results;
import com.example.sinhaladictionary.adapters.WordAdapter;
import com.example.sinhaladictionary.models.EnglishWord;
import com.example.sinhaladictionary.models.SinhalaMeaning;
import com.example.sinhaladictionary.models.SinhalaWord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SinhalaWords extends Fragment {
    private LinearLayout spinnerContainer;
    private RecyclerView sinhalaWordRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private List<EnglishWord> sinhalaWordlist;
    private WordAdapter wordAdapter;
    private static SinhalaWords sinhalaWords = null;


    public static SinhalaWords getSinhalaWordObj(){
        if(sinhalaWords == null){
            sinhalaWords = new SinhalaWords();
        }
        return sinhalaWords;
    }

    private SinhalaWords(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sinhala_words, container, false);


        HashMap<String, Object> data= null;
        try {
            data = sinhalaDictionaryData();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sinhalaWordlist = (List<EnglishWord>) data.get("sinhalaWordsArrayList");
        initRecycleView(view);
        return view;
    }

    public HashMap<String, Object> sinhalaDictionaryData() throws InterruptedException {
        HashMap<String, String> sinhalaDictionary = new HashMap<>();
        List<EnglishWord> wordsList = new ArrayList<>();
        HashMap<String, Object> data= new HashMap<>();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                InputStream is = getResources().openRawResource(R.raw.sn2en);
                Writer writer = new StringWriter();
                char[] buffer = new char[1024];
                try{
                    Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                    int n;
                    while((n = reader.read(buffer)) != -1){
                        writer.write(buffer, 0, n);
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }finally {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                String jsoString = writer.toString();
                try{
                    JSONArray jsonArray =new JSONArray(jsoString);
                    for(int i=0; i<jsonArray.length(); i++){
                        JSONObject obj = (JSONObject) jsonArray.get(i);
                        sinhalaDictionary.put(obj.getString("word"),obj.getString("definitions"));
                        EnglishWord sinhalaWord = new EnglishWord(obj.getString("word"),obj.getString("definitions"));
                        wordsList.add(sinhalaWord);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                data.put("sinhalaWordsArrayList", wordsList);
                data.put("sinhalaWordsHashMap", sinhalaDictionary);
            }
        });
        thread.start();
        thread.join();
        return data;
    }

    private void initRecycleView(View view){
        sinhalaWordRecyclerView = view.findViewById(R.id.sinhala_words_container);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        sinhalaWordRecyclerView.setLayoutManager(linearLayoutManager);
        wordAdapter = new WordAdapter(sinhalaWordlist, new WordAdapter.ItemClickListener() {
            @Override
            public void onItemClick(EnglishWord englishWord) {
                Intent intent = new Intent(getContext(), Results.class);
                intent.putExtra("word", englishWord.getWord());
                intent.putExtra("definitions", englishWord.getDefinition());
                startActivity(intent);
            }
        });
        sinhalaWordRecyclerView.setAdapter(wordAdapter);
        wordAdapter.notifyDataSetChanged();
    }

    public void myFilter(String searchInput){
        wordAdapter.getFilter().filter(searchInput);
    }

}