package com.example.jegansbeast.fazt;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by JEGAN'S BEAST on 6/19/2016.
 */
public class FaztDatabase extends SQLiteOpenHelper {

    public FaztDatabase(Context context,int version) {
        super(context, "faztdb", null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
