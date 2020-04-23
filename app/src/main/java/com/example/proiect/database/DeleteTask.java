package com.example.proiect.database;

import android.os.AsyncTask;

public class DeleteTask extends AsyncTask<User, Void, Void> {

    OnUserRepositoryActionListener listener;

    DeleteTask(OnUserRepositoryActionListener listener) {
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(User... users) {
        ApplicationController.getAppDatabase().userDao().delete(users[0]);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        listener.actionSucces();
    }
}