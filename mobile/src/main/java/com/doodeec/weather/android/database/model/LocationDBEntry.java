package com.doodeec.weather.android.database.model;

/**
 * @author Dusan Bartos
 */
public abstract class LocationDBEntry {

    public static final String TABLE_NAME = "location";
    public static final String COL_ID = "_id";
    public static final String COL_LATITUDE = "latitude";
    public static final String COL_LONGITUDE = "longitude";
    public static final String COL_REGION = "region";
    public static final String COL_COUNTRY = "country";

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COL_ID + DBStorageStatic.TYPE_INT + " PRIMARY KEY," +
                    COL_LATITUDE + DBStorageStatic.TYPE_NUMERIC + "," +
                    COL_LONGITUDE + DBStorageStatic.TYPE_NUMERIC + "," +
                    COL_REGION + DBStorageStatic.TYPE_TEXT + "," +
                    COL_COUNTRY + DBStorageStatic.TYPE_TEXT + ")";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;
}
