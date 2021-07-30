package com.example.food.models;

import android.app.Activity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class Animations {

    public static void addAnimation(Activity activity , int veriable_id , int animation_id , long duration){
        //Delay olan animasyon
        Animation animation = AnimationUtils.loadAnimation(activity , animation_id);
        animation.setDuration(duration);
        activity.findViewById(veriable_id).startAnimation(animation);
    }

    public static void addAnimation(Activity activity , int veriable_id , int animation_id){
        //Delay olmayan gelen animasyon
        Animation animation = AnimationUtils.loadAnimation(activity , animation_id);
        activity.findViewById(veriable_id).startAnimation(animation);
    }
}

