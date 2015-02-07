package com.doodeec.weather.android.client.parser;

import com.doodeec.weather.android.client.data.CurrentCondition;
import com.doodeec.weather.android.client.data.DailyForecast;
import com.doodeec.weather.android.client.data.WeatherData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Dusan Bartos
 */
public class WeatherDataParser {
    
    public static WeatherData parseWeatherData(JSONObject objectDefinition) throws JSONException {
        WeatherData weatherData = new WeatherData();

        if (objectDefinition.has(WeatherData.KEY_DATA)) {
            JSONObject data = objectDefinition.getJSONObject(WeatherData.KEY_DATA);
            
            if (data.has(CurrentCondition.KEY_CONDITION)) {
                weatherData.setCondition(parseCurrentConditionData(
                        data.getJSONArray(CurrentCondition.KEY_CONDITION))[0]);
            }

            if (data.has(DailyForecast.KEY_WEATHER)) {
                DailyForecast[] parsedForecast = parseForecast(
                        data.getJSONArray(DailyForecast.KEY_WEATHER));

                weatherData.clearForecast();
                for (DailyForecast dailyForecast : parsedForecast) {
                    weatherData.addForecast(dailyForecast);
                }
            }
        }

        return weatherData;
    }

    private static CurrentCondition[] parseCurrentConditionData(JSONArray conditionArray)
            throws JSONException {
        CurrentCondition[] conditions = new CurrentCondition[conditionArray.length()];
        for (int i = 0; i < conditionArray.length(); i++) {
            conditions[i] = new CurrentCondition(conditionArray.getJSONObject(i));
        }
        return conditions;
    }

    private static DailyForecast[] parseForecast(JSONArray weatherArray) throws JSONException {
        DailyForecast[] forecasts = new DailyForecast[weatherArray.length()];
        for (int i = 0; i < weatherArray.length(); i++) {
            forecasts[i] = new DailyForecast(weatherArray.getJSONObject(i));
        }
        return forecasts;
    }
}
