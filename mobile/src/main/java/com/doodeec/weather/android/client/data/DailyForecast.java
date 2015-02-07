package com.doodeec.weather.android.client.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Dusan Bartos
 */
public class DailyForecast extends JSONParser {

    public static final String KEY_WEATHER = "weather";

    private static final String KEY_DATE = "date";
    private static final String KEY_MAX_TEMP_C = "maxtempC";
    private static final String KEY_MAX_TEMP_F = "maxtempF";
    private static final String KEY_MIN_TEMP_C = "mintempC";
    private static final String KEY_MIN_TEMP_F = "mintempF";
    private static final String KEY_UV_INDEX = "uvIndex";

    private Date mDate;
    private Integer mMaxTempC;
    private Integer mMaxTempF;
    private Integer mMinTempC;
    private Integer mMinTempF;
    private Integer mUVIndex;
    private List<HourlyForecast> mHourlyForecastList;
    private AstronomicalForecast mAstrForecast;

    public DailyForecast(JSONObject jsonDefinition) throws JSONException {
        //TODO get date
        mMaxTempC = getInt(jsonDefinition, KEY_MAX_TEMP_C);
        mMaxTempF = getInt(jsonDefinition, KEY_MAX_TEMP_F);
        mMinTempC = getInt(jsonDefinition, KEY_MIN_TEMP_C);
        mMinTempF = getInt(jsonDefinition, KEY_MIN_TEMP_F);
        mUVIndex = getInt(jsonDefinition, KEY_UV_INDEX);

        if (jsonDefinition.has(AstronomicalForecast.KEY_ASTRONOMY)) {
            mAstrForecast = new AstronomicalForecast(
                    jsonDefinition.getJSONArray(AstronomicalForecast.KEY_ASTRONOMY).getJSONObject(0));
        }

        mHourlyForecastList = new ArrayList<>();
        if (jsonDefinition.has(HourlyForecast.KEY_HOURLY)) {
            JSONArray hourlyDefinition = jsonDefinition.getJSONArray(HourlyForecast.KEY_HOURLY);
            for (int i = 0; i < hourlyDefinition.length(); i++) {
                mHourlyForecastList.add(new HourlyForecast(hourlyDefinition.getJSONObject(i)));
            }
        }
    }

    public Date getDate() {
        return mDate;
    }

    public Integer getMaxTempC() {
        return mMaxTempC;
    }

    public Integer getMaxTempF() {
        return mMaxTempF;
    }

    public Integer getMinTempC() {
        return mMinTempC;
    }

    public Integer getMinTempF() {
        return mMinTempF;
    }

    public Integer getUVIndex() {
        return mUVIndex;
    }

    public List<HourlyForecast> getHourlyForecast() {
        return mHourlyForecastList;
    }

    public AstronomicalForecast getAstrForecast() {
        return mAstrForecast;
    }
}
