package com.example.proiect.database;

import android.os.AsyncTask;

public class UpdateTask extends AsyncTask<User, Void, Void> {
    OnUserRepositoryActionListener listener;
    private AppDatabase appData;

    UpdateTask(OnUserRepositoryActionListener listener) {
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(User... users) {
        ApplicationController.getAppDatabase().userDao().update(users[0]);
        //appData.userDao().insertTask(users[0]);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        listener.actionSucces();
    }
}
