package com.example.sinhaladictionary.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sinhaladictionary.MainActivity;
import com.example.sinhaladictionary.R;
import com.example.sinhaladictionary.Results;
import com.example.sinhaladictionary.adapters.WordAdapter;
import com.example.sinhaladictionary.models.EnglishWord;

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


public class EnglishWords extends Fragment {
    private LinearLayout spinnerContainer;
    private RecyclerView englishWordRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private List<EnglishWord> englishWordlist;
    private static EnglishWords englishWords = null;

    private WordAdapter wordAdapter;

    private EnglishWords() {
    }

    public static EnglishWords getEnglishWordObj(){
        synchronized (EnglishWords.class){
            if(englishWords == null){
                englishWords = new EnglishWords();
            }
            return englishWords;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_english_words, container, false);
        spinnerContainer = view.findViewById(R.id.spinner_container);
        englishWordRecyclerView = view.findViewById(R.id.recyclerViewEnglishWords);

        HashMap<String, Object> data= null;
        try {
            data = englishDictionaryData();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        englishWordlist = (List<EnglishWord>) data.get("englishWordsArrayList");
        spinnerContainer.setVisibility(View.GONE);
        englishWordRecyclerView.setVisibility(View.VISIBLE);

        initRecycleView(view);

        return view;
    }

    public HashMap<String, Object> englishDictionaryData() throws InterruptedException {
        HashMap<String, String> englishDictionary = new HashMap<>();
        List<EnglishWord> wordsList = new ArrayList<>();
        HashMap<String, Object> data= new HashMap<>();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                InputStream is = getResources().openRawResource(R.raw.en2sn);
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
                        englishDictionary.put(obj.getString("word"),obj.getString("definitions"));
                        EnglishWord englishWord = new EnglishWord(obj.getString("word"),obj.getString("definitions"));
                        wordsList.add(englishWord);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                data.put("englishWordsArrayList", wordsList);
                data.put("englishWordsHashMap", englishDictionary);
            }
        });
        thread.start();
        thread.join();
        return data;
    }

    private void initRecycleView(View view){
        englishWordRecyclerView = view.findViewById(R.id.recyclerViewEnglishWords);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        englishWordRecyclerView.setLayoutManager(linearLayoutManager);
        wordAdapter = new WordAdapter(englishWordlist, new WordAdapter.ItemClickListener() {
            @Override
            public void onItemClick(EnglishWord englishWord) {
                Intent intent = new Intent(getContext(), Results.class);
                intent.putExtra("word", englishWord.getWord());
                intent.putExtra("definitions", englishWord.getDefinition());
                startActivity(intent);
            }
        });
        englishWordRecyclerView.setAdapter(wordAdapter);
        wordAdapter.notifyDataSetChanged();
    }

    public void myFilter(String searchInput){
        wordAdapter.getFilter().filter(searchInput);
    }
}