package com.example.sinhaladictionary.fragments;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.sinhaladictionary.R;

public class DictionaryFragment extends Fragment implements View.OnClickListener  {
    Button englishBtn, sinhalaBtn;
    Resources res;
    Drawable btnSelected, btnNotSelected;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_dictionary, container, false);
        englishBtn = view.findViewById(R.id.btn_english);
        sinhalaBtn = view.findViewById(R.id.btn_sinhala);
        res = getResources();

        btnSelected = ResourcesCompat.getDrawable(res, R.drawable.button_selected, null);
        btnNotSelected = ResourcesCompat.getDrawable(res, R.drawable.button_not_selected, null);

        englishBtn.setOnClickListener(this);
        sinhalaBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_english:
                englishBtn.setBackground(btnSelected);
                englishBtn.setTextColor(Color.parseColor("#ffffff"));
                sinhalaBtn.setTextColor(Color.parseColor("#CFCECE"));
                sinhalaBtn.setBackground(btnNotSelected);
                break;
            case R.id.btn_sinhala:
                sinhalaBtn.setBackground(btnSelected);
                englishBtn.setBackground(btnNotSelected);
                sinhalaBtn.setTextColor(Color.parseColor("#ffffff"));
                englishBtn.setTextColor(Color.parseColor("#CFCECE"));
                break;
        }
    }
}