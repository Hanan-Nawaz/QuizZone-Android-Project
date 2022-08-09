package com.example.quizzone.Fragments.MainFragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quizzone.Classes.AdapterClasses.ViewTopicAdapterClass;
import com.example.quizzone.Classes.AddTopics;
import com.example.quizzone.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewTopics extends Fragment {

    RecyclerView ViewTopicsRV;
    ArrayList<AddTopics> addTopic ;
    String name, image;
    ViewTopicAdapterClass viewTopicAdapterClass;




    public ViewTopics() {
        // Required empty public constructor
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_topics, container, false);
        ViewTopicsRV = view.findViewById(R.id.recyclerview);
        ViewTopicsRV.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference colRef = db.collection("Topics");
        addTopic = new ArrayList<>();
        viewTopicAdapterClass = new ViewTopicAdapterClass(addTopic);

        ViewTopicsRV.setAdapter(viewTopicAdapterClass);

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Loading..");
        progressDialog.show();

        colRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list =  queryDocumentSnapshots.getDocuments();
                for(DocumentSnapshot d : list){
                    AddTopics addTopics = d.toObject(AddTopics.class);
                    addTopic.add(addTopics);
                }
                viewTopicAdapterClass.notifyDataSetChanged();
                progressDialog.cancel();
            }

        });



        return view;
    }

}