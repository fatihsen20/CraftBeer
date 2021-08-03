package com.example.food.activities.ui.profile;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.food.R;
import com.example.food.activities.AddPhotoActivity;
import com.example.food.activities.MainActivity;
import com.example.food.adapters.DBHandler;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

public class ProfileFragment extends Fragment {
    private TextView name, age, email;
    private DBHandler dbHandler;
    private FirebaseFirestore firestore;
    private FirebaseAuth mAuth;
    FirebaseStorage firebaseStorage;
    private ImageView profilePhoto, changeProfilePhoto;
    private ProgressDialog progressDialog;
    private String uId;
    private Button deleteUser;
    private ProfileViewModel profileViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        name = root.findViewById(R.id.fragment_profile_Name);
        age = root.findViewById(R.id.fragment_profile_Age);
        email = root.findViewById(R.id.fragment_profile_email);
        profilePhoto = root.findViewById(R.id.fragment_profile_Image);
        deleteUser = root.findViewById(R.id.fragment_profile_Delete);
        //changeProfilePhoto = root.findViewById(R.id.fragment_profile_changeImage);

        firestore = FirebaseFirestore.getInstance();
        dbHandler = new DBHandler(mAuth, firestore , firebaseStorage);

        Intent intent = getActivity().getIntent();
        uId = intent.getStringExtra("uId");
        getData(uId);

        profilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddPhotoActivity.class);
                intent.putExtra("uId",uId);
                startActivity(intent);
            }
        });

        deleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Hesabımı Sil");
                alert.setMessage("Hesabınızı Silmek İstediğinizden Emin misiniz? Bu İşlem Geri Alınamaz.");
                alert.setPositiveButton("Hayır", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //
                    }
                });
                alert.setNegativeButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHandler.DeleteUserFirestore(uId);
                        dbHandler.DeleteUserAuthentication(uId);
                        //dbHandler.DeleteUserPhoto(uId);
                        Intent intent2 = new Intent(getContext(), MainActivity.class);
                        startActivity(intent2);
                    }
                });
                alert.show();
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        showProfilePhoto();
    }

    public void showProfilePhoto(){
        showProgressDialog();
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();
        storageReference.child("UserImage").child(uId+"userprofilephoto").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                progressDialog.dismiss();

                Glide.with(getContext())
                        .load(uri)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(new SimpleTarget<Drawable>() {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                profilePhoto.setBackground(resource);

                            }
                        });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Yükleme Başarısız!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Yükleniyor...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void getData(String uId) {
        DocumentReference documentReference = firestore.collection("Kullanıcılar").document(uId);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    try {
                        name.setText(task.getResult().getData().get("fullname").toString());
                        age.setText(task.getResult().getData().get("age").toString());
                        email.setText(task.getResult().getData().get("email").toString());
                    }
                    catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }
        });
    }

}