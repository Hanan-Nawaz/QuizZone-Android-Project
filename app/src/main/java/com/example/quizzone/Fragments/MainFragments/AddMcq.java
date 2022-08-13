package com.example.quizzone.Fragments.MainFragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizzone.Classes.AddMcqs;
import com.example.quizzone.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddMcq extends Fragment {

    TextView TopicName;
    EditText QsET, Op1, Op2, Op3, COp;
    Button saveBTn;

    public AddMcq() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_mcq, container, false);
        String TName = this.getArguments().getString("TopicName");
        String Name = this.getArguments().getString("Name");

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference colRef = db.collection(TName);

        TopicName = view.findViewById(R.id.addMcqTopicName);
        QsET = view.findViewById(R.id.addMcqQsET);
        Op1 = view.findViewById(R.id.addMcqOp1);
        Op2 = view.findViewById(R.id.addMcqOp2);
        Op3 = view.findViewById(R.id.addMcqOp3);
        COp = view.findViewById(R.id.addMcqOpCorrect);
        saveBTn = view.findViewById(R.id.addMcqBtn);

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Saving");

        TopicName.setText(Name);

        saveBTn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                String Qs = QsET.getText().toString();
                String Op1T = Op1.getText().toString();
                String Op2T = Op2.getText().toString();
                String Op3T = Op3.getText().toString();
                String COpT = COp.getText().toString();

                AddMcqs addMcqs = new AddMcqs(Qs, Op1T, Op2T, Op3T, COpT);

                colRef.add(addMcqs).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getContext(), "Mcq added Successfully", Toast.LENGTH_SHORT).show();
                            progressDialog.cancel();
                            ViewTopics viewTopics = new ViewTopics();
                        }
                        else {
                            Toast.makeText(getContext(), "Unexpected Error! Please Try Again.", Toast.LENGTH_SHORT).show();
                            progressDialog.cancel();
                        }
                    }
                });

            }
        });

        return view;
    }
}