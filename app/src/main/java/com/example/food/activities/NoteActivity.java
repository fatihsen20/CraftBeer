package com.example.food.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.food.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class NoteActivity extends AppCompatActivity {
    private TextView title , desc, type;
    private ImageView noteImage;
    private ProgressDialog progressDialog;
    private String uId;
    private String txtTitle;

    public void init(){
        title = findViewById(R.id.activity_note_title);
        desc = findViewById(R.id.activity_note_desc);
        type = findViewById(R.id.activity_note_type);
        noteImage = findViewById(R.id.activity_note_Image);

        Intent intent = getIntent();

        txtTitle = intent.getStringExtra("title");
        String txtDesc = intent.getStringExtra("desc");
        String txtType = intent.getStringExtra("type");
        uId = intent.getStringExtra("uId");

        desc.setMovementMethod(new ScrollingMovementMethod());
        title.setText(txtTitle);
        desc.setText(txtDesc);
        type.setText(txtType);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        init();
        showPhoto();
    }

    public void showPhoto(){

        showProgressDialog();
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();
        storageReference.child("NoteImage").child(uId+txtTitle).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                progressDialog.dismiss();

                Glide.with(NoteActivity.this)
                        .load(uri)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(new SimpleTarget<Drawable>() {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                noteImage.setBackground(resource);

                            }
                        });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(NoteActivity.this, "Yükleme Başarısız!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Yükleniyor...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
}