package com.example.quizzone.Fragments.MainFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.quizzone.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

public class Dashboard extends Fragment {

    TextView test;
    TextView user;
    TextView subject;
    TextView name;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public Dashboard() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        String Name = this.getArguments().getString("Name");
        String Email = this.getArguments().getString("Email");

        test = view.findViewById(R.id.TestTaken);
        user = view.findViewById(R.id.Users);
        subject = view.findViewById(R.id.Subjects);
        name = view.findViewById(R.id.DashUserNAme);

        name.setText(Name);


        return view;
    }
}