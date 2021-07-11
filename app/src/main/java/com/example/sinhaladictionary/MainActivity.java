package com.example.sinhaladictionary;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sinhaladictionary.fragments.DictionaryFragment;
import com.example.sinhaladictionary.models.EnglishWord;
import com.example.sinhaladictionary.models.SinhalaWord;
import com.google.android.material.navigation.NavigationView;

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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.navigation_drawer_main);
        navigationView = findViewById(R.id.nev_main);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nevigation_drawer_opem, R.string.nevigation_drawer_close);

        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        mountFragment();
        getSupportActionBar().setElevation(0);

//        HashMap<String, Object> data= sinhalaDictionaryData();
//        List<SinhalaWord> dddd= (List<SinhalaWord>) data.get("sinhalaWordsArrayList");
//        for(SinhalaWord x : dddd){
//            System.out.println(x.getWord());
//        }
//
//        for(Object x : data.keySet()){
////            System.out.println(x);
////            System.out.println(data.get(x));
//            EnglishWord e= new EnglishWord(x.toString(), data.get(x));
//            dddd.add(e);
//        }
//
//        List<EnglishWord> sss = new ArrayList<>();




    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_menu, menu);
        return true;
    }

    public void mountFragment(){
        androidx.fragment.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.main_frame, new DictionaryFragment())
                .commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(this, SettingsActivity.class);
        switch (item.getItemId()){
            case R.id.drawer_dictionary:
                mountFragment();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.drawer_translator:
                drawerLayout.closeDrawer(GravityCompat.START);
                Toast.makeText(this, "translatr clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.drawer_setting:
                startActivity(intent);
                break;
        }
        return true;
    }

    public HashMap<String, Object> englishDictionaryData(){
        HashMap<String, String> englishDictionary = new HashMap<>();
        List<EnglishWord> wordsList = new ArrayList<>();
        HashMap<String, Object> data= new HashMap<>();

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
        return data;
    }

    public HashMap<String, Object> sinhalaDictionaryData(){
        HashMap<String, String> sinahalaDictionary = new HashMap<>();
        List<SinhalaWord> wordsList = new ArrayList<>();
        HashMap<String, Object> data= new HashMap<>();

        InputStream is = getResources().openRawResource(R.raw.sn2en);
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
                sinahalaDictionary.put(obj.getString("word"),obj.getString("definitions"));
                SinhalaWord sinhalaWord = new SinhalaWord(obj.getString("word"),obj.getString("definitions"));
                wordsList.add(sinhalaWord);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        data.put("sinhalaWordsArrayList", wordsList);
        data.put("sinhalaWordsHashMap", sinahalaDictionary);
        return data;
    }

}