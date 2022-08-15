package com.example.quizzone.Activities.Menu;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.quizzone.Activities.SignInSignUp.AuthActivity;
import com.example.quizzone.Fragments.MainFragments.AddTopic;
import com.example.quizzone.Fragments.MainFragments.Dashboard;
import com.example.quizzone.Fragments.MainFragments.Profile;
import com.example.quizzone.Fragments.MainFragments.UsersList;
import com.example.quizzone.Fragments.MainFragments.ViewTopics;
import com.example.quizzone.R;
import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    NavigationView navigationView;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    TextView UserEmail;
    TextView UserName;
    CircleImageView IVHeader;
    ProgressBar headerPBAr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        navigationView = findViewById(R.id.NavigationView);

        View header = navigationView.getHeaderView(0);

        UserEmail = header.findViewById(R.id.UserEmail);
        UserName = header.findViewById(R.id.UserName);
        IVHeader = header.findViewById(R.id.headerIV);
        headerPBAr = header.findViewById(R.id.headerPBar);


            String email = getIntent().getStringExtra("Email");
            String name = getIntent().getStringExtra("Name");
            String Role = getIntent().getStringExtra("Role");
            String Image = getIntent().getStringExtra("Image");
            UserName.setText(name);
            UserEmail.setText(email);




        if(savedInstanceState == null){
            Bundle bundle = new Bundle();
            bundle.putString("Email", email );
            bundle.putString("Name", name );
            Dashboard dashboard = new Dashboard();
            dashboard.setArguments(bundle);
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
        navigationView.setCheckedItem(R.id.home);

        if(Role.equals("1")){
            navigationView.getMenu().findItem(R.id.addTopic).setVisible(false);
            navigationView.getMenu().findItem(R.id.userList).setVisible(false);
        }
        else if(Role.equals("2")){
            navigationView.getMenu().findItem(R.id.addTopic).setVisible(true);
            navigationView.getMenu().findItem(R.id.userList).setVisible(false);
        }



        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case (R.id.home):{
                        getSupportActionBar().setTitle("Dashboard");
                        Bundle bundle = new Bundle();
                        bundle.putString("Email", email );
                        bundle.putString("Name", name );
                        Dashboard dashboard = new Dashboard();
                        dashboard.setArguments(bundle);
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.Main, dashboard);
                        fragmentTransaction.commit();
                        break;
                    }

                    case (R.id.profile):{
                        getSupportActionBar().setTitle("Profile");
                        Bundle bundle = new Bundle();
                        bundle.putString("Email" , email);
                        Profile profile = new Profile();
                        profile.setArguments(bundle);
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.Main, profile);
                        fragmentTransaction.commit();
                        break;
                    }

                    case (R.id.addTopic):{
                        getSupportActionBar().setTitle("Add Topic");
                        Bundle bundle = new Bundle();
                        bundle.putString("Email" , email);
                        bundle.putString("Name" , name);
                        AddTopic addTopic = new AddTopic();
                        addTopic.setArguments(bundle);
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.Main, addTopic);
                        fragmentTransaction.commit();
                        break;
                    }

                    case (R.id.viewTopics):{
                        getSupportActionBar().setTitle("View Topics");
                        Bundle bundle = new Bundle();
                        bundle.putString("Email", email );
                        bundle.putString("Name", name );
                        ViewTopics viewTopics = new ViewTopics();
                        viewTopics.setArguments(bundle);
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.Main, viewTopics);
                        fragmentTransaction.commit();
                        break;
                    }

                    case (R.id.userList):{
                        getSupportActionBar().setTitle("User's List");
                        Bundle bundle = new Bundle();
                        bundle.putString("Email" , email);
                        bundle.putString("Name" , name);
                        UsersList usersList = new UsersList();
                        usersList.setArguments(bundle);
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.Main, usersList);
                        fragmentTransaction.commit();
                        break;
                    }

                    case (R.id.logout):{
                        finishAffinity();
                        Intent intent = new Intent(getApplicationContext(), AuthActivity.class);
                        startActivity(intent);
                        break;
                    }


                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        try {
            URL url = new URL(Image);
            Bitmap bMAp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            IVHeader.setImageBitmap(bMAp);
            headerPBAr.setVisibility(View.GONE);
            IVHeader.setVisibility(View.VISIBLE);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        onBackPressed();

    }

    @Override
    public void onBackPressed() {
        drawerLayout.closeDrawer(GravityCompat.START);
    }


}