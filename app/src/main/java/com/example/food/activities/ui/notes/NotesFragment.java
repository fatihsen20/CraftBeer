package com.example.food.activities.ui.notes;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.food.R;
import com.example.food.activities.NoteActivity;
import com.example.food.adapters.NoteAdapter;
import com.example.food.models.Note;

import java.util.ArrayList;

public class NotesFragment extends Fragment {
    private GridView gridView;
    private NoteAdapter noteAdapter;
    private ArrayList<Note> notes = new ArrayList<>();


    private NotesViewModel notesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notesViewModel =
                ViewModelProviders.of(this).get(NotesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notes, container, false);

        gridView = root.findViewById(R.id.fragment_notes_gridview);

        addNote();
        noteAdapter = new NoteAdapter(getActivity() , notes);
        gridView.setAdapter(noteAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(), NoteActivity.class);
                intent.putExtra("title", notes.get(position).getTitle());
                intent.putExtra("image" , notes.get(position).getImage());
                intent.putExtra("desc", notes.get(position).getDesc());
                getActivity().startActivity(intent);
            }
        });


        return root;
    }

    public void addNote(){
        notes.add(new Note("Coca Cola Cinger Beer", "jsdkgfskjodgfksdjgfkdsjlgfdskjgfdskjgdjgdkjgddfhfgdhdfgjhfdgjfhgjdfhjhfjfjfghfjgjghfjghkjgdfkjhkjhdfghdfkjhgdfjkhgdfj","Lager",R.drawable.corona));
        notes.add(new Note("Blonde Ale", "jsdkgfskjodgfksdjgfkdsjlgfdskjgfdskjgdjgdkjgdkjgdfkjhkjhdfghdfkjhgdfjkhgdfj","Ale", R.drawable.guinness));
    }

}