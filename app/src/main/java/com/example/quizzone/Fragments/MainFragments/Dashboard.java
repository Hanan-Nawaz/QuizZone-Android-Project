package com.example.quizzone.Fragments.MainFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizzone.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

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

        CollectionReference users = db.collection("Users");

        users.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    QuerySnapshot Snapshot = task.getResult();

                   int Total = Snapshot.size();

                   String TotalUser = String.valueOf(Total);
                    user.setText(TotalUser);
                }
                else{
                    user.setText("0");
                }
            }
        });

        CollectionReference SubjectsRef = db.collection("Topics");

        SubjectsRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    QuerySnapshot querySnapshot = task.getResult();

                    int TotalSub = querySnapshot.size();
                    String Total = String.valueOf(TotalSub);
                    subject.setText(Total);
                }
                else{
                    subject.setText("0");
                }
            }
        });

        CollectionReference TestIdRef = db.collection(Email);

        TestIdRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    QuerySnapshot querySnapshot = task.getResult();
                    int TotalTest = querySnapshot.size();
                    String Total = String.valueOf(TotalTest);
                    test.setText(Total);
                }
                else{
                    test.setText("0");
                }
            }
        });
        return view;
    }
}