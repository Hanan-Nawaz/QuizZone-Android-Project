package com.example.quizzone.Fragments;

import android.os.Bundle;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.quizzone.R;

public class login_with extends Fragment {

    TextView btn;

    public login_with() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_loginwith, container, false);

        btn = view.findViewById(R.id.tv_btn_auth);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup_with signupWith = new signup_with();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.hide(login_with.this);
                fragmentTransaction.add(R.id.fragment_viewer, signupWith);
                fragmentTransaction.commit();
            }
        });

        return view;


    }
}