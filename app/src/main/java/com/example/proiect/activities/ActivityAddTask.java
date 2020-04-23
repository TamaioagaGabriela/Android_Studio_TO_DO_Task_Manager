package com.example.proiect.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.proiect.R;
import com.example.proiect.fragments.FragmentAddTask;

public class ActivityAddTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        FragmentAddTask fragmentAddTask = new FragmentAddTask();
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.add(R.id.container_add_task, fragmentAddTask);
        ft.commit();
    }
}
