package com.example.grabgrid.Constants;

import android.content.Context;

import com.example.grabgrid.Handler.SQLiteDatabaseHandler;
import com.example.grabgrid.Model.User;

public class Constants {

    public static SQLiteDatabaseHandler db;

    public static void initialize(Context context){

        db = new SQLiteDatabaseHandler(context);

        //create one user
        User userDefault = new User();
        userDefault.setUsername("m2l2");
        userDefault.setPassword("password");
        Constants.db.addUser(userDefault);
        //db.onUpgrade(db.getWritableDatabase(), 0, 1);
    }
    
}
