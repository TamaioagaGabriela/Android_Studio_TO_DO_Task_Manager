package com.example.proiect.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


import androidx.core.app.NotificationCompat;

import static com.example.proiect.fragments.FragmentAddTask.EXTRA_DESCRIPTION_TASK;
import static com.example.proiect.fragments.FragmentAddTask.EXTRA_TITLE_TASK;

public class AlarmReceiver extends BroadcastReceiver {

    private NotificationHelper mNotificationHelper;
    private Context mContext;



    @Override
    public void onReceive(Context context, Intent intent) {
            mContext = context;

            mNotificationHelper = new NotificationHelper(context);
            NotificationCompat.Builder nb = mNotificationHelper.getChannelNotification("TO DO: " + EXTRA_TITLE_TASK, EXTRA_DESCRIPTION_TASK);
            mNotificationHelper.getManager().notify(1, nb.build());

    }
}

