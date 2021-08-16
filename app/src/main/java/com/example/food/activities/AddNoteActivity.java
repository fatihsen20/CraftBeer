package com.example.food.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.food.R;
import com.example.food.activities.ui.notes.NotesFragment;
import com.example.food.adapters.DBHandler;
import com.example.food.models.Note;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class AddNoteActivity extends AppCompatActivity {

    private EditText title, desc, type;
    private Button btnAddnote, btnBack, btnImgAdd;
    private DBHandler dbHandler;
    private FirebaseFirestore firestore;
    private Intent intent;
    private String uId;

    public void init() {
        title = findViewById(R.id.activity_add_note_title);
        desc = findViewById(R.id.activity_add_note_desc);
        type = findViewById(R.id.activity_add_note_type);
        btnAddnote = findViewById(R.id.activity_add_note_btnAdd);
        btnBack = findViewById(R.id.activity_add_note_btnBack);
        btnImgAdd = findViewById(R.id.activity_add_note_btnPhotoadd);

        dbHandler = new DBHandler(firestore);

        intent = getIntent();
        uId = intent.getStringExtra("uId");

    }

    public void ButtonClick() {

        btnAddnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtTitle = title.getText().toString();
                String txtDesc = desc.getText().toString();
                String txtType = type.getText().toString();

                if (!TextUtils.isEmpty(txtTitle) && !TextUtils.isEmpty(txtDesc) && !TextUtils.isEmpty(txtType)) {
                    Note note = new Note(txtTitle, txtDesc, txtType);
                    dbHandler.addNote(note, uId, txtTitle);
                    onBackPressed();
                } else
                    Toast.makeText(AddNoteActivity.this, "Kutucukları Boş Bırakmayınız!", Toast.LENGTH_SHORT).show();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnImgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(AddNoteActivity.this, AddNotePhotoActivity.class);
                intent.putExtra("beerName",title.getText().toString());
                intent.putExtra("uId", uId);
                startActivity(intent);

            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        init();
        ButtonClick();
    }
}