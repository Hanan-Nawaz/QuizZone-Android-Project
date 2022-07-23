package com.example.quizzone.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.quizzone.R;

public class signup_with extends Fragment {


    Button btn;

    public signup_with() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup_with, container, false);

        btn = view.findViewById(R.id.btn_auth);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login_with loginWith = new login_with();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.hide(signup_with.this);
                fragmentTransaction.add(R.id.fragment_viewer, loginWith);
                fragmentTransaction.commit();
            }
        });

        return view;
    }
}