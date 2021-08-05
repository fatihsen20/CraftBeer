package com.example.food.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.food.R;

public class NoteActivity extends AppCompatActivity {
    private TextView title , desc, type;

    public void init(){
        title = findViewById(R.id.activity_note_title);
        desc = findViewById(R.id.activity_note_desc);
        type = findViewById(R.id.activity_note_type);

        Intent intent = getIntent();

        String txtTitle = intent.getStringExtra("title");
        String txtDesc = intent.getStringExtra("desc");
        String txtType = intent.getStringExtra("type");
        int image = intent.getIntExtra("image" , 0);

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
    }
}