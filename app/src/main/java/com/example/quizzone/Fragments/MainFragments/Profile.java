package com.example.quizzone.Fragments.MainFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.quizzone.Classes.Insert;
import com.example.quizzone.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Collection;

public class Profile extends Fragment {

    FirebaseFirestore db;

    EditText EmailET;
    EditText IdET;
    EditText NameET;
    EditText MobileNumberET;
    EditText PasswordET;
    EditText StatusET;
    EditText OccupationET;
    Button btnEdit;
    Button btnSave;
    RadioButton OccupationRB;
    RadioGroup OccupationRG;
    LinearLayout btnSaveLL;
    LinearLayout btnEditLL;
    String OccupationPF, OccupationAC; //AC After Conversion

    public Profile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        String Email = this.getArguments().getString("Email");

        db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("Users").document(Email);
        CollectionReference colRef = db.collection("Users");

        EmailET = view.findViewById(R.id.ProfileEmail);
        IdET = view.findViewById(R.id.ProfileID);
        MobileNumberET = view.findViewById(R.id.ProfileMobileNumber);
        PasswordET = view.findViewById(R.id.ProfilePassword);
        NameET = view.findViewById(R.id.ProfileName);
        OccupationET = view.findViewById(R.id.ProfileOccupation);
        OccupationRG = view.findViewById(R.id.OccupationRG);
        StatusET = view.findViewById(R.id.ProfileStatus);
        btnEdit = view.findViewById(R.id.ButtonEdit);
        btnSave = view.findViewById(R.id.ButtonSave);
        btnSaveLL = view.findViewById(R.id.btnSaveLL);
        btnEditLL = view.findViewById(R.id.btnEditLL);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EmailET.setEnabled(false);
                IdET.setEnabled(true);
                MobileNumberET.setEnabled(true);
                PasswordET.setEnabled(true);
                NameET.setEnabled(true);
                OccupationET.setVisibility(View.GONE);
                OccupationRG.setVisibility(View.VISIBLE);
                btnSaveLL.setVisibility(View.VISIBLE);
                btnEditLL.setVisibility(View.INVISIBLE);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //PF means from Profile Fragment
                int id = OccupationRG.getCheckedRadioButtonId();

                        if(id == -1){
                            Toast.makeText(getContext(), "Please Select Occupation", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            OccupationRB = OccupationRG.findViewById(id);
                            OccupationPF = OccupationRB.getText().toString();
                        }

                String EmailPF = EmailET.getText().toString();
                String IdPF = IdET.getText().toString();
                String MobileNumberPF = MobileNumberET.getText().toString();
                String PasswordPF = PasswordET.getText().toString();
                String NamePF = NameET.getText().toString();
                String StatusPF = StatusET.getText().toString();


                if(EmailPF.equals("Please Update") || IdPF.equals("Please Update") || MobileNumberPF.equals("Please Update")
                        || PasswordPF.equals("Please Update") || NamePF.equals("Please Update") || StatusPF.equals("Please Update")){
                    Toast.makeText(getContext(), "Please Update all Fields", Toast.LENGTH_LONG).show();
                }
                else if(EmailPF.equals("") || IdPF.equals("") || MobileNumberPF.equals("") || PasswordPF.equals("") ||
                        NamePF.equals("") || StatusPF.equals("") ){
                    Toast.makeText(getContext(), "Please Fill all Fields", Toast.LENGTH_LONG).show();
                }
                else{
                    if(OccupationPF.equals("Student")){
                        OccupationAC = "1";
                    }
                    else{
                        OccupationAC = "2";
                    }
                    Insert insert = new Insert(EmailPF, IdPF, MobileNumberPF, PasswordPF, NamePF, OccupationAC, "Active");
                    colRef.document(EmailPF).set(insert).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getContext(), "Profile Updated Successfully!!", Toast.LENGTH_SHORT).show();
                                EmailET.setEnabled(false);
                                IdET.setEnabled(false);
                                MobileNumberET.setEnabled(false);
                                PasswordET.setEnabled(false);
                                NameET.setEnabled(false);
                                OccupationET.setVisibility(View.VISIBLE);
                                OccupationRG.setVisibility(View.GONE);
                                if(OccupationAC.equals("1")){
                                    OccupationET.setText("Student");
                                }
                                else if(OccupationAC.equals("2")){
                                    OccupationET.setText("Teacher");
                                }
                                btnSaveLL.setVisibility(View.INVISIBLE);
                                btnEditLL.setVisibility(View.VISIBLE);
                            }
                            else{
                                Toast.makeText(getContext(), "Unexpected Error! Try Again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }




            }
        });

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

                        if(NameDB.equals("User")){
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

                        if(StatusDB.equals("Partial")){
                            btnEdit.setText("Update");
                        }
                        else{
                            btnEdit.setText("Edit");
                        }

                    }
                }
            }
        });


        return view;
    }
}