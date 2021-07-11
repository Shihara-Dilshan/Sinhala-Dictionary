package com.example.sinhaladictionary.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sinhaladictionary.R;
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, Object> data= null;
                try {
                    data = englishDictionaryData();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                List<EnglishWord> dddd= (List<EnglishWord>) data.get("englishWordsArrayList");
                for(EnglishWord x : dddd){
                    System.out.println(x.getWord());
                    System.out.println(2);
                }
            }
        });
        thread.start();
        View view = inflater.inflate(R.layout.fragment_english_words, container, false);
        TextView textView = view.findViewById(R.id.sdsd);
        textView.setText("Sdsdsddddz");
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
                char[] buffer = new char[11024];
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
}