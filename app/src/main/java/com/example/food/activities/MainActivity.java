package com.example.food.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.food.R;
import com.example.food.adapters.FoodAdapter;

public class MainActivity extends AppCompatActivity {
    private GridView gridView;
    private String[] foods = {"Bamya" , "Bonfile" , "Kurufasulye" , "Pilav" , "Künefe" ,"Ördek"};
    private int[] images = {R.drawable.ic_launcher_background , R.drawable.ic_launcher_background ,
            R.drawable.ic_launcher_background , R.drawable.ic_launcher_background , R.drawable.ic_launcher_background ,
            R.drawable.ic_launcher_background};
    private FoodAdapter foodAdapter;

    public void init(){
        gridView = findViewById(R.id.main_activity_grid);
        foodAdapter = new FoodAdapter(foods , images,this);
        gridView.setAdapter(foodAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, foods[position], Toast.LENGTH_SHORT).show();
            }
        });
    }
}