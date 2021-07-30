package com.example.food.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.example.food.R;
import com.example.food.adapters.DBHandler;
import com.example.food.models.Animations;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private EditText email, pass;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private DBHandler dbHandler;
    private Intent intent;
    private Button btnforgotpass;
    private CheckBox checkBox;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    private void init(){
        email = findViewById(R.id.activity_main_email);
        pass = findViewById(R.id.activity_main_pass);
        dbHandler = new DBHandler(mAuth, firestore);
        btnforgotpass = findViewById(R.id.activity_main_forgotpass);
        checkBox = findViewById(R.id.activity_main_checkbox);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Hoşgeldiniz");

        btnforgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this , ForgotPassActivity.class);
                startActivity(intent);
            }
        });

        sharedPreferences = this.getSharedPreferences("com.example.sharedpreferences", Context.MODE_PRIVATE);
        String getemail = sharedPreferences.getString("email" , null);
        Boolean check = sharedPreferences.getBoolean("checkbox" , false);

        if (check && !TextUtils.isEmpty(getemail)) {
            email.setText(getemail);
            checkBox.setChecked(check);
        }
        Animations.addAnimation(this, R.id.activity_main_email, R.anim.lefttoright , 2000);
        Animations.addAnimation(this, R.id.activity_main_pass, R.anim.righttoleft , 2000);
        Animations.addAnimation(this, R.id.activity_main_checkbox, R.anim.bounce , 2000);
        Animations.addAnimation(this, R.id.activity_main_btnContainer, R.anim.bounce , 2000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void Login(View view) {
        String txtEmail = email.getText().toString();
        String txtPass = pass.getText().toString();

        if (!TextUtils.isEmpty(txtEmail) && !TextUtils.isEmpty(txtPass)){
            dbHandler.addLogin(txtEmail , txtPass , MainActivity.this);
            remember_me(sharedPreferences);
        }
        else
            Toast.makeText(this, "Lütfen Kutucukları Boş Bırakmayınız!", Toast.LENGTH_SHORT).show();

    }

    public void Register(View view) {
        intent = new Intent(this , RegisterActivity.class);
        startActivity(intent);
    }
    public void remember_me(SharedPreferences sharedPreferences){
        if (checkBox.isChecked()) {
            editor = sharedPreferences.edit();
            editor.putString("email", email.getText().toString());
            editor.putBoolean("checkbox", true);
            editor.apply();
        }
        else{
            editor = sharedPreferences.edit();
            editor.putString("email", null);
            editor.putBoolean("checkbox" , false);
            editor.apply();
        }
    }
}