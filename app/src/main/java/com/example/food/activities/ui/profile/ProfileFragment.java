package com.example.food.activities.ui.profile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.food.R;
import com.example.food.adapters.DBHandler;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class ProfileFragment extends Fragment {
    private TextView name, age, email;
    DBHandler dbHandler;
    FirebaseFirestore firestore;

    private ProfileViewModel profileViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        name = root.findViewById(R.id.fragment_profile_Name);
        age = root.findViewById(R.id.fragment_profile_Age);
        email = root.findViewById(R.id.fragment_profile_email);

        firestore = FirebaseFirestore.getInstance();
        dbHandler = new DBHandler(firestore);

        Intent intent = getActivity().getIntent();
        String uId = intent.getStringExtra("uId");

        getData(uId);

        return root;
    }

    public void getData(String uId) {
        DocumentReference documentReference = firestore.collection("Kullanıcılar").document(uId);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    try {
                        name.setText(task.getResult().getData().get("fullname").toString());
                        age.setText(task.getResult().getData().get("age").toString());
                        email.setText(task.getResult().getData().get("email").toString());
                    }
                    catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }
        });
    }

}