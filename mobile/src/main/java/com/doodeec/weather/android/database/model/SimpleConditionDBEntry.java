package com.doodeec.weather.android.database.model;

/**
 * @author Dusan Bartos
 */
public abstract class SimpleConditionDBEntry {

    public static final String TABLE_NAME = "condition";
    public static final String COL_ID = "_id";
    public static final String COL_LOCATION_ID = "location_id";
    public static final String COL_HUMIDITY = "humidity";
    public static final String COL_PRESSURE = "pressure";
    public static final String COL_PRECIP_MM = "precip_mm";
    public static final String COL_PRECIP_IN = "precip_in";
    public static final String COL_TEMP_C = "temp_c";
    public static final String COL_TEMP_F = "temp_f";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_WIND_DIRECTION = "wind_direction";
    public static final String COL_WIND_SPEED_KM = "wind_speed_km";
    public static final String COL_WIND_SPEED_MI = "wind_speed_mi";
    public static final String COL_DATE = "date";

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COL_ID + DBStorageStatic.TYPE_INT + " PRIMARY KEY," +
                    COL_LOCATION_ID + DBStorageStatic.TYPE_INT + "," +
                    COL_HUMIDITY + DBStorageStatic.TYPE_NUMERIC + "," +
                    COL_PRESSURE + DBStorageStatic.TYPE_INT + "," +
                    COL_PRECIP_MM + DBStorageStatic.TYPE_INT + "," +
                    COL_PRECIP_IN + DBStorageStatic.TYPE_NUMERIC + "," +
                    COL_TEMP_C + DBStorageStatic.TYPE_INT + "," +
                    COL_TEMP_F + DBStorageStatic.TYPE_INT + "," +
                    COL_DESCRIPTION + DBStorageStatic.TYPE_TEXT + "," +
                    COL_WIND_DIRECTION + DBStorageStatic.TYPE_TEXT + "," +
                    COL_WIND_SPEED_KM + DBStorageStatic.TYPE_INT + "," +
                    COL_WIND_SPEED_MI + DBStorageStatic.TYPE_INT + "," +
                    COL_DATE + DBStorageStatic.TYPE_INT + ")";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;
}
