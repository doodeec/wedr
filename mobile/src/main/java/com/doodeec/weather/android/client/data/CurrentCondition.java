package com.doodeec.weather.android.client.data;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * @author Dusan Bartos
 */
public class CurrentCondition extends JSONParser {

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
    private static final String KEY_PRESSURE_INCHES = "pressureInches";
    private static final String KEY_TEMP_C = "temp_C";
    private static final String KEY_TEMP_F = "temp_F";
    private static final String KEY_VISIBILITY_KM = "visibility";
    private static final String KEY_VISIBILITY_MI = "visibilityMiles";
    private static final String KEY_WEATHER_CODE = "weatherCode";
    private static final String KEY_WEATHER_DESC = "weatherDesc";
    private static final String KEY_WEATHER_ICON = "weatherIconUrl";
    private static final String KEY_WIND_DIRECTION = "winddir16Point";
    private static final String KEY_WIND_DIRECTION_DEG = "winddirDegree";
    private static final String KEY_WIND_SPEED_KM = "windspeedKmph";
    private static final String KEY_WIND_SPEED_MI = "windspeedMiles";

    private Integer mCloudCover;
    private Integer mFeelTempC;
    private Integer mFeelTempF;
    private Double mHumidity;
    private Integer mPrecipMM;
    private Double mPrecipInch;
    private Integer mPressure;
    private Double mPressureInch;
    private Integer mTempC;
    private Integer mTempF;
    private Integer mVisibilityKm;
    private Integer mVisibilityMi;
    private String mWeatherDescription;
//    private URL mWeatherIconUrl;
    private String mWeatherIconUrl;
    private Integer mWeatherCode;
    private WindDirection mWindDir;
    private Integer mWindDirDegree;
    private Integer mWindSpeedKm;
    private Integer mWindSpeedMi;
    private Date mObservationDate;

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
        mPressureInch = getDouble(jsonDefinition, KEY_PRESSURE_INCHES);
        mTempC = getInt(jsonDefinition, KEY_TEMP_C);
        mTempF = getInt(jsonDefinition, KEY_TEMP_F);
        mVisibilityKm = getInt(jsonDefinition, KEY_VISIBILITY_KM);
        mVisibilityMi = getInt(jsonDefinition, KEY_VISIBILITY_MI);
        if (jsonDefinition.has(KEY_WEATHER_DESC)) {
            JSONObject descriptionObject = jsonDefinition.getJSONArray(KEY_WEATHER_DESC).getJSONObject(0);
            mWeatherDescription = getString(descriptionObject, KEY_VALUE);
        }
        mWeatherCode = getInt(jsonDefinition, KEY_WEATHER_CODE);
        mWindDir = WindDirection.forAbbreviation(getString(jsonDefinition, KEY_WIND_DIRECTION));
        mWindDirDegree = getInt(jsonDefinition, KEY_WIND_DIRECTION_DEG);
        mWindSpeedKm = getInt(jsonDefinition, KEY_WIND_SPEED_KM);
        mWindSpeedMi = getInt(jsonDefinition, KEY_WIND_SPEED_MI);
        //TODO get date
//        mObservationDate = getInt(jsonDefinition, KEY_WIND_SPEED_MI);

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

    public Double getPressureInch() {
        return mPressureInch;
    }

    public Integer getTempC() {
        return mTempC;
    }

    public Integer getTempF() {
        return mTempF;
    }

    public Integer getVisibilityKm() {
        return mVisibilityKm;
    }

    public Integer getVisibilityMi() {
        return mVisibilityMi;
    }

    public String getWeatherDescription() {
        return mWeatherDescription;
    }

    public Integer getWeatherCode() {
        return mWeatherCode;
    }

    public WindDirection getWindDirection() {
        return mWindDir;
    }

    public Integer getWindDirectionDegree() {
        return mWindDirDegree;
    }

    public Integer getWindSpeedKm() {
        return mWindSpeedKm;
    }

    public Integer getWindSpeedMi() {
        return mWindSpeedMi;
    }

    /*public URL getIconURL() {
        return mWeatherIconUrl;
    }*/

    public String getIconURL() {
        return mWeatherIconUrl;
    }
}
