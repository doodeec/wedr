package com.doodeec.weather.android.client.data.model;

import com.doodeec.weather.android.client.data.WindDirection;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Dusan Bartos
 */
public class HourlyForecast extends JSONParser {

    public static final String KEY_HOURLY = "hourly";

    private static final String KEY_VALUE = "value";

    private static final String KEY_TIME = "time";
    private static final String KEY_TEMP_C = "tempC";
    private static final String KEY_TEMP_F = "tempF";
    private static final String KEY_WIND_SPEED_KM = "windspeedKmph";
    private static final String KEY_WIND_SPEED_MI = "windspeedMiles";
    private static final String KEY_WIND_DIR = "winddir16Point";
    private static final String KEY_WEATHER_DESC = "weatherDesc";
    private static final String KEY_WEATHER_ICON_URL = "weatherIconUrl";
    private static final String KEY_PRECIP_MM = "precipMM";
    private static final String KEY_PRECIP_IN = "precipInches";
    private static final String KEY_HUMIDITY = "humidity";
    private static final String KEY_PRESSURE = "pressure";
    private static final String KEY_CLOUD_COVER = "cloudcover";
    private static final String KEY_RAIN_CHANCE = "chanceofrain";
    private static final String KEY_WINDY_CHANCE = "chanceofwindy";
    private static final String KEY_OVERCAST_CHANCE = "chanceofovercast";
    private static final String KEY_SUNNY_CHANCE = "chanceofsunny";
    private static final String KEY_FROST_CHANCE = "chanceoffrost";
    private static final String KEY_FOG_CHANCE = "chanceoffog";
    private static final String KEY_SNOW_CHANCE = "chanceofsnow";
    private static final String KEY_THUNDER_CHANCE = "chanceofthunder";

    private String mTime;
    private Integer mTempC;
    private Integer mTempF;
    private Integer mWindSpeedKm;
    private Integer mWindSpeedMi;
    private WindDirection mWindDirection;
    private String mWeatherDesc;
    private String mWeatherIconUrl;
    private Double mPrecipMM;
    private Double mPrecipIn;
    private Integer mHumidity;
    private Integer mPressure;
    private Integer mCloudCover;
    private Integer mChanceRain;
    private Integer mChanceWindy;
    private Integer mChanceOvercast;
    private Integer mChanceSunny;
    private Integer mChanceFrost;
    private Integer mChanceFog;
    private Integer mChanceSnow;
    private Integer mChanceThunder;

    public HourlyForecast(JSONObject jsonDefinition) throws JSONException {
        Integer timeDefinition = getInt(jsonDefinition, KEY_TIME);
        mTime = ((timeDefinition - timeDefinition % 100) / 100) + ":" + timeDefinition % 100;
        mTempC = getInt(jsonDefinition, KEY_TEMP_C);
        mTempF = getInt(jsonDefinition, KEY_TEMP_F);
        mWindSpeedKm = getInt(jsonDefinition, KEY_WIND_SPEED_KM);
        mWindSpeedMi = getInt(jsonDefinition, KEY_WIND_SPEED_MI);
        mWindDirection = WindDirection.forAbbreviation(getString(jsonDefinition, KEY_WIND_DIR));
        if (jsonDefinition.has(KEY_WEATHER_DESC)) {
            JSONObject weatherDescObject = jsonDefinition.getJSONArray(KEY_WEATHER_DESC).getJSONObject(0);
            mWeatherDesc = getString(weatherDescObject, KEY_VALUE);
        }
        if (jsonDefinition.has(KEY_WEATHER_ICON_URL)) {
            JSONObject iconUrlObject = jsonDefinition.getJSONArray(KEY_WEATHER_ICON_URL).getJSONObject(0);
            mWeatherIconUrl = getString(iconUrlObject, KEY_VALUE);
        }
        mPrecipMM = getDouble(jsonDefinition, KEY_PRECIP_MM);
        mPrecipIn = getDouble(jsonDefinition, KEY_PRECIP_IN);
        mHumidity = getInt(jsonDefinition, KEY_HUMIDITY);
        mPressure = getInt(jsonDefinition, KEY_PRESSURE);
        mCloudCover = getInt(jsonDefinition, KEY_CLOUD_COVER);
        mChanceRain = getInt(jsonDefinition, KEY_RAIN_CHANCE);
        mChanceWindy = getInt(jsonDefinition, KEY_WINDY_CHANCE);
        mChanceOvercast = getInt(jsonDefinition, KEY_OVERCAST_CHANCE);
        mChanceSunny = getInt(jsonDefinition, KEY_SUNNY_CHANCE);
        mChanceFrost = getInt(jsonDefinition, KEY_FROST_CHANCE);
        mChanceFog = getInt(jsonDefinition, KEY_FOG_CHANCE);
        mChanceSnow = getInt(jsonDefinition, KEY_SNOW_CHANCE);
        mChanceThunder = getInt(jsonDefinition, KEY_THUNDER_CHANCE);
    }

    public String getTime() {
        return mTime;
    }

    public Integer getTempC() {
        return mTempC;
    }

    public Integer getTempF() {
        return mTempF;
    }

    public Integer getWindSpeedKm() {
        return mWindSpeedKm;
    }

    public Integer getWindSpeedMiles() {
        return mWindSpeedMi;
    }

    public WindDirection getWindDirection() {
        return mWindDirection;
    }

    public String getWeatherDescription() {
        return mWeatherDesc;
    }

    public String getWeatherIconUrl() {
        return mWeatherIconUrl;
    }

    public Double getPrecipMM() {
        return mPrecipMM;
    }

    public Double getPrecipIn() {
        return mPrecipIn;
    }

    public Integer getHumidity() {
        return mHumidity;
    }

    public Integer getPressure() {
        return mPressure;
    }

    public Integer getCloudCover() {
        return mCloudCover;
    }

    public Integer getChanceOfRain() {
        return mChanceRain;
    }

    public Integer getChanceOfWindy() {
        return mChanceWindy;
    }

    public Integer getChanceOfOvercast() {
        return mChanceOvercast;
    }

    public Integer getChanceOfSunny() {
        return mChanceSunny;
    }

    public Integer getChanceOfFrost() {
        return mChanceFrost;
    }

    public Integer getChanceOfFog() {
        return mChanceFog;
    }

    public Integer getChanceOfSnow() {
        return mChanceSnow;
    }

    public Integer getChanceOfThunder() {
        return mChanceThunder;
    }
}
