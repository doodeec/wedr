package com.doodeec.weather.android.client.data;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Dusan Bartos
 */
public class HourlyForecast extends JSONParser {

    public static final String KEY_HOURLY = "hourly";

    private static final String KEY_TIME = "time";
    private static final String KEY_TEMP_C = "tempC";
    private static final String KEY_TEMP_F = "tempF";
    private static final String KEY_WIND_SPEED_KM = "windspeedKmph";
    private static final String KEY_WIND_SPEED_MS = "windspeedMeterSec";
    private static final String KEY_WIND_SPEED_MI = "windspeedMiles";
    private static final String KEY_WIND_SPEED_KN = "windspeedKnots";
    private static final String KEY_WIND_DIR_DEG = "winddirDegree";
    private static final String KEY_WIND_DIR = "winddir16Point";
    private static final String KEY_WEATHER_CODE = "weatherCode";
    private static final String KEY_WEATHER_DESC = "weatherDesc";
    private static final String KEY_WEATHER_ICON_URL = "weatherIconUrl";
    private static final String KEY_PRECIP_MM = "precipMM";
    private static final String KEY_PRECIP_IN = "precipInches";
    private static final String KEY_HUMIDITY = "humidity";
    private static final String KEY_VISIBILITY_KM = "visibility";
    private static final String KEY_VISIBILITY_MI = "visibilityMiles";
    private static final String KEY_PRESSURE = "pressure";
    private static final String KEY_PRESSURE_IN = "pressureInches";
    private static final String KEY_CLOUD_COVER = "cloudcover";
    private static final String KEY_RAIN_CHANCE = "chanceofrain";
    private static final String KEY_WINDY_CHANCE = "chanceofwindy";
    private static final String KEY_OVERCAST_CHANCE = "chanceofovercast";
    private static final String KEY_SUNNY_CHANCE = "chanceofsunny";
    private static final String KEY_FROST_CHANCE = "chanceoffrost";
    private static final String KEY_FOG_CHANCE = "chanceoffog";
    private static final String KEY_SNOW_CHANCE = "chanceofsnow";
    private static final String KEY_THUNDER_CHANCE = "chanceofthunder";
    
    public HourlyForecast(JSONObject jsonDefinition) throws JSONException {
        //TODO parse hourly forecast
    }
}
