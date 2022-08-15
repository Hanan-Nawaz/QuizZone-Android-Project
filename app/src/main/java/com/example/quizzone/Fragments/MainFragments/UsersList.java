package com.example.quizzone.Fragments.MainFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.quizzone.Classes.AdapterClasses.UserListAdapterClass;
import com.example.quizzone.Classes.Insert;
import com.example.quizzone.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class UsersList extends Fragment {

    RecyclerView userListRV;
    UserListAdapterClass adapterClass;
    ArrayList<Insert> arrayList;
    ProgressBar userListPBar;

    public UsersList() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users_list, container, false);

        userListRV = view.findViewById(R.id.userListRV);
        userListPBar = view.findViewById(R.id.userListPBar);
        userListRV.setLayoutManager(new LinearLayoutManager(getContext()));
        arrayList = new ArrayList<>();
        adapterClass = new UserListAdapterClass(arrayList);
        userListRV.setAdapter(adapterClass);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference colRef = db.collection("Users");

        colRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> documentSnapshotArrayList = queryDocumentSnapshots.getDocuments();

                for(DocumentSnapshot d : documentSnapshotArrayList){
                    Insert insert = d.toObject(Insert.class);
                    arrayList.add(insert);
                }
                adapterClass.notifyDataSetChanged();
            }

        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                userListPBar.setVisibility(View.GONE);
            }
        }, 5000);

        return view;
    }
}