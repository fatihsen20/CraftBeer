package com.example.food.activities.ui.notes;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.food.R;
import com.example.food.activities.AddNoteActivity;
import com.example.food.activities.NoteActivity;
import com.example.food.adapters.NoteAdapter;
import com.example.food.models.Note;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class NotesFragment extends Fragment {
    private GridView gridView;
    private NoteAdapter noteAdapter;
    private ArrayList<Note> notes = new ArrayList<>();
    private Button btnAddnote;
    private Intent intent;
    private String uId;
    private NotesViewModel notesViewModel;
    private FirebaseFirestore firestore;
    private ProgressDialog progressDialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notesViewModel =
                ViewModelProviders.of(this).get(NotesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notes, container, false);

        gridView = root.findViewById(R.id.fragment_notes_gridview);
        btnAddnote = root.findViewById(R.id.fragment_notes_btnAdd);

        firestore = FirebaseFirestore.getInstance();

        intent = getActivity().getIntent();
        uId = intent.getStringExtra("uId");

        notes = getData();
        noteAdapter = new NoteAdapter(getActivity() ,notes);
        gridView.setAdapter(noteAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                intent = new Intent(getActivity(), NoteActivity.class);
                intent.putExtra("title", notes.get(position).getTitle());
                intent.putExtra("desc", notes.get(position).getDesc());
                intent.putExtra("type", notes.get(position).getBeertype());
                getActivity().startActivity(intent);
            }
        });

        btnAddnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(), AddNoteActivity.class);
                intent.putExtra("uId", uId);
                getActivity().startActivity(intent);
            }
        });

        return root;
    }
    private void showProgressDialog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Yükleniyor...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public ArrayList<Note> getData() {
        showProgressDialog();
        final ArrayList<Note> mynotes = new ArrayList<>();
        firestore.collection("Tarifler")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.getId().contains(uId)){
                                    mynotes.add(new Note(document.getData().get("title").toString(),document.getData().get("desc").toString(),document.getData().get("beertype").toString()));
                                }
                            }
                            noteAdapter.notifyDataSetChanged();
                        }
                        else {
                                progressDialog.dismiss();
                                Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
        return mynotes;
    }
}
