package com.example.salarycalculator;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UsersDao {
    @Insert
    void insertUser(User user);

    @Update
    void updateUser(User user);

    @Query("select * from user_table where id = 1")
    User getUser();
}
