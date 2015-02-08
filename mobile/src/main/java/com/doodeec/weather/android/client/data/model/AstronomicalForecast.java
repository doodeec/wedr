package com.doodeec.weather.android.client.data.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Dusan Bartos
 */
public class AstronomicalForecast extends JSONParser {

    public static final String KEY_ASTRONOMY = "astronomy";

    private static final String KEY_MOONRISE = "moonrise";
    private static final String KEY_MOONSET = "moonset";
    private static final String KEY_SUNRISE = "sunrise";
    private static final String KEY_SUNSET = "sunset";

    private String mMoonRise;
    private String mMoonSet;
    private String mSunRise;
    private String mSunSet;

    public AstronomicalForecast(JSONObject jsonDefinition) throws JSONException {
        mMoonRise = getString(jsonDefinition, KEY_MOONRISE);
        mMoonSet = getString(jsonDefinition, KEY_MOONSET);
        mSunRise = getString(jsonDefinition, KEY_SUNRISE);
        mSunSet = getString(jsonDefinition, KEY_SUNSET);
    }

    public String getMoonRise() {
        return mMoonRise;
    }

    public String getMoonSet() {
        return mMoonSet;
    }

    public String getSunRise() {
        return mSunRise;
    }

    public String getSunSet() {
        return mSunSet;
    }
}
