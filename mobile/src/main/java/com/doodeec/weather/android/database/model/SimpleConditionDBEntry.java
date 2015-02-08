package com.doodeec.weather.android.database.model;

/**
 * @author Dusan Bartos
 */
public abstract class SimpleConditionDBEntry {

    public static final String TABLE_NAME = "condition";
    public static final String COL_ID = "id";
    public static final String COL_TEMP_C = "temp_c";
    public static final String COL_TEMP_F = "temp_f";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_DATE = "date";

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COL_ID + DBStorageStatic.TYPE_INT + " PRIMARY KEY," +
                    COL_TEMP_C + DBStorageStatic.TYPE_INT + "," +
                    COL_TEMP_F + DBStorageStatic.TYPE_INT + "," +
                    COL_DESCRIPTION + DBStorageStatic.TYPE_TEXT + "," +
                    COL_DATE + DBStorageStatic.TYPE_INT + ")";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;
}
