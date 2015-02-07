package com.doodeec.weather.android.client.data;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Dusan Bartos
 */
public class WeatherData extends JSONParser {

    public static final String KEY_WEATHER = "weather";

    private static final String KEY_DATE = "date";
    private static final String KEY_MAX_TEMP_C = "maxtempC";
    private static final String KEY_MAX_TEMP_F = "maxtempF";
    private static final String KEY_MIN_TEMP_C = "mintempC";
    private static final String KEY_MIN_TEMP_F = "mintempF";
    private static final String KEY_UV_INDEX = "uvIndex";

    private CurrentCondition mCurCondition;
    private AstronomicalForecast mAstrForecast;
    private List<HourlyForecast> mHourlyForecastList;
    private Date mDate;
    private Integer mMaxTempC;
    private Integer mMaxTempF;
    private Integer mMinTempC;
    private Integer mMinTempF;
    private Integer mUVIndex;

    public WeatherData(JSONObject jsonDefinition) {
        mHourlyForecastList = new ArrayList<>();
    }

    public void setCondition(CurrentCondition condition) {
        mCurCondition = condition;
    }

    public CurrentCondition getCondition() {
        return mCurCondition;
    }

    public void setAstrForecast(AstronomicalForecast forecast) {
        mAstrForecast = forecast;
    }

    public AstronomicalForecast getAstrForecast() {
        return mAstrForecast;
    }

    public void clearForecast() {
        mHourlyForecastList.clear();
    }

    public void addForecast(HourlyForecast forecast) {
        mHourlyForecastList.add(forecast);
    }

    public List<HourlyForecast> getForecast() {
        return mHourlyForecastList;
    }
}
