package com.example.myapplication;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

class homeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.homeBottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.homeBottomNavigationMenuHome);

    }

    HomeFragment homeFragment = new HomeFragment();
    SearchFragment searchFragment = new SearchFragment();
    MyTripFragment myTripFragment = new MyTripFragment();
    NotificationFragment notificationFragment = new NotificationFragment();
    MenuFragment menuFragment = new MenuFragment();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.homeBottomNavigationMenuHome) {
            getSupportFragmentManager().beginTransaction().replace(R.id.HomeFramelayout, homeFragment).commit();

        } else if (item.getItemId() == R.id.homeBottomNavigationView) {
            getSupportFragmentManager().beginTransaction().replace(R.id.HomeFramelayout, searchFragment).commit();

        } else if (item.getItemId() == R.id.homeBottomNavigationMenuTrip) {
            getSupportFragmentManager().beginTransaction().replace(R.id.HomeFramelayout, myTripFragment).commit();

        } else if (item.getItemId() == R.id.homeBottomNavigationNotification) {
            getSupportFragmentManager().beginTransaction().replace(R.id.HomeFramelayout, notificationFragment).commit();

        } else if (item.getItemId() == R.id.homeBottomNavigationMenu) {
            getSupportFragmentManager().beginTransaction().replace(R.id.HomeFramelayout, menuFragment).commit();

        }

        return true;
    }
}


