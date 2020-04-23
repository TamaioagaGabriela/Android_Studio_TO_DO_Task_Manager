package com.example.proiect.database;

import android.content.Context;

public class UserRepository {
    private AppDatabase appDatabase;

    public UserRepository(Context context){
        appDatabase = ApplicationController.getAppDatabase();
    }

    public void insertTask(final User user,
                           final OnUserRepositoryActionListener listener){
        new InsertTask(listener).execute(user);
    }

    public User getUserByTitleString (String title){
        return appDatabase.userDao().findByName(title);
    }

    public void deleteTask(final User user,
                           final OnUserRepositoryActionListener listener){
        new DeleteTask(listener).execute(user);
    }
    public void update(final User user, final OnUserRepositoryActionListener listener){
        new UpdateTask(listener).execute(user);
    }
}
