package com.doodeec.weather.android.client.data.model;

import android.content.ContentValues;

import com.doodeec.weather.android.database.model.IDatabaseSavable;
import com.doodeec.weather.android.database.model.LocationDBEntry;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Dusan Bartos
 */
public class NearestLocation extends JSONParser implements IDatabaseSavable {

    public static final String KEY_NEAREST = "nearest_area";

    private static final String KEY_VALUE = "value";

    private static final String KEY_COUNTRY = "country";
    private static final String KEY_REGION = "region";
    private static final String KEY_GEO_LAT = "latitude";
    private static final String KEY_GEO_LON = "longitude";

    private String mCountry;
    private String mRegion;
    private Double mLat;
    private Double mLon;

    public NearestLocation(JSONObject jsonDefinition) throws JSONException {
        mLat = getDouble(jsonDefinition, KEY_GEO_LAT);
        mLon = getDouble(jsonDefinition, KEY_GEO_LON);

        if (jsonDefinition.has(KEY_COUNTRY)) {
            JSONObject countryObject = jsonDefinition.getJSONArray(KEY_COUNTRY).getJSONObject(0);
            mCountry = getString(countryObject, KEY_VALUE);
        }

        if (jsonDefinition.has(KEY_REGION)) {
            JSONObject regionObject = jsonDefinition.getJSONArray(KEY_REGION).getJSONObject(0);
            mRegion = getString(regionObject, KEY_VALUE);
        }
    }

    public String getCountry() {
        return mCountry;
    }

    public String getRegion() {
        return mRegion;
    }

    public Double getLatitude() {
        return mLat;
    }

    public Double getLongitude() {
        return mLon;
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(LocationDBEntry.COL_LATITUDE, mLat);
        values.put(LocationDBEntry.COL_LONGITUDE, mLon);
        values.put(LocationDBEntry.COL_REGION, mRegion);
        values.put(LocationDBEntry.COL_COUNTRY, mCountry);
        return values;
    }

    @Override
    public String getTableName() {
        return LocationDBEntry.TABLE_NAME;
    }

    @Override
    public String getPrimaryColumnName() {
        return ""; // only insert strategy, does not need update parameters
    }

    @Override
    public String getPrimaryColumnValue() {
        return ""; // only insert strategy, does not need update parameters
    }
}
