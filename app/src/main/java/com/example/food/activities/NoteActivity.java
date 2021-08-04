package com.example.food.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.food.R;

public class NoteActivity extends AppCompatActivity {
    private TextView title , desc;
    private ImageView beerPhoto;

    public void init(){
        title = findViewById(R.id.activity_note_title);
        desc = findViewById(R.id.activity_note_desc);
        beerPhoto = findViewById(R.id.activity_note_Image);

        Intent intent = getIntent();

        String txtTitle = intent.getStringExtra("title");
        String txtDesc = intent.getStringExtra("desc");
        String txtType = intent.getStringExtra("type");
        int image = intent.getIntExtra("image" , 0);

        title.setText(txtTitle);
        desc.setText(txtDesc);
        beerPhoto.setImageResource(image);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        init();
    }
}