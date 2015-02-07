package com.doodeec.weather.android.client.data;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * @author Dusan Bartos
 */
public class AstronomicalForecast extends JSONParser {

    public static final String KEY_ASTRONOMY = "astronomy";

    private static final String KEY_MOONRISE = "moonrise";
    private static final String KEY_MOONSET = "moonset";
    private static final String KEY_SUNRISE = "sunrise";
    private static final String KEY_SUNSET = "sunset";

    private Date mMoonRise;
    private Date mMoonSet;
    private Date mSunRise;
    private Date mSunSet;

    public AstronomicalForecast(JSONObject jsonDefinition) throws JSONException {
        //TODO dates
    }

    public Date getMoonRise() {
        return mMoonRise;
    }

    public Date getMoonSet() {
        return mMoonSet;
    }

    public Date getSunRise() {
        return mSunRise;
    }

    public Date getSunSet() {
        return mSunSet;
    }
}
