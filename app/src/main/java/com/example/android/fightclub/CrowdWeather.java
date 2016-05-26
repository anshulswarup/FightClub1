package com.example.android.fightclub;

import com.firebase.client.Firebase;

/**
 * Created by Saxena on 5/25/2016.
 */
public class CrowdWeather extends android.app.Application {
    @Override
    public void onCreate(){
        super.onCreate();
        Firebase.setAndroidContext(this);

    }
}

