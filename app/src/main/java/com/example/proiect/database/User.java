package com.example.proiect.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "username")
    public String username;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "position")
    public int position;

    public User(String username, String title, String description, int position) {
        this.username = username;
        this.title = title;
        this.description = description;
        this.position = position;
    }
}