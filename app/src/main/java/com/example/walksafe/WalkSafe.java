package com.example.walksafe;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class WalkSafe extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
