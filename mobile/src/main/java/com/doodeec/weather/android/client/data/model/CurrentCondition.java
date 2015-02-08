package com.doodeec.weather.android.client.data.model;

import android.content.ContentValues;

import com.doodeec.weather.android.client.data.WindDirection;
import com.doodeec.weather.android.database.model.IDatabaseSavable;
import com.doodeec.weather.android.database.model.SimpleConditionDBEntry;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Dusan Bartos
 */
public class CurrentCondition extends JSONParser implements IDatabaseSavable {

    public static final String KEY_CONDITION = "current_condition";

    private static final String KEY_VALUE = "value";
    private static final String KEY_CLOUD_COVER = "cloudcover";
    private static final String KEY_FEELS_LIKE_C = "FeelsLikeC";
    private static final String KEY_FEELS_LIKE_F = "FeelsLikeF";
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

    private Integer mCloudCover;
    private Integer mFeelTempC;
    private Integer mFeelTempF;
    private Double mHumidity;
    private Integer mPrecipMM;
    private Double mPrecipInch;
    private Integer mPressure;
    private Integer mTempC;
    private Integer mTempF;
    private String mWeatherDescription;
    private String mWeatherIconUrl;
    private WindDirection mWindDir;
    private Integer mWindSpeedKm;
    private Integer mWindSpeedMi;
    private String mObservationTime;
    private long mTimestamp;

    /**
     * Current condition constructor from JSON definition
     * Beware that real data from server is a bit different from documentation at
     * http://www.worldweatheronline.com/api/docs/local-city-town-weather-api.aspx#current_condition_element
     *
     * @param jsonDefinition json object
     */
    public CurrentCondition(JSONObject jsonDefinition) throws JSONException {
        mCloudCover = getInt(jsonDefinition, KEY_CLOUD_COVER);
        mFeelTempC = getInt(jsonDefinition, KEY_FEELS_LIKE_C);
        mFeelTempF = getInt(jsonDefinition, KEY_FEELS_LIKE_F);
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

        /*try {
            mWeatherIconUrl = new URL(getString(jsonDefinition, KEY_WEATHER_ICON, ""));
        } catch (MalformedURLException e) {
            WedrLog.e(e.getMessage());
        }*/
        if (jsonDefinition.has(KEY_WEATHER_ICON)) {
            JSONObject iconObject = jsonDefinition.getJSONArray(KEY_WEATHER_ICON).getJSONObject(0);
            mWeatherIconUrl = getString(iconObject, KEY_VALUE);
        }
    }

    public String getObservationTime() {
        return mObservationTime;
    }

    public Integer getCloudCover() {
        return mCloudCover;
    }

    public Integer getFeelTempC() {
        return mFeelTempC;
    }

    public Integer getFeelTempF() {
        return mFeelTempF;
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

    //TODO validate URL
    /*public URL getIconURL() {
        return mWeatherIconUrl;
    }*/

    public String getIconURL() {
        return mWeatherIconUrl;
    }

    public void setTimestamp(long timestamp) {
        mTimestamp = timestamp;
    }

    public long getTimestamp() {
        return mTimestamp;
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(SimpleConditionDBEntry.COL_DESCRIPTION, mWeatherDescription);
        values.put(SimpleConditionDBEntry.COL_TEMP_C, mTempC);
        values.put(SimpleConditionDBEntry.COL_TEMP_F, mTempF);
        values.put(SimpleConditionDBEntry.COL_DATE, mTimestamp);
        return values;
    }

    @Override
    public String getTableName() {
        return SimpleConditionDBEntry.TABLE_NAME;
    }

    @Override
    public String getPrimaryColumnName() {
        return null;
    }

    @Override
    public String getPrimaryColumnValue() {
        return null;
    }
}
