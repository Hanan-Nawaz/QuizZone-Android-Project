package com.example.quizzone.Fragments.AuthFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizzone.Activities.Menu.MainActivity;
import com.example.quizzone.Activities.SignInSignUp.AuthActivity;
import com.example.quizzone.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class Signin_with_email extends Fragment {

    Button back;
    EditText Email;
    EditText Password;
    Button SignIn;
    FirebaseFirestore db;
    ProgressBar PBar;


    public Signin_with_email() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signin_with_email, container, false);

        back = view.findViewById(R.id.Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AuthActivity.class);
                startActivity(intent);
            }
        });

        Email = view.findViewById(R.id.EmailSignIn);
        Password = view.findViewById(R.id.PasswordSignIn);
        PBar = view.findViewById(R.id.ProgressSignIn);


        db = FirebaseFirestore.getInstance();
        SignIn = view.findViewById(R.id.SignIn);
        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PBar.setVisibility(View.VISIBLE);
                String email = Email.getText().toString();
                String PassFromUSer = Password.getText().toString();

                if(email.equals("") ){
                    Email.setError("Email is Mandatory");
                    PBar.setVisibility(View.INVISIBLE);
                }
                else if(PassFromUSer.equals("")){
                    Password.setError("Password is Mandatory");
                    PBar.setVisibility(View.INVISIBLE);
                }
                else{
                    DocumentReference documentReference = db.collection("Users").document(email);
                    documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful()){
                                DocumentSnapshot document = task.getResult();
                                if(document.exists()){
                                    String password = document.get("Password").toString();
                                    String UName = document.get("Name").toString();
                                    String UEmail = document.get("Email").toString();

                                    if(password.equals(PassFromUSer)){
                                        Toast.makeText(getContext(), "Successfully SignedIn", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(getContext(), MainActivity.class);
                                        intent.putExtra("Email", UEmail);
                                        intent.putExtra("Name", UName);
                                        startActivity(intent);
                                    }
                                    else{
                                        Toast.makeText(getContext(), "Wrong Password!!!", Toast.LENGTH_LONG).show();
                                        PBar.setVisibility(View.INVISIBLE);
                                    }
                                }
                                else {
                                    Toast.makeText(getContext(), "Wrong Email/Password!!!", Toast.LENGTH_LONG).show();
                                    PBar.setVisibility(View.INVISIBLE);
                                }
                            }
                        }
                    });
                }


            }
        });

        return view;
    }
}