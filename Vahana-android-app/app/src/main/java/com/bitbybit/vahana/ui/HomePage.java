package com.bitbybit.vahana.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.bitbybit.vahana.R;
import com.bitbybit.vahana.location.LocationService;
import com.bitbybit.vahana.utils.Shared_prefs;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class HomePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private CardView busCard, flightCard, trainCard, bookingsCard,
                     ourLocationCard, covidCard, socialDistanceCard, customerCareCard;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        findingview();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!Shared_prefs.getInstance(this).get_session_token()){
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
        if (!checkPermission()) {
            requestPermission();
        }
        startService(new Intent(this, LocationService.class));
    }

    private void findingview() {

        busCard = findViewById(R.id.busCard);
        trainCard = findViewById(R.id.trainCard);
        flightCard = findViewById(R.id.flightCard);
        bookingsCard = findViewById(R.id.bookingsCard);
        ourLocationCard = findViewById(R.id.ourlocationCard);
        covidCard = findViewById(R.id.covidCard);
        socialDistanceCard = findViewById(R.id.socialdistanceCard);
        customerCareCard = findViewById(R.id.customercareCard);

        busCard.setOnClickListener(this);
        trainCard.setOnClickListener(this);
        flightCard.setOnClickListener(this);
        bookingsCard.setOnClickListener(this);
        ourLocationCard.setOnClickListener(this);
        covidCard.setOnClickListener(this);
        socialDistanceCard.setOnClickListener(this);
        customerCareCard.setOnClickListener(this);

        drawerLayout = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        View headerView = navigationView.getHeaderView(0);

        Menu menu = navigationView.getMenu();

        MenuItem general= menu.findItem(R.id.general);
        MenuItem communicate= menu.findItem(R.id.communicate);
        SpannableString s = new SpannableString(general.getTitle());
        SpannableString s1 = new SpannableString(communicate.getTitle());
        s.setSpan(new TextAppearanceSpan(this, R.style.TitleTextAppearance44), 0, s.length(), 0);
        s1.setSpan(new TextAppearanceSpan(this, R.style.TitleTextAppearance44), 0, s1.length(), 0);
        general.setTitle(s);
        communicate.setTitle(s1);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
        } else {
            return true;
        }
        return false;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 001);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.vahanastation:
                startActivity(new Intent(this, Stations.class));
                break;
            case R.id.rate:

                break;
            case R.id.notification:

                break;
            case R.id.setting:

                break;
            case R.id.privacy:

                break;
            case R.id.logout:
                Shared_prefs.getInstance(this).clear_session();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, Search.class);
        switch (view.getId()) {
            case R.id.busCard:
                intent.putExtra("flag", "bus");
                startActivity(intent);
                break;
            case R.id.trainCard:
                intent.putExtra("flag", "train");
                startActivity(intent);
                break;
            case R.id.flightCard:
                intent.putExtra("flag", "flight");
                startActivity(intent);
                break;
            case R.id.bookingsCard:
                startActivity(new Intent(this, Bookings.class));
                break;
            case R.id.ourlocationCard:

                break;
            case R.id.covidCard:

                break;
            case R.id.socialdistanceCard:

                break;
            case R.id.customercareCard:

                break;
        }
    }
}
