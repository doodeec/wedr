package com.doodeec.weather.android.client.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;

import com.doodeec.weather.android.WedrApplication;
import com.doodeec.weather.android.client.data.model.CurrentCondition;
import com.doodeec.weather.android.client.data.model.NearestLocation;
import com.doodeec.weather.android.database.DatabaseHelper;
import com.doodeec.weather.android.database.model.IDatabaseSavable;
import com.doodeec.weather.android.database.model.LocationDBEntry;
import com.doodeec.weather.android.database.model.SimpleConditionDBEntry;
import com.doodeec.weather.android.util.WedrLog;

import java.util.Observable;

/**
 * @author Dusan Bartos
 */
public class SessionData {

    private static SessionData sInstance;

    private WeatherData mWeatherData;
    private GeoLocation mGeoLocation;
    private long mLastUpdate;

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
                    sInstance.mWeatherData = new WeatherData();
                    sInstance.mGeoLocation = new GeoLocation();

                    SQLiteDatabase db = new DatabaseHelper(WedrApplication.getContext()).getReadableDatabase();
                    sInstance.loadStoredCondition(db);
                    if (sInstance.mWeatherData.getCondition() != null) {
                        sInstance.loadStoredLocation(db);
                    }
                    db.close();
                }
            }
        }
        return sInstance;
    }

    public void setWeatherData(WeatherData weatherData) {
        mWeatherData = weatherData;
        if (weatherData.getCondition() != null) {
            mLastUpdate = System.currentTimeMillis();
            mWeatherData.getCondition().setTimestamp(mLastUpdate);
        }

        // save data to database
        SQLiteDatabase db = new DatabaseHelper(WedrApplication.getContext()).getReadableDatabase();
        try {
            db.beginTransaction();
            // store location
            long locationId = saveContentToDb(db, weatherData.getNearestLocation(), true);
            if (locationId != -1) {
                weatherData.getCondition().setLocationId(locationId);
                // store simple condition information
                saveContentToDb(db, weatherData.getCondition(), false);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public WeatherData getWeatherData() {
        return mWeatherData;
    }

    public GeoLocation getGeoLocation() {
        return mGeoLocation;
    }

    public long getLastUpdateTimestamp() {
        return mLastUpdate;
    }

    private void loadStoredCondition(SQLiteDatabase db) {
        Cursor cursor = db.query(SimpleConditionDBEntry.TABLE_NAME, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            mWeatherData.setCondition(new CurrentCondition(cursor));
        }
    }

    private void loadStoredLocation(SQLiteDatabase db) {
        String[] args = {String.valueOf(mWeatherData.getCondition().getLocationId())};
        Cursor cursor = db.query(LocationDBEntry.TABLE_NAME, null, LocationDBEntry.COL_ID + " = ?", args, null, null, null);

        if (cursor.moveToFirst()) {
            mWeatherData.setNearestLocation(new NearestLocation(cursor));
        }
    }

    /**
     * Saves data to DB
     *
     * @param db              database instance
     * @param databaseSavable object to save
     * @param doNotUpdate     true if entry is "insert only", false if update is possible
     * @return row id
     */
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

    /**
     * Helper for storing geolocation data
     */
    public static class GeoLocation extends Observable {
        private boolean mOngoingLocationRequest;
        private Location mLocation;

        public GeoLocation() {
        }

        public void setLocation(Location location) {
            mLocation = location;
            setChanged();
            notifyObservers();
        }

        public void setOngoingRequest(boolean isActive) {
            mOngoingLocationRequest = isActive;
        }

        public boolean getOngoingRequest() {
            return mOngoingLocationRequest;
        }

        public Location getLocation() {
            return mLocation;
        }
    }
}
