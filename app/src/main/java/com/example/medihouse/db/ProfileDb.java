package com.example.medihouse.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class}, version = 1)
public abstract class ProfileDb extends RoomDatabase {

    public static ProfileDb instance;

    public abstract ProfileDao profileDao();

    public static synchronized ProfileDb getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ProfileDb.class,"ProfileDb")
                    .build();
        }
        return instance;
    }

}
