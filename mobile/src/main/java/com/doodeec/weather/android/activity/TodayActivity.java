package com.doodeec.weather.android.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Toast;

import com.doodeec.scom.CancellableServerRequest;
import com.doodeec.scom.RequestError;
import com.doodeec.scom.listener.BaseRequestListener;
import com.doodeec.weather.android.R;
import com.doodeec.weather.android.WedrConfig;
import com.doodeec.weather.android.client.APIService;
import com.doodeec.weather.android.client.data.SessionData;
import com.doodeec.weather.android.client.data.WeatherData;
import com.doodeec.weather.android.fragment.TodayFragment;
import com.doodeec.weather.android.geoloc.LocationService;
import com.doodeec.weather.android.util.WedrLog;

public class TodayActivity extends BaseDrawerActivity implements TodayFragment.OnTodayInteractionListener,
        LocationService.OnLocationRetrievedListener {

    private CancellableServerRequest mLoadWeatherRequest;
    private CancellableServerRequest mLoadIconRequest;
    private TodayFragment mTodayFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, TodayFragment.newInstance(), TodayFragment.TODAY_FRG_TAG)
                    .commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTodayFragment = (TodayFragment) getSupportFragmentManager().findFragmentByTag(TodayFragment.TODAY_FRG_TAG);

        // load stored data
        if (SessionData.getInstance().getWeatherData().getCondition() != null &&
                SessionData.getInstance().getWeatherData().getNearestLocation() != null) {
            if (mTodayFragment.isAdded()) {
                mTodayFragment.updateData(SessionData.getInstance().getWeatherData());
            }
        }

        long now = System.currentTimeMillis();
        if (now - SessionData.getInstance().getLastUpdateTimestamp() > WedrConfig.WEATHER_THRESHOLD) {
            onRefreshInvoked();
        }
    }

    @Override
    public void onLocation(double latitude, double longitude) {
        mTodayFragment.setRefreshing(true);
        mLoadWeatherRequest = APIService.loadWeatherForLocation(latitude, longitude,
                new BaseRequestListener<WeatherData>() {
                    @Override
                    public void onError(RequestError requestError) {
                        WedrLog.e("Error loading weather: " + requestError.getMessage());
                        Toast.makeText(TodayActivity.this, R.string.weather_data_error, Toast.LENGTH_SHORT).show();
                        if (mTodayFragment.isAdded()) {
                            mTodayFragment.setRefreshing(false);
                            mTodayFragment.showProgress(false);
                        }
                        mLoadWeatherRequest = null;
                    }

                    @Override
                    public void onSuccess(WeatherData weatherData) {
                        SessionData.getInstance().setWeatherData(weatherData);
                        if (mTodayFragment.isAdded()) {
                            mTodayFragment.updateData(weatherData);
                            mTodayFragment.setRefreshing(false);
                            mTodayFragment.showProgress(false);
                        }

                        mLoadWeatherRequest = null;
                        loadWeatherIcon(weatherData);
                    }

                    @Override
                    public void onCancelled() {
                        WedrLog.w("Weather loading cancelled");
                        if (mTodayFragment.isAdded()) {
                            mTodayFragment.setRefreshing(false);
                            mTodayFragment.showProgress(false);
                        }
                        mLoadWeatherRequest = null;
                    }

                    @Override
                    public void onProgress(Integer integer) {
                    }
                });
    }

    @Override
    public void onLocationError() {
        Toast.makeText(this, R.string.location_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        // stop loading request if activity is paused
        if (mLoadWeatherRequest != null) {
            WedrLog.w("Cancelling weather loading");
            mLoadWeatherRequest.cancel(true);
            mLoadWeatherRequest = null;
        }
        if (mLoadIconRequest != null) {
            WedrLog.w("Cancelling weather icon loading");
            mLoadIconRequest.cancel(true);
            mLoadIconRequest = null;
        }
        super.onPause();
    }

    @Override
    public void onRefreshInvoked() {
        mTodayFragment.showProgress(true);
        LocationService.requestLocation(this);
    }

    /**
     * Loads weather icon asynchronously from server
     *
     * @param weatherData data
     */
    private void loadWeatherIcon(WeatherData weatherData) {
        if (weatherData != null && weatherData.getCondition() != null &&
                weatherData.getCondition().getIconURL() != null) {

            WedrLog.d("Loading weather icon: " + weatherData.getCondition().getIconURL());
            mLoadIconRequest = APIService.loadWeatherIcon(
                    weatherData.getCondition().getIconURL().toString(),
                    new BaseRequestListener<Bitmap>() {
                        @Override
                        public void onError(RequestError error) {
                            WedrLog.e("Error loading weather icon: " + error.getMessage());
                            mLoadIconRequest = null;
                        }

                        @Override
                        public void onSuccess(Bitmap icon) {
                            if (mTodayFragment.isAdded()) {
                                mTodayFragment.updateIcon(icon);
                            }
                            mLoadIconRequest = null;
                        }

                        @Override
                        public void onCancelled() {
                            mLoadIconRequest = null;
                        }

                        @Override
                        public void onProgress(Integer progress) {
                        }
                    });
        }
    }
}
