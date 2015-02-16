package com.doodeec.weather.android.client.parser;

import com.doodeec.weather.android.client.data.model.CurrentCondition;
import com.doodeec.weather.android.client.data.model.DailyForecast;
import com.doodeec.weather.android.client.data.model.NearestLocation;
import com.doodeec.weather.android.client.data.WeatherData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.text.ParseException;

/**
 * @author Dusan Bartos
 */
public class WeatherDataParser {

    public static WeatherData parseWeatherData(JSONObject objectDefinition)
            throws JSONException, ParseException, MalformedURLException {
        WeatherData weatherData = new WeatherData();

        if (objectDefinition.has(WeatherData.KEY_DATA)) {
            JSONObject data = objectDefinition.getJSONObject(WeatherData.KEY_DATA);

            if (data.has(WeatherData.KEY_ERROR)) {
                JSONObject errorObject = data.getJSONArray(WeatherData.KEY_ERROR).getJSONObject(0);
                throw new ParseException("Unable to parse weather data: " +
                        errorObject.getString(WeatherData.KEY_MESSAGE), 0);
            }

            if (data.has(NearestLocation.KEY_NEAREST)) {
                weatherData.setNearestLocation(parseNearestLocationData(
                        data.getJSONArray(NearestLocation.KEY_NEAREST)));
            }

            if (data.has(CurrentCondition.KEY_CONDITION)) {
                weatherData.setCondition(parseCurrentConditionData(
                        data.getJSONArray(CurrentCondition.KEY_CONDITION)));
            } else {
                throw new ParseException("Unable to parse weather data, no 'current_condition'" +
                        " attribute defined", 0);
            }

            if (data.has(DailyForecast.KEY_WEATHER)) {
                DailyForecast[] parsedForecast = parseForecastData(
                        data.getJSONArray(DailyForecast.KEY_WEATHER));

                weatherData.clearForecast();
                for (DailyForecast dailyForecast : parsedForecast) {
                    weatherData.addForecast(dailyForecast);
                }
            }
        } else {
            throw new ParseException("Unable to parse weather data, no 'data' attribute defined", 0);
        }

        return weatherData;
    }

    private static NearestLocation parseNearestLocationData(JSONArray nearestLocArray)
            throws JSONException {
        return new NearestLocation(nearestLocArray.getJSONObject(0));
    }

    private static CurrentCondition parseCurrentConditionData(JSONArray conditionArray)
            throws JSONException, MalformedURLException {
        return new CurrentCondition(conditionArray.getJSONObject(0));
    }

    private static DailyForecast[] parseForecastData(JSONArray weatherArray) throws JSONException, ParseException {
        DailyForecast[] forecasts = new DailyForecast[weatherArray.length()];
        for (int i = 0; i < weatherArray.length(); i++) {
            forecasts[i] = new DailyForecast(weatherArray.getJSONObject(i));
        }
        return forecasts;
    }
}
