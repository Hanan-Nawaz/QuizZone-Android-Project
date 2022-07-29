package com.example.quizzone.Fragments.AuthFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.quizzone.Activities.SignInSignUp.AuthActivity;
import com.example.quizzone.Classes.Insert;
import com.example.quizzone.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Signup_with_email extends Fragment {

    Button back;
    Button SignUpBtn;
    EditText Email;
    EditText Password;
    FirebaseFirestore db;
    String email,password;
    ProgressBar PBar;

    public Signup_with_email() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup_with_email, container, false);

        back = view.findViewById(R.id.Back);
        Email = view.findViewById(R.id.EmailSignUp);
        Password = view.findViewById(R.id.PasswordSignUp);
        SignUpBtn = view.findViewById(R.id.BtnSignUp);
        PBar = view.findViewById(R.id.ProgressSignup);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AuthActivity.class);
                startActivity(intent);
            }
        });

        db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection("Users");

        SignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = Email.getText().toString();
                password = Password.getText().toString();
                PBar.setVisibility(View.VISIBLE);

                if(email.equals("") ){
                    Email.setError("Email is Mandatory");
                    PBar.setVisibility(View.INVISIBLE);
                }
                else if(password.equals("")){
                    Password.setError("Password is Mandatory");
                    PBar.setVisibility(View.INVISIBLE);
                }
                else {
                    Insert insert = new Insert(email, "0" , "0", password , "0", "0", "Partial");
                    collectionReference.document(email).set(insert).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getContext(), "User Created Successfully. Please SignIn", Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(getContext(), "Unexpected Error. Please Try Again ", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });




        return view;
    }
}