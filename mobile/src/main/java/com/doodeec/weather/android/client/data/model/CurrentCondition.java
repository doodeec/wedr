package com.doodeec.weather.android.client.data.model;

import android.content.ContentValues;
import android.database.Cursor;

import com.doodeec.weather.android.client.data.WindDirection;
import com.doodeec.weather.android.database.model.IDatabaseSavable;
import com.doodeec.weather.android.database.model.SimpleConditionDBEntry;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Dusan Bartos
 */
public class CurrentCondition extends JSONParser implements IDatabaseSavable {

    public static final String KEY_CONDITION = "current_condition";

    private static final String KEY_VALUE = "value";
    private static final String KEY_HUMIDITY = "humidity";
    private static final String KEY_OBSERVATION_TIME = "observation_time";
    private static final String KEY_PRECIP_MM = "precipMM";
    private static final String KEY_PRECIP_IN = "precipInches";
    private static final String KEY_PRESSURE = "pressure";
    private static final String KEY_TEMP_C = "temp_C";
    private static final String KEY_TEMP_F = "temp_F";
    private static final String KEY_WEATHER_DESC = "weatherDesc";
    private static final String KEY_WEATHER_ICON = "weatherIconUrl";
    private static final String KEY_WIND_DIRECTION = "winddir16Point";
    private static final String KEY_WIND_SPEED_KM = "windspeedKmph";
    private static final String KEY_WIND_SPEED_MI = "windspeedMiles";

    private Double mHumidity;
    private Integer mPrecipMM;
    private Double mPrecipInch;
    private Integer mPressure;
    private Integer mTempC;
    private Integer mTempF;
    private String mWeatherDescription;
    private URL mWeatherIconUrl;
    private WindDirection mWindDir;
    private Integer mWindSpeedKm;
    private Integer mWindSpeedMi;
    private String mObservationTime;
    private long mTimestamp;
    private long mLocationId = -1;

    /**
     * Current condition constructor from JSON definition
     * Beware that real data from server is a bit different from documentation at
     * http://www.worldweatheronline.com/api/docs/local-city-town-weather-api.aspx#current_condition_element
     *
     * @param jsonDefinition json object
     */
    public CurrentCondition(JSONObject jsonDefinition) throws JSONException, MalformedURLException {
        mHumidity = getDouble(jsonDefinition, KEY_HUMIDITY);
        mPrecipMM = getInt(jsonDefinition, KEY_PRECIP_MM);
        mPrecipInch = getDouble(jsonDefinition, KEY_PRECIP_IN);
        mPressure = getInt(jsonDefinition, KEY_PRESSURE);
        mTempC = getInt(jsonDefinition, KEY_TEMP_C);
        mTempF = getInt(jsonDefinition, KEY_TEMP_F);
        if (jsonDefinition.has(KEY_WEATHER_DESC)) {
            JSONObject descriptionObject = jsonDefinition.getJSONArray(KEY_WEATHER_DESC).getJSONObject(0);
            mWeatherDescription = getString(descriptionObject, KEY_VALUE);
        }
        mWindDir = WindDirection.forAbbreviation(getString(jsonDefinition, KEY_WIND_DIRECTION));
        mWindSpeedKm = getInt(jsonDefinition, KEY_WIND_SPEED_KM);
        mWindSpeedMi = getInt(jsonDefinition, KEY_WIND_SPEED_MI);
        mObservationTime = getString(jsonDefinition, KEY_OBSERVATION_TIME);

        if (jsonDefinition.has(KEY_WEATHER_ICON)) {
            JSONObject iconObject = jsonDefinition.getJSONArray(KEY_WEATHER_ICON).getJSONObject(0);
            mWeatherIconUrl = new URL(getString(iconObject, KEY_VALUE, ""));
        }
    }

