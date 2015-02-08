package com.doodeec.weather.android.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.doodeec.weather.android.database.model.LocationDBEntry;
import com.doodeec.weather.android.database.model.SimpleConditionDBEntry;

/**
 * @author Dusan Bartos
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    // increment the database version when changing the schema
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "wedr.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(LocationDBEntry.SQL_CREATE_ENTRIES);
        db.execSQL(SimpleConditionDBEntry.SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        int upgradeTo = oldVersion + 1;
        while (upgradeTo <= newVersion) {
            switch (upgradeTo) {
                case 2:
                    // add tables in V2 if necessary
                    break;
            }
            upgradeTo++;
        }
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
