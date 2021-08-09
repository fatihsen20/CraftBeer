package com.example.food.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.food.R;
import com.example.food.models.PopulerNote;

import java.util.ArrayList;

public class PopulerNoteAdapter extends BaseAdapter {

    private ArrayList<PopulerNote> populerNotes;
    private TextView noteName;
    private TextView noteType;
    private LayoutInflater inflater;

    public PopulerNoteAdapter(Activity activity, ArrayList<PopulerNote> populerNotes){
        this.populerNotes = populerNotes;
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
       return populerNotes.size();
    }

    @Override
    public Object getItem(int position) {
        return populerNotes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.custom_note,null);
        if (convertView != null){
            noteName = convertView.findViewById(R.id.custom_note_title);
            noteType = convertView.findViewById(R.id.custom_note_beerType);

            PopulerNote populerNote = populerNotes.get(position);

            noteName.setText(populerNote.getName());
            noteType.setText(populerNote.getType());
        }
        return convertView;
    }
}
