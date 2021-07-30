package com.example.food.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.food.R;
import com.example.food.adapters.DBHandler;
import com.example.food.models.Animations;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassActivity extends AppCompatActivity {

    private EditText email;
    private FirebaseAuth mAuth;
    private DBHandler dbHandler;

    public void init(){
        email = findViewById(R.id.activity_forgot_pass_email);
        dbHandler = new DBHandler(mAuth);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Şifremi Unuttum");

        Animations.addAnimation(this, R.id.activity_forgot_pass_btnReset, R.anim.bounce , 1000);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        init();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void ResetPass(View view) {
        String txtEmail = email.getText().toString();
        if (!TextUtils.isEmpty(txtEmail)){
            dbHandler.resetPass(txtEmail,this);
        }
        else
            Toast.makeText(this, "Lütfen Kutucuğu Boş Bırakmayınız!", Toast.LENGTH_SHORT).show();
    }
}