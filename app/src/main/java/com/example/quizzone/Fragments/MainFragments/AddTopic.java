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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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

public class AddTopic extends Fragment {

    public AddTopic() {
        // Required empty public constructor
    }

    ImageView imageView;
    ImageButton btnAdd;
    EditText topicName;
    Button btnSave;

    private final int PICK_IMAGE_REQUEST = 22;
    private Uri filepath;
    String Path;

    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    StorageReference storageReference = firebaseStorage.getReference();

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference colReference = db.collection("Topics");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_topic, container, false);

        imageView = view.findViewById(R.id.imageView);
        btnAdd = view.findViewById(R.id.btnChoose);
        topicName = view.findViewById(R.id.addTopicET);
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

                if(TopicName.equals("")){
                    topicName.setError("Topic Name and Image is Mandatory");
                }
                else{


                    UploadImage(TopicName);

                }



            }
        });

        return view;
    }

    private void UploadImage(String TopicName) {
        if(filepath != null){

            ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Uploading");
            progressDialog.show();


            StorageReference ref = storageReference.child("uploads/" + TopicName);

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

                    storageReference.child("uploads/" + TopicName).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Uri DownUri = uri;
                            Path = uri.toString();

                            AddTopics addTopics = new AddTopics(Path, TopicName);

                            colReference.document(TopicName).set(addTopics).addOnCompleteListener(new OnCompleteListener<Void>() {
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
                    });

                }
            });
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