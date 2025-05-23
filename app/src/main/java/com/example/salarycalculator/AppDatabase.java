package com.example.salarycalculator;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class, Payslip.class}, version = 1)
public abstract class AppDatabase  extends RoomDatabase {
    private static AppDatabase instance;
    public abstract UsersDao usersDao();
    public abstract PayslipsDao payslipsDao();

    public static synchronized AppDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "app_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }

}
