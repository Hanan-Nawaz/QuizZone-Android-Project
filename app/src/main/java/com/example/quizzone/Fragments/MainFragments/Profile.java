package com.example.quizzone.Fragments.MainFragments;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizzone.Classes.AddTopics;
import com.example.quizzone.Classes.Insert;
import com.example.quizzone.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;


public class Profile extends Fragment {


    EditText EmailET;
    EditText IdET;
    EditText NameET;
    EditText MobileNumberET;
    EditText PasswordET;
    EditText StatusET;
    TextView btnEdit;
    TextView btnSave;
    TextView btnCancel;
    TextView NameTV, OccupationTV;
    RadioButton OccupationRB;
    RadioGroup OccupationRG;
    ImageView addBtn;
    CircleImageView imageView, imageViewShow;
    LinearLayout NameLayout, OccupationLayout, EditLL, ViewLL;
    String OccupationPF, OccupationAC; //AC After Conversion

    private final int PICK_IMAGE_REQUEST = 22;
    private Uri filepath;
    String Path;

    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    StorageReference storageReference = firebaseStorage.getReference();

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference colRef = db.collection("Users");

    public Profile() {
        // Required empty public constructor
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Loading");
        progressDialog.show();

        String Email = this.getArguments().getString("Email");

        DocumentReference docRef = db.collection("Users").document(Email);

        NameTV = view.findViewById(R.id.NameDisplay);
        OccupationTV = view.findViewById(R.id.OccupationDisplay);
        EmailET = view.findViewById(R.id.ProfileEmail);
        IdET = view.findViewById(R.id.ProfileID);
        MobileNumberET = view.findViewById(R.id.ProfileMobileNumber);
        PasswordET = view.findViewById(R.id.ProfilePassword);
        NameET = view.findViewById(R.id.ProfileName);
        OccupationRG = view.findViewById(R.id.OccupationRG);
        StatusET = view.findViewById(R.id.ProfileStatus);
        NameLayout = view.findViewById(R.id.NameLayout);
        addBtn = view.findViewById(R.id.AddImageBtn);
        imageView = view.findViewById(R.id.ImageViewProfile);
        imageViewShow = view.findViewById(R.id.IVProfile);
        OccupationLayout = view.findViewById(R.id.OccupationLayout);
        EditLL = view.findViewById(R.id.EditProfile);
        ViewLL = view.findViewById(R.id.ViewProfile);
        btnEdit = view.findViewById(R.id.EditProfileTV);
        btnSave = view.findViewById(R.id.saveTV);
        btnCancel = view.findViewById(R.id.cancelTV);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EmailET.setEnabled(false);
                IdET.setEnabled(true);
                MobileNumberET.setEnabled(true);
                PasswordET.setEnabled(true);
                NameET.setEnabled(true);
                OccupationRG.setVisibility(View.VISIBLE);
                btnSave.setVisibility(View.VISIBLE);
                btnCancel.setVisibility(View.VISIBLE);
                btnEdit.setVisibility(View.INVISIBLE);
                NameLayout.setVisibility(View.VISIBLE);
                OccupationLayout.setVisibility(View.VISIBLE);
                ViewLL.setVisibility(View.GONE);
                EditLL.setVisibility(View.VISIBLE);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EmailET.setEnabled(false);
                IdET.setEnabled(false);
                MobileNumberET.setEnabled(false);
                PasswordET.setEnabled(false);
                NameET.setEnabled(false);
                OccupationRG.setVisibility(View.GONE);
                btnSave.setVisibility(View.INVISIBLE);
                btnCancel.setVisibility(View.INVISIBLE);
                btnEdit.setVisibility(View.VISIBLE);
                NameLayout.setVisibility(View.INVISIBLE);
                OccupationLayout.setVisibility(View.INVISIBLE);
                ViewLL.setVisibility(View.VISIBLE);
                EditLL.setVisibility(View.GONE);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //PF means from Profile Fragment
                UploadImage(Email);

            }

        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectImage();
            }
        });

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if(documentSnapshot.exists()){
                        String NameDB = documentSnapshot.getString("name");
                        String EmailDB = documentSnapshot.getString("email");
                        String PasswordDB = documentSnapshot.getString("password");
                        String IdDB = documentSnapshot.getString("id");
                        String MobileNumberDB = documentSnapshot.getString("mobileNumber");
                        String OccupationDB = documentSnapshot.getString("occupation");
                        String StatusDB = documentSnapshot.getString("status");
                        String ImageDB = documentSnapshot.getString("image");

                        if(NameDB.equals("User")){
                            NameET.setText("Please Update");
                            NameTV.setText("Please Update");
                        }
                        else{
                            NameET.setText(NameDB);
                            NameTV.setText(NameDB);
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
                            OccupationTV.setText("Please Update");
                        }
                        else if(OccupationDB.equals("1")){
                            OccupationTV.setText("Student");
                        }
                        else if(OccupationDB.equals("2")){
                            OccupationTV.setText("Teacher");
                        }
                        else if(OccupationDB.equals("3")){
                            OccupationTV.setText("Admin");
                        }
                        else{
                            OccupationTV.setText(OccupationDB);
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
                            btnEdit.setText("Edit Profile");
                        }

                        if(ImageDB.equals("0")){

                        }
                        else{
                            try {
                                URL url = new URL(ImageDB);
                                Bitmap bMap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                                imageView.setImageBitmap(bMap);
                                imageViewShow.setImageBitmap(bMap);
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }



                    }
                }
                progressDialog.cancel();
            }
        });

        return view;
    }


    private void UploadImage(String Email) {
        if(filepath != null){

            ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Updating");
            progressDialog.show();


            StorageReference ref = storageReference.child("uploads/" + Email);

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

                    storageReference.child("uploads/" + Email).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Uri DownUri = uri;
                            Path = uri.toString();

                            int id = OccupationRG.getCheckedRadioButtonId();

                            if(id == -1){
                                Toast.makeText(getContext(), "Please Select Occupation", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                OccupationRB = OccupationRG.findViewById(id);
                                OccupationPF = OccupationRB.getText().toString();


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
                                    Insert insert = new Insert(EmailPF, IdPF, MobileNumberPF, PasswordPF, NamePF, OccupationAC, "Active", Path);
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
                                                OccupationRG.setVisibility(View.GONE);
                                                btnSave.setVisibility(View.INVISIBLE);
                                                btnCancel.setVisibility(View.INVISIBLE);
                                                btnEdit.setVisibility(View.VISIBLE);
                                                NameLayout.setVisibility(View.INVISIBLE);
                                                OccupationLayout.setVisibility(View.INVISIBLE);
                                                ViewLL.setVisibility(View.VISIBLE);
                                                EditLL.setVisibility(View.GONE);
                                                progressDialog.cancel();
                                            }
                                            else{
                                                Toast.makeText(getContext(), "Unexpected Error! Try Again", Toast.LENGTH_SHORT).show();
                                                progressDialog.cancel();
                                            }
                                        }

                                    });
                                }
                            }
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