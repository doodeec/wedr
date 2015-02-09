package com.doodeec.weather.android.activity;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.location.Location;
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

import java.util.Observable;
import java.util.Observer;

public class TodayActivity extends BaseDrawerActivity implements TodayFragment.OnTodayInteractionListener, Observer {

    private static final String BUNDLE_LOADING = "loading";

    private CancellableServerRequest mLoadWeatherRequest;
    private CancellableServerRequest mLoadIconRequest;
    private TodayFragment mTodayFragment;
    private boolean mWasRefreshing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, TodayFragment.newInstance(), TodayFragment.TODAY_FRG_TAG)
                    .commit();
        } else {
            mWasRefreshing = savedInstanceState.getBoolean(BUNDLE_LOADING);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTodayFragment = (TodayFragment) getSupportFragmentManager()
                .findFragmentByTag(TodayFragment.TODAY_FRG_TAG);

        // load stored data
        if (SessionData.getInstance().getWeatherData().getCondition() != null &&
                SessionData.getInstance().getWeatherData().getNearestLocation() != null) {
            if (mTodayFragment.isAdded()) {
                mTodayFragment.updateData(SessionData.getInstance().getWeatherData());
                mTodayFragment.showEmptyDataMessage(false);
                loadWeatherIcon(SessionData.getInstance().getWeatherData());
            }
        }

        long now = System.currentTimeMillis();
        if (now - SessionData.getInstance().getLastUpdateTimestamp() > WedrConfig.WEATHER_THRESHOLD
                || mWasRefreshing) {
            onRefreshInvoked();
        }

        SessionData.getInstance().getGeoLocation().addObserver(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (mTodayFragment.isAdded()) {
            outState.putBoolean(BUNDLE_LOADING, mTodayFragment.isRefreshing());
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void update(Observable observable, Object data) {
        Location location = ((SessionData.GeoLocation) observable).getLocation();
        onLocation(location.getLatitude(), location.getLongitude());
    }

    /**
     * Fired when location provider updates location
     *
     * @param latitude  current location latitude
     * @param longitude current location longitude
     */
    public void onLocation(double latitude, double longitude) {
        mTodayFragment.setRefreshing(true);
        mLoadWeatherRequest = APIService.loadWeatherForLocation(latitude, longitude,
                new BaseRequestListener<WeatherData>() {
                    @Override
                    public void onError(RequestError requestError) {
                        WedrLog.e("Error loading weather: " + requestError.getMessage());
                        Toast.makeText(TodayActivity.this, R.string.weather_data_error, Toast.LENGTH_SHORT).show();
                        mTodayFragment.setRefreshing(false);
                        mLoadWeatherRequest = null;
                    }

                    @Override
                    public void onSuccess(WeatherData weatherData) {
                        SessionData.getInstance().setWeatherData(weatherData);
                        if (mTodayFragment.isAdded()) {
                            mTodayFragment.updateData(weatherData);
                            mTodayFragment.setRefreshing(false);
                            mTodayFragment.showEmptyDataMessage(false);
                        }

                        mLoadWeatherRequest = null;
                        loadWeatherIcon(weatherData);
                    }

                    @Override
                    public void onCancelled() {
                        WedrLog.w("Weather loading cancelled");
                        mTodayFragment.setRefreshing(false);
                        mLoadWeatherRequest = null;
                    }

                    @Override
                    public void onProgress(Integer integer) {
                    }
                });
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

        SessionData.getInstance().getGeoLocation().deleteObserver(this);
        super.onPause();
    }

    @Override
    public void onRefreshInvoked() {
        if (!SessionData.getInstance().getGeoLocation().getOngoingRequest()) {
            mTodayFragment.setRefreshing(true);

            if (!LocationService.requestLocation()) {
                new AlertDialog.Builder(this)
                        .setTitle(R.string.location_unavailable_title)
                        .setMessage(R.string.location_unavailable_message)
                        .setPositiveButton(android.R.string.ok, null)
                        .show();

                mTodayFragment.setRefreshing(false);
            }
        }
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
