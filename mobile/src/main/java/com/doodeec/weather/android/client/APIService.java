package com.doodeec.weather.android.client;

import com.doodeec.scom.BaseServerRequest;
import com.doodeec.scom.CancellableServerRequest;
import com.doodeec.scom.RequestError;
import com.doodeec.scom.ServerRequest;
import com.doodeec.scom.listener.BaseRequestListener;
import com.doodeec.scom.listener.JSONRequestListener;
import com.doodeec.weather.android.WedrConfig;
import com.doodeec.weather.android.client.data.WeatherData;
import com.doodeec.weather.android.util.WedrLog;

import org.json.JSONObject;

/**
 * Service for getting data from API server
 *
 * @author Dusan Bartos
 * @see com.doodeec.scom.ServerRequest
 */
public class APIService {

    /**
     * Base server url skeleton
     * consists of server URL, path + parameters
     * Needed parameters are:
     * 1. String - world weather online API key
     * 2. Integer - number of days to forecast
     * 3. and 4. Float - location Latitude (3.) and Longitude (4.)
     */
    private static final String SERVER_URL_SKELETON = "http://api.worldweatheronline.com/free/v2/weather.ashx?key=%s&format=json&num_of_days=%d&q=%.5f,%.5f";

    public static CancellableServerRequest loadWeatherForLocation(float latitude, float longitude, final BaseRequestListener<WeatherData> listener) {
        String url = String.format(SERVER_URL_SKELETON,
                WedrConfig.API_KEY,
                WedrConfig.FORECAST_DAYS,
                latitude, longitude);

        ServerRequest request = new ServerRequest(BaseServerRequest.RequestType.GET, new JSONRequestListener() {
            @Override
            public void onSuccess(JSONObject object) {
                WedrLog.d("Success getting weather object");
                
                //TODO parse
                
                listener.onSuccess(null);
            }

            @Override
            public void onError(RequestError requestError) {
                WedrLog.d("Error getting weather object");
                listener.onError(requestError);
            }

            @Override
            public void onCancelled() {
                WedrLog.w("Request cancelled");
                listener.onCancelled();
            }

            @Override
            public void onProgress(Integer progress) {
                WedrLog.d("Getting weather: " + progress + "%");
                listener.onProgress(progress);
            }
        });
        request.executeInParallel(url);

        return request;
    }
}
