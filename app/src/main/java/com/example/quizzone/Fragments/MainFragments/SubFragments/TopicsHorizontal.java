package com.example.quizzone.Fragments.MainFragments.SubFragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.quizzone.Classes.AdapterClasses.DashboardTopicsAdapter;
import com.example.quizzone.Classes.AddTopics;
import com.example.quizzone.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class TopicsHorizontal extends Fragment {

    RecyclerView RV;
    ArrayList<AddTopics> addTopicsArrayList;
    DashboardTopicsAdapter dashboardTopicsAdapter;
    ProgressBar TopicsBar;

    public TopicsHorizontal() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_topics_horizontal, container, false);
        RV = view.findViewById(R.id.TopicRVGR);
        TopicsBar = view.findViewById(R.id.TopicsLoading);
        RV.setLayoutManager(new LinearLayoutManager( getContext(),LinearLayoutManager.HORIZONTAL, false));
        addTopicsArrayList = new ArrayList<>();
        dashboardTopicsAdapter = new DashboardTopicsAdapter(addTopicsArrayList);
        RV.setAdapter(dashboardTopicsAdapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference colRef = db.collection("Topics");

        colRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list  = queryDocumentSnapshots.getDocuments();
                for(DocumentSnapshot doc : list){
                    AddTopics addTopics = doc.toObject(AddTopics.class);
                    addTopicsArrayList.add(addTopics);
                }

                 dashboardTopicsAdapter.notifyDataSetChanged();


            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                     TopicsBar.setVisibility(View.GONE);
            }
        }, 6000);

        return  view;
    }
}