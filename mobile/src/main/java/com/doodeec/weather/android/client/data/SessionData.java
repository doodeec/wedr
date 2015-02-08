package com.doodeec.weather.android.client.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.doodeec.weather.android.WedrApplication;
import com.doodeec.weather.android.database.DBHelper;
import com.doodeec.weather.android.database.model.IDatabaseSavable;
import com.doodeec.weather.android.util.WedrLog;

/**
 * @author Dusan Bartos
 */
public class SessionData {

    private static SessionData sInstance;

    private WeatherData mWeatherData;

    /**
     * Gets session data singleton
     * synchronized double null check for thread safety
     *
     * @return session instance
     */
    public static SessionData getInstance() {
        if (sInstance == null) {
            synchronized (SessionData.class) {
                if (sInstance == null) {
                    sInstance = new SessionData();
                }
            }
        }
        return sInstance;
    }

    public void setWeatherData(WeatherData weatherData) {
        mWeatherData = weatherData;
        mWeatherData.getCondition().setTimestamp(System.currentTimeMillis());

        //TODO store some data in DB
        SQLiteDatabase db = new DBHelper(WedrApplication.getContext()).getReadableDatabase();
        try {
            db.beginTransaction();
            // store location
            SessionData.getInstance().saveContentToDb(db, weatherData.getNearestLocation(), true);
            // store simple condition information
            SessionData.getInstance().saveContentToDb(db, weatherData.getCondition(), false);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public WeatherData getWeatherData() {
        return mWeatherData;
    }

    private long saveContentToDb(SQLiteDatabase db, IDatabaseSavable databaseSavable, boolean doNotUpdate) {
        ContentValues values = databaseSavable.getContentValues();
        long newRowId;

        if (doNotUpdate) {
            newRowId = db.insert(databaseSavable.getTableName(), null, values);
            WedrLog.d("Inserted " + databaseSavable.getClass().getSimpleName() + " newRowId: " + newRowId);
        } else {
            String[] column = {databaseSavable.getPrimaryColumnName()};
            String[] args = {databaseSavable.getPrimaryColumnValue()};
            Cursor c = db.query(databaseSavable.getTableName(), column, databaseSavable.getPrimaryColumnName() + " = ?", args, null, null, null);

            // update rather than create new entry
            if (c.moveToFirst()) {
                newRowId = db.update(databaseSavable.getTableName(), values, databaseSavable.getPrimaryColumnName() + " = ?", args);

                if (newRowId == 0) {
                    newRowId = -1;
                }

                WedrLog.d("Updated " + databaseSavable.getClass().getSimpleName() + " : " + databaseSavable.getPrimaryColumnValue());
            } else {
                newRowId = db.insert(databaseSavable.getTableName(), null, values);
                WedrLog.d("Inserted " + databaseSavable.getClass().getSimpleName() + " newRowId: " + newRowId);
            }

            c.close();
        }

        return newRowId;
    }
}
