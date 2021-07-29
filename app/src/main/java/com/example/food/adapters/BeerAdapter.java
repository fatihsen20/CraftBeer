package com.example.food.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.food.R;
import com.example.food.models.Beer;

import java.util.ArrayList;

public class BeerAdapter extends BaseAdapter {
    private ArrayList<Beer> beers;
    private ImageView beerImage;
    private TextView beerName;
    private TextView beerDesc;
    private LayoutInflater inflater;

    public BeerAdapter(Activity activity , ArrayList<Beer> beers){
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.beers = beers;
    }


    @Override
    public int getCount() {
        return beers.size();
    }

    @Override
    public Object getItem(int position) {
        return beers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = inflater.inflate(R.layout.custom_beer,null);
        if (convertView != null){
            beerName = convertView.findViewById(R.id.custom_beer_name);
            beerDesc = convertView.findViewById(R.id.custom_beer_description);
            beerImage = convertView.findViewById(R.id.custom_beer_beerImage);

            Beer beer = beers.get(position);
            beerName.setText(beer.getName());
            beerDesc.setText(beer.getDescription());
            beerImage.setBackgroundResource(beer.getImage());

        }
        return convertView;
    }
}
