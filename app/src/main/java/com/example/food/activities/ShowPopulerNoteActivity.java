package com.example.food.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.food.R;
import com.example.food.models.PopulerNote;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class ShowPopulerNoteActivity extends AppCompatActivity {
    private TextView name, type, desc;
    private ImageView image;
    private FirebaseFirestore firestore;
    private FirebaseStorage firebaseStorage;
    private Intent intent;
    private PopulerNote populerNote;
    private String txtImage;
    private ProgressDialog progressDialog;

    public void init(){
        name = findViewById(R.id.activity_show_populer_note_name);
        type = findViewById(R.id.activity_show_populer_note_type);
        desc = findViewById(R.id.activity_show_populer_note_desc);
        image = findViewById(R.id.activity_show_populer_note_Image);
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tarif");

        firestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        intent = getIntent();
        populerNote = (PopulerNote) intent.getSerializableExtra("populerNote");

        String txtName = populerNote.getName();
        String txtType = populerNote.getType();
        String txtDesc = populerNote.getDesc();
        txtImage = populerNote.getImg();

        desc.setMovementMethod(new ScrollingMovementMethod());
        name.setText(txtName);
        type.setText(txtType);
        desc.setText(txtDesc);

        image.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(ShowPopulerNoteActivity.this);
                final ImageView tempImg = new ImageView(ShowPopulerNoteActivity.this);
                tempImg.setImageDrawable(image.getDrawable());
                alert.setView(tempImg);
                alert.setNegativeButton("Geri Gel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alert.show();
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_populer_note);

        init();
        getPhoto();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void getPhoto(){
        showProgressDialog();
        StorageReference storageReference = firebaseStorage.getReferenceFromUrl(txtImage);
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                progressDialog.dismiss();
                Glide.with(ShowPopulerNoteActivity.this)
                        .load(uri)
                        .into(new SimpleTarget<Drawable>() {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                image.setImageDrawable(resource);
                            }
                        });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("Başarısız" , e.getMessage());
            }
        });
    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(ShowPopulerNoteActivity.this);
        progressDialog.setMessage("Yükleniyor...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
}