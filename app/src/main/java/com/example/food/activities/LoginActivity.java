package com.example.food.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.food.R;
import com.example.food.adapters.DBHandler;
import com.example.food.fragments.ForgotPassFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    private EditText email, pass;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private DBHandler dbHandler;
    private Intent intent;
    private Button btnforgotpass;

    private void init(){
        email = findViewById(R.id.activity_main_email);
        pass = findViewById(R.id.activity_main_pass);
        dbHandler = new DBHandler(mAuth, firestore);
        btnforgotpass = findViewById(R.id.activity_main_forgotpass);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

        btnforgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForgotPassFragment fragment = new ForgotPassFragment();
                FragmentManager manager = getSupportFragmentManager();
                androidx.fragment.app.FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(R.id.login_container ,fragment);
                email.setVisibility(View.INVISIBLE);
                pass.setVisibility(View.INVISIBLE);
                findViewById(R.id.activity_main_login).setVisibility(View.INVISIBLE);
                findViewById(R.id.activity_main_register).setVisibility(View.INVISIBLE);
                findViewById(R.id.activity_main_forgotpass).setVisibility(View.INVISIBLE);
                transaction.commit();

            }
        });
    }

    public void Login(View view) {
        String txtEmail = email.getText().toString();
        String txtPass = pass.getText().toString();

        if (!TextUtils.isEmpty(txtEmail) && !TextUtils.isEmpty(txtPass)){
            dbHandler.addLogin(txtEmail , txtPass , LoginActivity.this);
        }
        else
            Toast.makeText(this, "Lütfen Kutucukları Boş Bırakmayınız!", Toast.LENGTH_SHORT).show();

    }

    public void Register(View view) {
        intent = new Intent(this , RegisterActivity.class);
        startActivity(intent);
    }
}