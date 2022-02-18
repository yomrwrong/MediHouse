package com.example.medihouse.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ProfileDao {

    @Insert
    void insertNewUser(User user);

    @Query("SELECT * FROM user_table")
    LiveData<List<User>> getAllUsers();
}
