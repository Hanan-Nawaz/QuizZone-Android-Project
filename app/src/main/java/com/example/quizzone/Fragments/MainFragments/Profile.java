package com.example.quizzone.Fragments.MainFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.quizzone.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Profile extends Fragment {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    EditText EmailET;
    EditText IdET;
    EditText NameET;
    EditText MobileNumberET;
    EditText PasswordET;
    EditText StatusET;
    EditText OccupationET;

    public Profile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        String Email = this.getArguments().getString("Email");

        DocumentReference docRef = db.collection("Users").document(Email);

        EmailET = view.findViewById(R.id.ProfileEmail);
        IdET = view.findViewById(R.id.ProfileID);
        MobileNumberET = view.findViewById(R.id.ProfileMobileNumber);
        PasswordET = view.findViewById(R.id.ProfilePassword);
        NameET = view.findViewById(R.id.ProfileName);
        OccupationET = view.findViewById(R.id.ProfileOccupation);
        StatusET = view.findViewById(R.id.ProfileStatus);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if(documentSnapshot.exists()){
                        String NameDB = documentSnapshot.getString("Name");
                        String EmailDB = documentSnapshot.getString("Email");
                        String PasswordDB = documentSnapshot.getString("Password");
                        String IdDB = documentSnapshot.getString("ID");
                        String MobileNumberDB = documentSnapshot.getString("MobileNumber");
                        String OccupationDB = documentSnapshot.getString("Occupation");
                        String StatusDB = documentSnapshot.getString("Status");

                        if(NameDB.equals("0")){
                            NameET.setText("Please Update");
                        }
                        else{
                            NameET.setText(NameDB);
                        }

                        if(IdDB.equals("0")){
                            IdET.setText("Please Update");
                        }
                        else{
                            IdET.setText(IdDB);
                        }

                        if(MobileNumberDB.equals("0")){
                            MobileNumberET.setText("Please Update");
                        }
                        else{
                            MobileNumberET.setText(MobileNumberDB);
                        }

                        if(PasswordDB.equals("0")){
                            PasswordET.setText("Please Update");
                        }
                        else{
                            PasswordET.setText(PasswordDB);
                        }

                        if(EmailDB.equals("0")){
                            EmailET.setText("Please Update");
                        }
                        else{
                            EmailET.setText(EmailDB);
                        }

                        if(OccupationDB.equals("0")){
                            OccupationET.setText("Please Update");
                        }
                        else if(OccupationDB.equals("1")){
                            OccupationET.setText("Student");
                        }
                        else if(OccupationDB.equals("2")){
                            OccupationET.setText("Teacher");
                        }
                        else if(OccupationDB.equals("3")){
                            OccupationET.setText("Admin");
                        }
                        else{
                            OccupationET.setText(OccupationDB);
                        }

                        if(StatusDB.equals("0")){
                            StatusET.setText("Please Update");
                        }
                        else{
                            StatusET.setText(StatusDB);
                        }

                    }
                }
            }
        });


        return view;
    }
}