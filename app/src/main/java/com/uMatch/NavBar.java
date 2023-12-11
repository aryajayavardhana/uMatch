package com.uMatch;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NavBar extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_bar);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.btnhome);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.btnhome:
                kehome();
                break;
            case R.id.btnchat:
                kechat();
                break;
            case R.id.btnshop:
                keshop();
                break;
            case R.id.btnprofile:
                keProfile();
                break;
        }
        return true;
    }



    private void kehome(){
        Home bukahome = new Home();

        FragmentTransaction fragtrans = getSupportFragmentManager().beginTransaction();
        Bundle isi = new Bundle();
        //isi.putString("nama", nama);
        // isi.putString("email", email);

        bukahome.setArguments(isi);
        fragtrans.replace(R.id.framemain, bukahome).commit();
    }

    private void kechat(){
        chat bukachat = new chat();

        FragmentTransaction fragtrans = getSupportFragmentManager().beginTransaction();
        Bundle isi = new Bundle();
        //isi.putString("nama", nama);
        // isi.putString("email", email);

        bukachat.setArguments(isi);
        fragtrans.replace(R.id.framemain, bukachat).commit();
    }

    private void keshop(){
         shop bukashop = new shop();

        FragmentTransaction fragtrans = getSupportFragmentManager().beginTransaction();
        Bundle isi = new Bundle();
        //isi.putString("nama", nama);
        // isi.putString("email", email);

        bukashop.setArguments(isi);
        fragtrans.replace(R.id.framemain, bukashop).commit();
    }

    private void keProfile(){
        profile bukapro = new profile();

        FragmentTransaction fragtrans = getSupportFragmentManager().beginTransaction();
        Bundle isi = new Bundle();
        //isi.putString("nama", nama);
       // isi.putString("email", email);

        bukapro.setArguments(isi);
        fragtrans.replace(R.id.framemain, bukapro).commit();
    }
}