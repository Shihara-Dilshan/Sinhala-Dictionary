package com.example.sinhaladictionary;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.sinhaladictionary.adapters.WordAdapter;
import com.example.sinhaladictionary.fragments.DictionaryFragment;
import com.example.sinhaladictionary.fragments.EnglishWords;
import com.example.sinhaladictionary.fragments.SinhalaWords;
import com.example.sinhaladictionary.fragments.TranslatorFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private int menuResource;
    private DictionaryFragment dictionaryFragment;

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
        menuResource = R.menu.search_menu;
        dictionaryFragment = new DictionaryFragment();
        mountDictionaryFragment();
        getSupportActionBar().setElevation(0);

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
    public boolean onCreateOptionsMenu(Menu menu)  {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(menuResource, menu);


        try{
            MenuItem menuItem = menu.findItem(R.id.searchBaricon);
            SearchView searchView = (SearchView)menuItem.getActionView();
            searchView.setQueryHint("Search...");

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    System.out.println("sumbit");
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    try{
                        EnglishWords.getEnglishWordObj().myFilter(newText);
                        SinhalaWords.getSinhalaWordObj().myFilter(newText);
                    }catch (NullPointerException e){

                    }

                    return false;
                }
            });
        }catch(NullPointerException NPE){

        }


        return true;
    }

    public void mountDictionaryFragment(){
        androidx.fragment.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.main_frame, new DictionaryFragment())
                .commit();
    }

    public void mountTranslatorFragment(){
        androidx.fragment.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.main_frame, new TranslatorFragment())
                .commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(this, SettingsActivity.class);
        switch (item.getItemId()){
            case R.id.drawer_dictionary:
                mountDictionaryFragment();
                drawerLayout.closeDrawer(GravityCompat.START);
                menuResource = R.menu.search_menu;
                break;
            case R.id.drawer_translator:
                mountTranslatorFragment();
                drawerLayout.closeDrawer(GravityCompat.START);
                menuResource = R.menu.translator_menu;
                break;
            case R.id.drawer_setting:
                startActivity(intent);
                break;
            case R.id.drawer_about:
                drawerLayout.closeDrawer(GravityCompat.START);
                openDialog();
                break;
            case R.id.drawer_share:
                drawerLayout.closeDrawer(GravityCompat.START);
                openShareOptions();
                break;
            case R.id.drawer_like_us:
                drawerLayout.closeDrawer(GravityCompat.START);
                shareOnFacebook();
                break;
            case R.id.drawer_rate:
                openPlayStore();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
        }
        return true;
    }

    private void openDialog() {
        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.95);
        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.95);

        final Dialog dialog = new Dialog(this); // Context, this, etc.
        dialog.setContentView(R.layout.about_diloag_box);
        dialog.setTitle("About");
        dialog.getWindow().setLayout(width, height);
        dialog.show();
    }

    private void openShareOptions(){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String messageBody = "My body";
        String subject = "Sinhala Dictionary clone created by Shihara Dilshan";
        intent.putExtra(Intent.EXTRA_SUBJECT, messageBody);
        intent.putExtra(Intent.EXTRA_TEXT, subject);
        startActivity(intent);
    }

    private void shareOnFacebook(){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com"));
        startActivity(intent);
    }

    private void openPlayStore(){
        final String appPackageName = getPackageName();
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }
}