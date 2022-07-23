package com.example.quizzone.Fragments.AuthFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.quizzone.Activities.SignInSignUp.AuthActivity;
import com.example.quizzone.R;
import com.google.firebase.firestore.FirebaseFirestore;

public class Signup_with_email extends Fragment {

    Button back;

    FirebaseFirestore db;

    public Signup_with_email() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup_with_email, container, false);

        back = view.findViewById(R.id.Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AuthActivity.class);
                startActivity(intent);
            }
        });

        db = FirebaseFirestore.getInstance();


        return view;
    }
}