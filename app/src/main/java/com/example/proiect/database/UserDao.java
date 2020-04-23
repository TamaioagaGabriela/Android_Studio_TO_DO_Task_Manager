package com.example.proiect.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.proiect.database.User;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE uid IN (:userIds)") // LIMIT 1
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE title LIKE :title " +
            "LIMIT 1")
    User findByName(String title);

    //@Query("UPDATE user SET position = :position WHERE title =:title")
    //User update(int position, String title);

    @Insert
    void insertAll(User...users);

    @Insert
    void insertTask(User user);

    @Delete
    void delete(User user);

    @Update
    void update(User user);

}