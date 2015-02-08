package com.doodeec.weather.android.client.data;

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
    }

    public WeatherData getWeatherData() {
        return mWeatherData;
    }
}
