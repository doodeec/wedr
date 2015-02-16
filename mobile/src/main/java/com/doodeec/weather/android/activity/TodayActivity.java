package com.doodeec.weather.android.activity;

import android.content.IntentSender;
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
import com.doodeec.weather.android.util.WedrLog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class TodayActivity extends BaseDrawerActivity implements TodayFragment.OnTodayInteractionListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private static final String BUNDLE_LOADING = "loading";
    private static final int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    private CancellableServerRequest mLoadWeatherRequest;
    private CancellableServerRequest mLoadIconRequest;
    private TodayFragment mTodayFragment;
    private boolean mWasRefreshing = false;

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

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

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setNumUpdates(1)
                .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();

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
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (mTodayFragment.isAdded()) {
            outState.putBoolean(BUNDLE_LOADING, mTodayFragment.isRefreshing());
        }
        super.onSaveInstanceState(outState);
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

        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }

        super.onPause();
    }

    @Override
    public void onRefreshInvoked() {
        if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(this) != ConnectionResult.SUCCESS) {
            Toast.makeText(this, R.string.common_google_play_services_unsupported_text, Toast.LENGTH_SHORT).show();
            mTodayFragment.setRefreshing(false);
        } else if (mGoogleApiClient.isConnected()) {
            if (!SessionData.getInstance().getGeoLocation().getOngoingRequest()) {
                Toast.makeText(this, R.string.reading_location_message, Toast.LENGTH_SHORT).show();
                mTodayFragment.setRefreshing(true);
                updateLocation();
            }
        } else {
            Toast.makeText(this, R.string.google_play_not_connected, Toast.LENGTH_SHORT).show();
            mTodayFragment.setRefreshing(false);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        WedrLog.w("Google API connection suspended");
    }

    @Override
    public void onConnected(Bundle bundle) {
        WedrLog.d("Connected Google API");
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location != null) {
            onLocation(location.getLatitude(), location.getLongitude());
        }

        long now = System.currentTimeMillis();
        if (now - SessionData.getInstance().getLastUpdateTimestamp() > WedrConfig.WEATHER_THRESHOLD
                || mWasRefreshing) {
            onRefreshInvoked();
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        WedrLog.e("Connection failed");
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            WedrLog.e("Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        WedrLog.d("Location changed - LAT:" + location.getLatitude() + " LON:" + location.getLongitude());
        SessionData.getInstance().getGeoLocation().setLocation(location);
        SessionData.getInstance().getGeoLocation().setOngoingRequest(false);
        onLocation(location.getLatitude(), location.getLongitude());
    }

    private void updateLocation() {
        WedrLog.d("Request location update");
        SessionData.getInstance().getGeoLocation().setOngoingRequest(true);
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
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
