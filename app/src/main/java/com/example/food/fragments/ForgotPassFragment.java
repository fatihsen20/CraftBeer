package com.example.food.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.food.R;
import com.example.food.adapters.DBHandler;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassFragment extends Fragment {
    private EditText email;
    private Button btnReset;
    private FirebaseAuth mAuth;
    private DBHandler dbHandler;
    public ForgotPassFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forgot_pass ,container , false);
        email = view.findViewById(R.id.fragment_forgot_pass_email);
        dbHandler = new DBHandler(mAuth);
        btnReset = view.findViewById(R.id.fragment_forgot_pass_btnReset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtEmail = email.getText().toString();
                if (!TextUtils.isEmpty(txtEmail)){
                    dbHandler.resetPass(txtEmail,getActivity());
                }
                else
                    Toast.makeText(getActivity(), "Lütfen Kutucuğu Boş Bırakmayınız!", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
