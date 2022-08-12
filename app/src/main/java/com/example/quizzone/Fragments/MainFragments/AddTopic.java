package com.example.quizzone.Fragments.MainFragments;

import static android.app.Activity.RESULT_OK;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Path;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.quizzone.Classes.AddTopics;
import com.example.quizzone.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class AddTopic extends Fragment {

    public AddTopic() {
        // Required empty public constructor
    }

    ImageView imageView;
    ImageView btnAdd;
    EditText topicName, topicEmail, topicAdderName;
    Spinner nicheSpinner;
    Button btnSave;

    private final int PICK_IMAGE_REQUEST = 22;
    private Uri filepath;
    String Path;

    String Email;
    String Name;

    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    StorageReference storageReference = firebaseStorage.getReference();

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference colReference = db.collection("Topics");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_topic, container, false);

         Name = this.getArguments().getString("Name");
         Email = this.getArguments().getString("Email");

        imageView = view.findViewById(R.id.imageView);
        btnAdd = view.findViewById(R.id.btnChoose);
        topicName = view.findViewById(R.id.addTopicET);
        topicEmail = view.findViewById(R.id.addTopicEmail);
        topicAdderName = view.findViewById(R.id.addTopicName);
        nicheSpinner = view.findViewById(R.id.addTopicNiche);

        topicEmail.setText(Email);
        topicAdderName.setText(Name);

        List<String> niche = new ArrayList<String>();
        niche.add("Web Development");
        niche.add("Android Development");
        niche.add("Programming");
        niche.add("Other");


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner_text, niche);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nicheSpinner.setAdapter(arrayAdapter);

//        nicheSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                dataSelected = nicheSpinner.getSelectedItem().toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

        btnSave = view.findViewById(R.id.addTopicBtn);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectImage();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String TopicName = topicName.getText().toString();
                String dataSelected = nicheSpinner.getSelectedItem().toString();
                if(TopicName.equals("")){
                    topicName.setError("Topic Name and Image is Mandatory");
                }
                else{


                    UploadImage(TopicName, dataSelected);

                }



            }
        });

        return view;
    }

    private void UploadImage(String TopicName, String dataSelected) {
        if(filepath != null){

            ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            String imageName = TopicName + Email;
            StorageReference ref = storageReference.child("uploads/" + imageName);

            ref.putFile(filepath).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if(task.isSuccessful()){
                        progressDialog.setMessage("Upload Complete");
                        Toast.makeText(getContext(), "Image Uploaded Successfully! Please Wait", Toast.LENGTH_SHORT).show();
                    }
                }

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Unexpected Error! not Uploaded! Try Again", Toast.LENGTH_SHORT).show();

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                    progressDialog.setMessage("Upload " + progress + "%");
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    storageReference.child("uploads/" + imageName).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Uri DownUri = uri;
                            Path = uri.toString();


                            if(TopicName.equals("") || Name.equals("") || Email.equals("")){
                                Toast.makeText(getContext(), "Please Fill all Mandatory Fields", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                AddTopics addTopics = new AddTopics(Path, TopicName, Name,dataSelected, Email);

                                String nameDoc = TopicName + Email;

                                colReference.document(nameDoc).set(addTopics).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()) {
                                            progressDialog.dismiss();
                                            Toast.makeText(getContext(), "Topic Added Successfully", Toast.LENGTH_SHORT).show();
                                        }
                                        else{
                                            Toast.makeText(getContext(), "Unexpected Error! Try Again", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }


                        }
                    });

                }
            });
        }
        else{
            Toast.makeText(getContext(), "Please Select Image", Toast.LENGTH_SHORT).show();
        }
    }

    private void SelectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK &&
                data != null && data.getData() != null){
            filepath = data.getData();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filepath);
                imageView.setImageBitmap(bitmap);
            }catch(Exception e){
                Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
            }
        }

    }

}