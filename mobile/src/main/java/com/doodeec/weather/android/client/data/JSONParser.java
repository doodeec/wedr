package com.doodeec.weather.android.client.data;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Dusan Bartos
 */
public abstract class JSONParser {

    /**
     * Gets integer value if it exists in json object
     *
     * @param jsonObject json object
     * @param key        key to find
     * @return value if exists, null otherwise
     */
    protected Integer getInt(JSONObject jsonObject, String key) {
        return getInt(jsonObject, key, null);
    }

    /**
     * Gets integer value if it exists in json object
     *
     * @param jsonObject json object
     * @param key        key to find
     * @param fallback   fallback value
     * @return value if exists, fallback value otherwise
     */
    protected Integer getInt(JSONObject jsonObject, String key, Integer fallback) {
        try {
            if (jsonObject.has(key)) {
                return jsonObject.getInt(key);
            } else {
                return fallback;
            }
        } catch (JSONException e) {
            return fallback;
        }
    }

    /**
     * Gets string value if it exists in json object
     *
     * @param jsonObject json object
     * @param key        key to find
     * @return value if exists, null otherwise
     */
    protected String getString(JSONObject jsonObject, String key) {
        return getString(jsonObject, key, null);
    }

    /**
     * Gets string value if it exists in json object
     *
     * @param jsonObject json object
     * @param key        key to find
     * @param fallback   fallback value
     * @return value if exists, fallback value otherwise
     */
    protected String getString(JSONObject jsonObject, String key, String fallback) {
        try {
            if (jsonObject.has(key)) {
                return jsonObject.getString(key);
            } else {
                return fallback;
            }
        } catch (JSONException e) {
            return fallback;
        }
    }

    /**
     * Gets double value if it exists in json object
     *
     * @param jsonObject json object
     * @param key        key to find
     * @return value if exists, null otherwise
     */
    protected Double getDouble(JSONObject jsonObject, String key) {
        return getDouble(jsonObject, key, null);
    }

    /**
     * Gets double value if it exists in json object
     *
     * @param jsonObject json object
     * @param key        key to find
     * @param fallback   fallback value
     * @return value if exists, fallback value otherwise
     */
    protected Double getDouble(JSONObject jsonObject, String key, Double fallback) {
        try {
            if (jsonObject.has(key)) {
                return jsonObject.getDouble(key);
            } else {
                return fallback;
            }
        } catch (JSONException e) {
            return fallback;
        }
    }
}
