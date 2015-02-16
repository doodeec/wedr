package com.doodeec.weather.android.client.data;

import com.doodeec.weather.android.client.data.model.CurrentCondition;
import com.doodeec.weather.android.client.data.model.DailyForecast;
import com.doodeec.weather.android.client.data.model.NearestLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * @author Dusan Bartos
 */
public class WeatherData extends Observable {

    public static final String KEY_DATA = "data";
    public static final String KEY_ERROR = "error";
    public static final String KEY_MESSAGE = "msg";

    private NearestLocation mNearestLocation;
    private CurrentCondition mCurCondition;
    private List<DailyForecast> mDailyForecastList;

    public WeatherData() {
        mDailyForecastList = new ArrayList<>();
    }

    public void setNearestLocation(NearestLocation location) {
        if (mNearestLocation != location) {
            mNearestLocation = location;
            setChanged();
            notifyObservers();
        }
    }

    public NearestLocation getNearestLocation() {
        return mNearestLocation;
    }

    public void setCondition(CurrentCondition condition) {
        if (mCurCondition != condition) {
            mCurCondition = condition;
            setChanged();
            notifyObservers();
        }
    }

    public CurrentCondition getCondition() {
        return mCurCondition;
    }

    public void clearForecast() {
        if (mDailyForecastList.size() != 0) {
            mDailyForecastList.clear();
            setChanged();
            notifyObservers();
        }
    }

    public void addForecast(DailyForecast forecast) {
        mDailyForecastList.add(forecast);
        setChanged();
        notifyObservers();
    }

    public List<DailyForecast> getForecast() {
        return mDailyForecastList;
    }
}
