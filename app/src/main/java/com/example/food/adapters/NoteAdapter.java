package com.example.food.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.food.R;
import com.example.food.models.Beer;
import com.example.food.models.Note;

import java.util.ArrayList;

public class NoteAdapter extends BaseAdapter {

    private ArrayList<Note> notes;
    private LayoutInflater inflater;

    public NoteAdapter(Activity activity , ArrayList<Note> notes){
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.notes = notes;
    }


    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public Object getItem(int position) {
        return notes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView title, beerType;
        ImageView beerPhoto;

        convertView = inflater.inflate(R.layout.custom_note,null);
        if (convertView != null){
            title = convertView.findViewById(R.id.custom_note_title);
            beerType = convertView.findViewById(R.id.custom_note_beerType);

            Note note = notes.get(position);
            title.setText(note.getTitle());
            beerType.setText(note.getBeertype());

        }
        return convertView;
    }
}
