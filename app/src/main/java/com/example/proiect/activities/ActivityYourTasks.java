package com.example.proiect.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.proiect.clickListener.ClickJson;
import com.example.proiect.clickListener.ClickListenerSecond;
import com.example.proiect.clickListener.ClickListenerThird;
import com.example.proiect.R;
import com.example.proiect.fragments.FragmentTask;
import com.example.proiect.fragments.FragmentTaskDoing;
import com.example.proiect.fragments.FragmentTaskDone;

public class ActivityYourTasks extends AppCompatActivity implements ClickListenerSecond, ClickListenerThird {

    private ImageButton button_add;
    private ClickJson clickJson;

    public void setActivityListener(ClickJson clickJson){
        this.clickJson = clickJson;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_tasks);

        FragmentTask fragmentTaskToDo = new FragmentTask(this);
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.add(R.id.container_to_do, fragmentTaskToDo);

        FragmentTaskDoing fragmentTaskDoing = new FragmentTaskDoing(this);
        ft.add(R.id.container_doing, fragmentTaskDoing);

        FragmentTaskDone fragmentTaskDone = new FragmentTaskDone();
        ft.add(R.id.container_done, fragmentTaskDone);

        ft.commit();

        button_add = findViewById(R.id.btn_add_new_task);
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityAddTask();
                finish();
            }
        });

    }

    public void openActivityAddTask(){
        Intent intent = new Intent(this, ActivityAddTask.class);
        startActivity(intent);
    }

    @Override
    public void onClickListenerForSecond(String title, String description) {
        //Toast.makeText(getApplicationContext(), title+description, Toast.LENGTH_SHORT).show();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("description", description);
        FragmentTaskDoing fragmentTaskDoing = new FragmentTaskDoing(this);
        fragmentTaskDoing.setArguments(args);
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container_doing, fragmentTaskDoing).commit();

    }

    @Override
    public void onClickListenerForThird(String title, String description) {
        //Toast.makeText(this, "Am ajuns sa fac cu bundle", Toast.LENGTH_SHORT).show();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("description", description);
        FragmentTaskDone fragmentTaskDone = new FragmentTaskDone();
        fragmentTaskDone.setArguments(args);
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container_done, fragmentTaskDone).commit();
    }

    public void addJsonButton(View view) {
        clickJson.onClickJson();
    }
}