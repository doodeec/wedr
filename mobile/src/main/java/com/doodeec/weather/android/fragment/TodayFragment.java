package com.doodeec.weather.android.fragment;

import android.graphics.Bitmap;
import android.graphics.Outline;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doodeec.weather.android.R;
import com.doodeec.weather.android.WedrConfig;
import com.doodeec.weather.android.client.data.WeatherData;
import com.doodeec.weather.android.client.data.model.NearestLocation;
import com.doodeec.weather.android.util.OvalImageView;
import com.doodeec.weather.android.util.WedrPreferences;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TodayFragment extends Fragment {

    public static final String TODAY_FRG_TAG = "todayFragment";
    private static final String LOC_FORMAT = "%s, %s";

    @InjectView(R.id.today_weather_icon)
    ImageView mWeatherIcon;
    @InjectView(R.id.weather_temperature)
    TextView mWeatherTemp;
    @InjectView(R.id.weather_description)
    TextView mWeatherDesc;
    @InjectView(R.id.location_region_country)
    TextView mLocRegionCountry;
    @InjectView(R.id.weather_progressbar)
    RelativeLayout mProgressBar;

    public static TodayFragment newInstance() {
        TodayFragment fragment = new TodayFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public TodayFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_today, container, false);

        ButterKnife.inject(this, rootView);

        if (mWeatherTemp == null || mWeatherDesc == null || mLocRegionCountry == null ||
                mWeatherIcon == null || mProgressBar == null) {
            throw new AssertionError("Today fragment has invalid layout");
        }

        return rootView;
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
    }

    /**
     * Sets synchronously loaded weather icon into the layout
     *
     * @param weatherIcon icon bitmap
     */
    public void updateIcon(Bitmap weatherIcon) {
        // clip to oval icon
        // for Lollipop use outline provider, for pre-Lollipop devices fallback to OvalImageView
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ViewOutlineProvider viewOutlineProvider = new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    // Or read size directly from the view's width/height
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        int size = getResources().getDimensionPixelSize(R.dimen.today_icon_size);
                        outline.setOval(0, 0, size, size);
                    }
                }
            };

            mWeatherIcon.setOutlineProvider(viewOutlineProvider);
            mWeatherIcon.setClipToOutline(true);
        } else if (mWeatherIcon instanceof OvalImageView) {
            ((OvalImageView) mWeatherIcon).setRadius(
                    getResources().getDimensionPixelSize(R.dimen.today_icon_size) / 2);
        }

        mWeatherIcon.setImageBitmap(weatherIcon);
    }

    /**
     * Shows/hides progress loader
     *
     * @param show true to show progress, false to hide progress
     */
    public void showProgress(boolean show) {
        mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
