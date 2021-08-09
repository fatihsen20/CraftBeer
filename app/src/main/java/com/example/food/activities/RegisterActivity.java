package com.example.food.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.food.R;
import com.example.food.adapters.DBHandler;
import com.example.food.models.Animations;
import com.example.food.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity {

    private EditText email, pass, passvalidation, name, age;
    private CheckBox visiblepass , visiblepassValidation;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private FirebaseUser mUser;
    private DBHandler dbHandler;

    public void init(){
        email = findViewById(R.id.activity_register_email);
        pass = findViewById(R.id.activity_register_pass);
        passvalidation = findViewById(R.id.activity_register_passValidation);
        name = findViewById(R.id.activity_register_name);
        age = findViewById(R.id.activity_register_age);
        visiblepass = findViewById(R.id.activity_register_Visiblepass);
        visiblepassValidation = findViewById(R.id.activity_register_VisiblepassValidation);
        dbHandler = new DBHandler(mAuth , mUser , firestore);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Kayıt Ol");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();
        itemClick();
        Animation();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void Register(View view) {
        String txtName = name.getText().toString();
        String txtEmail = email.getText().toString();
        String txtPass = pass.getText().toString();
        String txtPassValidation = passvalidation.getText().toString();
        String txtAge = age.getText().toString();

        if (!TextUtils.isEmpty(txtName)&& !TextUtils.isEmpty(txtEmail)&& !TextUtils.isEmpty(txtPass)&& !TextUtils.isEmpty(txtPassValidation)&& !TextUtils.isEmpty(txtAge)){
            if (txtPass.compareTo(txtPassValidation)== 0 ){
                User user = new User(txtEmail, txtPass, Integer.parseInt(txtAge),txtName);
                dbHandler.addRegister(user , RegisterActivity.this);
            }
            else
                Toast.makeText(this, "Şifreler Aynı Değil!", Toast.LENGTH_SHORT).show();

        }
        else
            Toast.makeText(this, "Lütfen Kutucukları Boş Bırakmayınız!", Toast.LENGTH_SHORT).show();
    }

    public void Animation(){
        Animations.addAnimation(this, R.id.activity_register_BtnReg, R.anim.bounce , 1000);
    }

    public void itemClick(){
        visiblepass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                else
                    pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });

        visiblepassValidation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    passvalidation.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                else
                    passvalidation.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });
    }

}