package com.example.food.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.food.R;

public class FoodAdapter extends ArrayAdapter<String> {
    private String[] Foods;
    private int[] Images;
    private ImageView foodImage;
    private TextView foodName;
    Context context;

    public FoodAdapter(String[] Foods, int[] Images, Context context){
        super(context, R.layout.food , Foods);
        this.Foods = Foods;
        this.Images = Images;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.food , null);
        if (v!=null){
            foodName = v.findViewById(R.id.food_textFood);
            foodImage = v.findViewById(R.id.food_Imagefood);

            foodName.setText(Foods[position]);
            foodImage.setBackgroundResource(Images[position]);
        }

        return v;
    }
}
