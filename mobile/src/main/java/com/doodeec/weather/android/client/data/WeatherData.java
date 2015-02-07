package com.doodeec.weather.android.client.data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dusan Bartos
 */
public class WeatherData extends JSONParser {

    public static final String KEY_DATA = "data";

    private CurrentCondition mCurCondition;
    private List<DailyForecast> mDailyForecastList;

    public WeatherData() {
        mDailyForecastList = new ArrayList<>();
    }

    public void setCondition(CurrentCondition condition) {
        mCurCondition = condition;
    }

    public CurrentCondition getCondition() {
        return mCurCondition;
    }

    public void clearForecast() {
        mDailyForecastList.clear();
    }

    public void addForecast(DailyForecast forecast) {
        mDailyForecastList.add(forecast);
    }

    public List<DailyForecast> getForecast() {
        return mDailyForecastList;
    }
}
