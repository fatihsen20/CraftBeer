package com.example.food.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.food.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class AddNotePhotoActivity extends AppCompatActivity {

    private ImageView notePhoto;
    private Button selectPhoto, savePhoto;
    private FirebaseStorage firebaseStorage;
    private FirebaseAuth mAuth;
    private Uri filePath;
    private Intent intent;
    private String uId;
    private String beerName;
    private ProgressDialog progressDialog;
    private static final int IMAGE_REQUEST = 111;

    public void init() {
        mAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        notePhoto = findViewById(R.id.activity_add_note_photo_notePhoto);
        selectPhoto = findViewById(R.id.activity_add_note_photo_selectPhoto);
        savePhoto = findViewById(R.id.activity_add_note_photo_savePhoto);

        intent = getIntent();
        uId = intent.getStringExtra("uId");
        beerName = intent.getStringExtra("beerName");

        selectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPhoto();
            }
        });

        savePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePhoto();
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note_photo);

        init();

    }

    private void selectPhoto() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Resim Seçiniz"), IMAGE_REQUEST);

    }

    public void savePhoto() {

        if (filePath != null) {
            showProgressDialog();
            StorageReference storageReference = firebaseStorage.getReference();
            storageReference.child("NoteImage").child(uId+beerName).putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    Toast.makeText(AddNotePhotoActivity.this, "Fotoğraf Başarıyla Kaydedildi.", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(AddNotePhotoActivity.this, "Fotoğraf Kaydedilmedi.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(AddNotePhotoActivity.this);
        progressDialog.setMessage("Yükleniyor...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Picasso.with(AddNotePhotoActivity.this).load(filePath).fit().centerCrop().into(notePhoto);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}