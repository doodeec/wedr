package com.doodeec.weather.android.client.data;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Dusan Bartos
 */
public class NearestLocation extends JSONParser {

    public static final String KEY_NEAREST = "nearest_area";

    private static final String KEY_VALUE = "value";

    private static final String KEY_COUNTRY = "country";
    private static final String KEY_REGION = "region";
    private static final String KEY_GEO_LAT = "latitude";
    private static final String KEY_GEO_LON = "longitude";

    private String mCountry;
    private String mRegion;
    private Double mLat;
    private Double mLon;

    public NearestLocation(JSONObject jsonDefinition) throws JSONException {
        mLat = getDouble(jsonDefinition, KEY_GEO_LAT);
        mLon = getDouble(jsonDefinition, KEY_GEO_LON);

        if (jsonDefinition.has(KEY_COUNTRY)) {
            JSONObject countryObject = jsonDefinition.getJSONArray(KEY_COUNTRY).getJSONObject(0);
            mCountry = getString(countryObject, KEY_VALUE);
        }

        if (jsonDefinition.has(KEY_REGION)) {
            JSONObject regionObject = jsonDefinition.getJSONArray(KEY_REGION).getJSONObject(0);
            mRegion = getString(regionObject, KEY_VALUE);
        }
    }
    
    public String getCountry() {
        return mCountry;
    }

    public String getRegion() {
        return mRegion;
    }

    public Double getLatitude() {
        return mLat;
    }

    public Double getLongitude() {
        return mLon;
    }
}
