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

import com.example.sinhaladictionary.R;
import com.example.sinhaladictionary.Results;
import com.example.sinhaladictionary.adapters.ResultAdapter;
import com.example.sinhaladictionary.adapters.WordAdapter;
import com.example.sinhaladictionary.models.EnglishWord;
import com.example.sinhaladictionary.models.SinhalaMeaning;

import java.util.ArrayList;
import java.util.List;

public class DictionaryResultFragment extends Fragment {
    private String definitions;
    private LinearLayout spinnerContainer;
    private RecyclerView meaningRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private List<SinhalaMeaning> sinhalaMeanings;
    private ResultAdapter resultAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view =  inflater.inflate(R.layout.fragment_dictionary_result, container, false);

        definitions = getArguments().getString("definitions");
        sinhalaMeanings = convertStringToListOfMean(definitions);
        initRecycleView(view);
        return view;
    }

    private void initRecycleView(View view){
        meaningRecyclerView = view.findViewById(R.id.dic_result_sinahala_recycleview);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        meaningRecyclerView.setLayoutManager(linearLayoutManager);
        resultAdapter = new ResultAdapter(sinhalaMeanings);
        meaningRecyclerView.setAdapter(resultAdapter);
        resultAdapter.notifyDataSetChanged();
    }

    private List<SinhalaMeaning> convertStringToListOfMean(String meaningAsString){
        List<SinhalaMeaning> sinhalaMeanings = new ArrayList<>();
        String[] definitionsArr = meaningAsString.split(",");

        for(String definition: definitionsArr){
            SinhalaMeaning sinhalaMeanObj = new SinhalaMeaning(definition.replace("[" , "").replace("]","").replace("\"", ""));
            sinhalaMeanings.add(sinhalaMeanObj);
        }

        return sinhalaMeanings;
    }


}