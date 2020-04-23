package com.example.proiect.modelTasks;

public class ExampleTaskToDo {
    String mTitle;
    String mDescription;

    public ExampleTaskToDo(){}

    public ExampleTaskToDo(String title, String description){
        mTitle = title;
        mDescription = description;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }
}
