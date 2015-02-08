package com.doodeec.weather.android;

/**
 * Config for the application
 *
 * @author Dusan Bartos
 */
public class WedrConfig {

    /**
     * http://www.worldweatheronline.com/ API key
     */
    public static final String API_KEY = "737651555e3dccd242820f9cdf82e";

    /**
     * Number of days to read forecast for
     */
    public static final int FORECAST_DAYS = 5;

    /**
     * Enables/disables logCat logging
     */
    public static final boolean ENABLE_LOGS = true;

    /**
     * Helpers for temperature formatting
     */
    public static final String TEMP_FORMAT_C = "%d°C";
    public static final String TEMP_FORMAT_F = "%dF";
    public static final String HUMIDITY_FORMAT = "%.0f%%";
    public static final String PRECIPITATION_FORMAT_MM = "%.1f mm";
    public static final String PRECIPITATION_FORMAT_IN = "%.1f in";
    public static final String PRESSURE_FORMAT = "%d hPa";
    public static final String WIND_SPEED_FORMAT_KM = "%d km/h";
    public static final String WIND_SPEED_FORMAT_MI = "%d mph";
}
