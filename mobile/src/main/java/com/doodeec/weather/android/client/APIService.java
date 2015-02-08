package com.doodeec.weather.android.client;

import android.graphics.Bitmap;

import com.doodeec.scom.BaseServerRequest;
import com.doodeec.scom.CancellableServerRequest;
import com.doodeec.scom.ImageServerRequest;
import com.doodeec.scom.RequestError;
import com.doodeec.scom.ServerRequest;
import com.doodeec.scom.listener.BaseRequestListener;
import com.doodeec.scom.listener.JSONRequestListener;
import com.doodeec.weather.android.WedrConfig;
import com.doodeec.weather.android.cache.ImageCache;
import com.doodeec.weather.android.client.data.WeatherData;
import com.doodeec.weather.android.client.parser.WeatherDataParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;

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
    private static final String SERVER_URL_SKELETON = "http://api.worldweatheronline.com/free/v2/weather.ashx?key=%s&format=json&includeLocation=yes&tp=24&num_of_days=%d&q=%.7f,%.7f";

    public static CancellableServerRequest loadWeatherForLocation(double latitude, double longitude, final BaseRequestListener<WeatherData> listener) {
        String url = String.format(SERVER_URL_SKELETON,
                WedrConfig.API_KEY,
                WedrConfig.FORECAST_DAYS,
                latitude, longitude);

        ServerRequest request = new ServerRequest(BaseServerRequest.RequestType.GET, new JSONRequestListener() {
            @Override
            public void onSuccess(JSONObject object) {
                try {
                    listener.onSuccess(WeatherDataParser.parseWeatherData(object));
                } catch (JSONException | ParseException e) {
                    onError(new RequestError(e));
                }
            }

            @Override
            public void onError(RequestError requestError) {
                listener.onError(requestError);
            }

            @Override
            public void onCancelled() {
                listener.onCancelled();
            }

            @Override
            public void onProgress(Integer progress) {
                listener.onProgress(progress);
            }
        });
        request.executeInParallel(url);

        return request;
    }

    public static CancellableServerRequest loadWeatherIcon(final String url, final BaseRequestListener<Bitmap> listener) {
        Bitmap cachedImage = ImageCache.getBitmapFromCache(url);
        if (cachedImage != null) {
            listener.onSuccess(cachedImage);
            return null;
        }

        ImageServerRequest request = new ImageServerRequest(BaseServerRequest.RequestType.GET, new BaseRequestListener<Bitmap>() {
            @Override
            public void onError(RequestError error) {
                listener.onError(error);
            }

            @Override
            public void onSuccess(Bitmap response) {
                ImageCache.addBitmapToCache(url, response);
                listener.onSuccess(response);
            }

            @Override
            public void onCancelled() {
                listener.onCancelled();
            }

            @Override
            public void onProgress(Integer progress) {
                listener.onProgress(progress);
            }
        });
        request.executeInParallel(url);

        return request;
    }
}
