package com.doodeec.weather.android.fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doodeec.weather.android.R;
import com.doodeec.weather.android.WedrConfig;
import com.doodeec.weather.android.client.data.WeatherData;
import com.doodeec.weather.android.client.data.model.NearestLocation;
import com.doodeec.weather.android.util.OvalImageView;
import com.doodeec.weather.android.util.WedrPreferences;
import com.doodeec.weather.android.view.WeatherInfoView;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TodayFragment extends Fragment {

    public static final String TODAY_FRG_TAG = "todayFragment";
    private static final String LOC_FORMAT = "%s, %s";

    private OnTodayInteractionListener mListener;
    @InjectView(R.id.refresh_weather)
    SwipeRefreshLayout mRefreshLayout;
    @InjectView(R.id.today_weather_icon)
    OvalImageView mWeatherIcon;
    @InjectView(R.id.weather_temperature)
    TextView mWeatherTemp;
    @InjectView(R.id.weather_description)
    TextView mWeatherDesc;
    @InjectView(R.id.location_region_country)
    TextView mLocRegionCountry;
    @InjectView(R.id.empty_data)
    RelativeLayout mEmptyDataMessage;
    @InjectView(R.id.info_humidity)
    WeatherInfoView mHumidityInfo;
    @InjectView(R.id.info_precipitation)
    WeatherInfoView mPrecipitationInfo;
    @InjectView(R.id.info_pressure)
    WeatherInfoView mPressureInfo;
    @InjectView(R.id.info_wind_speed)
    WeatherInfoView mWindSpeedInfo;
    @InjectView(R.id.info_wind_direction)
    WeatherInfoView mWindDirectionInfo;
    private final SwipeRefreshLayout.OnRefreshListener mRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mListener.onRefreshInvoked();
        }
    };

    public static TodayFragment newInstance() {
        TodayFragment fragment = new TodayFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public TodayFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_today, container, false);

        ButterKnife.inject(this, rootView);

        if (mWeatherTemp == null || mWeatherDesc == null || mLocRegionCountry == null ||
                mWeatherIcon == null || mEmptyDataMessage == null || mHumidityInfo == null ||
                mPrecipitationInfo == null || mPressureInfo == null || mWindSpeedInfo == null ||
                mWindDirectionInfo == null || mRefreshLayout == null) {
            throw new AssertionError("Today fragment has invalid layout");
        }

        mRefreshLayout.setOnRefreshListener(mRefreshListener);

        return rootView;
    }

    @Override
    public void onDestroy() {
        mRefreshLayout.setOnRefreshListener(null);
        super.onDestroy();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnTodayInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnForecastInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * Sets refresh layout indicator
     *
     * @param isRefreshing true to show indicator
     */
    public void setRefreshing(boolean isRefreshing) {
        if (isAdded()) {
            mRefreshLayout.setOnRefreshListener(isRefreshing ? null : mRefreshListener);
            mRefreshLayout.setRefreshing(isRefreshing);
        }
    }

    /**
     * Gets current refresh status
     *
     * @return true if refresh is currently ongoing
     */
    public boolean isRefreshing() {
        return mRefreshLayout.isRefreshing();
    }

    /**
     * Updates data in fragment with the new object
     *
     * @param weatherData data to set
     */
    public void updateData(WeatherData weatherData) {
        if (WedrPreferences.getTemperatureUnit().equals(WedrPreferences.TemperatureUnit.Fahrenheit)) {
            mWeatherTemp.setText(String.format(WedrConfig.TEMP_FORMAT_F, weatherData.getCondition().getTempF()));
        } else {
            // celsius is default
            mWeatherTemp.setText(String.format(WedrConfig.TEMP_FORMAT_C, weatherData.getCondition().getTempC()));
        }

        mWeatherDesc.setText(weatherData.getCondition().getWeatherDescription());

        // set location
        NearestLocation nearestLocation = weatherData.getNearestLocation();
        if (nearestLocation != null) {
            if (nearestLocation.getRegion() != null) {
                mLocRegionCountry.setText(String.format(LOC_FORMAT,
                        weatherData.getNearestLocation().getRegion(),
                        weatherData.getNearestLocation().getCountry()));
            } else {
                // some places does not provide region
                mLocRegionCountry.setText(weatherData.getNearestLocation().getCountry());
            }
        }

        // set info
        mHumidityInfo.setText(String.format(WedrConfig.HUMIDITY_FORMAT, weatherData.getCondition().getHumidity()));
        mPressureInfo.setText(String.format(WedrConfig.PRESSURE_FORMAT, weatherData.getCondition().getPressure()));
        mWindDirectionInfo.setText(weatherData.getCondition().getWindDirection().getAbbreviation());

        if (WedrPreferences.getLengthUnit().equals(WedrPreferences.LengthUnit.Mile)) {
            mWindSpeedInfo.setText(String.format(WedrConfig.WIND_SPEED_FORMAT_MI,
                    weatherData.getCondition().getWindSpeedMi()));
        } else {
            mWindSpeedInfo.setText(String.format(WedrConfig.WIND_SPEED_FORMAT_KM,
                    weatherData.getCondition().getWindSpeedKm()));
        }

        // precipInch can return null sometimes, in that case use MM as a fallback
        if (WedrPreferences.getLengthUnit().equals(WedrPreferences.LengthUnit.Mile) &&
                weatherData.getCondition().getPrecipInch() != null) {
            mPrecipitationInfo.setText(String.format(WedrConfig.PRECIPITATION_FORMAT_IN,
                    weatherData.getCondition().getPrecipInch()));
        } else {
            mPrecipitationInfo.setText(String.format(WedrConfig.PRECIPITATION_FORMAT_MM,
                    (float) weatherData.getCondition().getPrecipMM()));
        }
    }

    /**
     * Sets synchronously loaded weather icon into the layout
     *
     * @param weatherIcon icon bitmap
     */
    public void updateIcon(Bitmap weatherIcon) {
        mWeatherIcon.setImageBitmap(weatherIcon);
    }

    /**
     * Shows/hides empty data message
     *
     * @param show true to show message
     */
    public void showEmptyDataMessage(boolean show) {
        mEmptyDataMessage.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public interface OnTodayInteractionListener {
        /**
         * Fires when refresh layout is pulled down
         */
        void onRefreshInvoked();
    }
}
