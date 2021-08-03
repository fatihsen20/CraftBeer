package com.example.food.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
    private CheckBox checkBox, visibilityPass;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    private void init(){
        email = findViewById(R.id.activity_main_email);
        pass = findViewById(R.id.activity_main_pass);
        dbHandler = new DBHandler(mAuth, firestore);
        btnforgotpass = findViewById(R.id.activity_main_forgotpass);
        checkBox = findViewById(R.id.activity_main_checkbox);
        visibilityPass = findViewById(R.id.activity_main_Visiblepass);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Hoşgeldiniz");

        //Forgot Pass Button Click Listener
        btnforgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this , ForgotPassActivity.class);
                startActivity(intent);
            }
        });

        //Remember me code
        sharedPreferences = this.getSharedPreferences("com.example.sharedpreferences", Context.MODE_PRIVATE);
        String getemail = sharedPreferences.getString("email" , null);
        Boolean check = sharedPreferences.getBoolean("checkbox" , false);

        if (check && !TextUtils.isEmpty(getemail)) {
            email.setText(getemail);
            checkBox.setChecked(check);
        }

        //Checkbox PassToogleClick
        visibilityPass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                else
                    pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        Animation();
    }

    @Override
    public void onBackPressed() {
        //Geri Tuşu İptal Edildi.
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

    public void Animation(){
        Animations.addAnimation(this, R.id.activity_main_email, R.anim.lefttoright , 2000);
        Animations.addAnimation(this, R.id.activity_main_pass, R.anim.righttoleft , 2000);
        Animations.addAnimation(this, R.id.activity_main_checkbox, R.anim.bounce , 2000);
        Animations.addAnimation(this, R.id.activity_main_btnContainer, R.anim.bounce , 2000);
    }

}