    /**
     * Constructs simple condition from DB cursor
     *
     * @param cursor DB cursor
     */
    public CurrentCondition(Cursor cursor) {
        if (cursor == null || cursor.isClosed()) return;

        mHumidity = cursor.getDouble(cursor.getColumnIndex(SimpleConditionDBEntry.COL_HUMIDITY));
        mPressure = cursor.getInt(cursor.getColumnIndex(SimpleConditionDBEntry.COL_PRESSURE));
        mPrecipMM = cursor.getInt(cursor.getColumnIndex(SimpleConditionDBEntry.COL_PRECIP_MM));
        mPrecipInch = cursor.getDouble(cursor.getColumnIndex(SimpleConditionDBEntry.COL_PRECIP_IN));
        mTempC = cursor.getInt(cursor.getColumnIndex(SimpleConditionDBEntry.COL_TEMP_C));
        mTempF = cursor.getInt(cursor.getColumnIndex(SimpleConditionDBEntry.COL_TEMP_F));
        mWeatherDescription = cursor.getString(cursor.getColumnIndex(SimpleConditionDBEntry.COL_DESCRIPTION));
        mWindDir = WindDirection.forAbbreviation(
                cursor.getString(cursor.getColumnIndex(SimpleConditionDBEntry.COL_WIND_DIRECTION)));
        mWindSpeedKm = cursor.getInt(cursor.getColumnIndex(SimpleConditionDBEntry.COL_WIND_SPEED_KM));
        mWindSpeedMi = cursor.getInt(cursor.getColumnIndex(SimpleConditionDBEntry.COL_WIND_SPEED_MI));

        mLocationId = cursor.getLong(cursor.getColumnIndex(SimpleConditionDBEntry.COL_LOCATION_ID));
    }

    public String getObservationTime() {
        return mObservationTime;
    }

    public Double getHumidity() {
        return mHumidity;
    }

    public Integer getPrecipMM() {
        return mPrecipMM;
    }

    public Double getPrecipInch() {
        return mPrecipInch;
    }

    public Integer getPressure() {
        return mPressure;
    }

    public Integer getTempC() {
        return mTempC;
    }

    public Integer getTempF() {
        return mTempF;
    }

    public String getWeatherDescription() {
        return mWeatherDescription;
    }

    public WindDirection getWindDirection() {
        return mWindDir;
    }

    public Integer getWindSpeedKm() {
        return mWindSpeedKm;
    }

    public Integer getWindSpeedMi() {
        return mWindSpeedMi;
    }

    public URL getIconURL() {
        return mWeatherIconUrl;
    }

    public void setTimestamp(long timestamp) {
        mTimestamp = timestamp;
    }

    public long getTimestamp() {
        return mTimestamp;
    }

    public void setLocationId(long locationId) {
        mLocationId = locationId;
    }

    public long getLocationId() {
        return mLocationId;
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(SimpleConditionDBEntry.COL_LOCATION_ID, mLocationId);
        values.put(SimpleConditionDBEntry.COL_HUMIDITY, mHumidity);
        values.put(SimpleConditionDBEntry.COL_PRESSURE, mPressure);
        values.put(SimpleConditionDBEntry.COL_PRECIP_MM, mPrecipMM);
        values.put(SimpleConditionDBEntry.COL_PRECIP_IN, mPrecipInch);
        values.put(SimpleConditionDBEntry.COL_TEMP_C, mTempC);
        values.put(SimpleConditionDBEntry.COL_TEMP_F, mTempF);
        values.put(SimpleConditionDBEntry.COL_DESCRIPTION, mWeatherDescription);
        values.put(SimpleConditionDBEntry.COL_WIND_DIRECTION, mWindDir.getAbbreviation());
        values.put(SimpleConditionDBEntry.COL_WIND_SPEED_KM, mWindSpeedKm);
        values.put(SimpleConditionDBEntry.COL_WIND_SPEED_MI, mWindSpeedMi);
        values.put(SimpleConditionDBEntry.COL_DATE, mTimestamp);
        return values;
    }

    @Override
    public String getTableName() {
        return SimpleConditionDBEntry.TABLE_NAME;
    }

    /**
     * Strategy for storing Current condition is to always keep only one entry in the DB
     * it is the last known condition, so it can be shown right when application is started
     */
    @Override
    public String getPrimaryColumnName() {
        return SimpleConditionDBEntry.COL_ID;
    }

    @Override
    public String getPrimaryColumnValue() {
        return String.valueOf(1);
    }
}
