package com.example.quizzone.Activities.SignInSignUp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import android.os.Bundle;

import com.example.quizzone.Fragments.login_with;
import com.example.quizzone.R;

public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        getSupportActionBar().hide();

        if(savedInstanceState == null){
            Bundle bundle = new Bundle();
            bundle.putInt("15", 0);
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_viewer , login_with.class, bundle).commit();

        }

    }
}