package com.example.proiect.database;

import android.app.Application;

import androidx.room.Room;

public class ApplicationController extends Application {
    private static ApplicationController mInstance;

    private static AppDatabase mAppDatabase;

    public static ApplicationController getInstance(){
        return  mInstance;
    }

    @Override
    public void onCreate() {

        super.onCreate();

        mInstance  = this;

        mAppDatabase = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "db-users").allowMainThreadQueries().build();
    }

    public static AppDatabase getAppDatabase(){
        return mAppDatabase;
    }
}
