package com.example.food.activities.ui.home;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.food.R;
import com.example.food.activities.ShowPopulerNoteActivity;
import com.example.food.adapters.PopulerNoteAdapter;
import com.example.food.models.PopulerNote;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import java.io.Serializable;
import java.util.ArrayList;


public class HomeFragment extends Fragment {
    private GridView gridView;
    private FirebaseFirestore firestore;
    private FirebaseStorage firebaseStorage;
    private PopulerNoteAdapter populerNoteAdapter;
    private ArrayList<PopulerNote> populerNotes = new ArrayList<>();
    private ProgressDialog progressDialog;

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        gridView = root.findViewById(R.id.fragment_home_gridview);

        firestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        populerNotes = getPopulerNotes();
        populerNoteAdapter = new PopulerNoteAdapter(getActivity(), populerNotes);
        gridView.setAdapter(populerNoteAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ShowPopulerNoteActivity.class);
                intent.putExtra("populerNote", (Serializable) populerNotes.get(position));
                startActivity(intent);
            }
        });
        return root;
    }

    public ArrayList<PopulerNote> getPopulerNotes(){
        showProgressDialog();
        ArrayList<PopulerNote> populerNotes = new ArrayList<>();
        firestore.collection("PopulerTarifler")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    progressDialog.dismiss();
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        populerNotes.add(new PopulerNote(documentSnapshot.getData().get("name").toString(),documentSnapshot.getData().get("type").toString(),
                                documentSnapshot.getData().get("img").toString(),documentSnapshot.getData().get("desc").toString()));
                    }
                    populerNoteAdapter.notifyDataSetChanged();
                }
            }
        });
        return populerNotes;
    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Yükleniyor...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

}