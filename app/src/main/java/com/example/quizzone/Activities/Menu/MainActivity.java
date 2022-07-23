package com.example.quizzone.Activities.Menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.quizzone.Fragments.MainFragments.Dashboard;
import com.example.quizzone.R;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    NavigationView navigationView;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    TextView UserEmail;
    TextView UserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        navigationView = findViewById(R.id.NavigationView);

        View header = navigationView.getHeaderView(0);

        UserEmail = header.findViewById(R.id.UserEmail);
        UserName = header.findViewById(R.id.UserName);


            String email = getIntent().getStringExtra("Email");
            String name = getIntent().getStringExtra("Name");
            UserName.setText(name);
            UserEmail.setText(email);


        if(savedInstanceState == null){
            Bundle bundle = new Bundle();
            bundle.putInt("15", 0);
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.Main, Dashboard.class, bundle).commit();
        }




        toolbar = findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.DrawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case (R.id.home):{
                        Dashboard dashboard = new Dashboard();
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.add(R.id.Main, dashboard);
                        fragmentTransaction.commit();
                        break;
                    }


                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

    }

}