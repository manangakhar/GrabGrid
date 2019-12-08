package com.example.grabgrid.Constants;

import android.content.Context;

import com.example.grabgrid.Handler.SQLiteDatabaseHandler;
import com.example.grabgrid.Model.User;

public class Constants {

    public static SQLiteDatabaseHandler db;

    public static void initialize(Context context){

        db = new SQLiteDatabaseHandler(context);
        //db.onUpgrade(db.getWritableDatabase(), 0, 1);
    }
    
}
