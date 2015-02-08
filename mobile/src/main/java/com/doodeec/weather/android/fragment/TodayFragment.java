package com.doodeec.weather.android.fragment;

import android.app.Activity;
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
import android.widget.TextView;

import com.doodeec.weather.android.R;
import com.doodeec.weather.android.client.data.model.NearestLocation;
import com.doodeec.weather.android.client.data.WeatherData;
import com.doodeec.weather.android.util.OvalImageView;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TodayFragment extends Fragment {

    public static final String TODAY_FRG_TAG = "todayFragment";

    private static final String TEMP_FORMAT_C = "%d°C";
    private static final String TEMP_FORMAT_F = "%dF";
    private static final String LOC_FORMAT = "%s, %s";

    private OnTodayInteractionListener mListener;
    @InjectView(R.id.weather_icon)
    ImageView mWeatherIcon;
    @InjectView(R.id.weather_temperature)
    TextView mWeatherTemp;
    @InjectView(R.id.weather_description)
    TextView mWeatherDesc;
    @InjectView(R.id.location_region_country)
    TextView mLocRegionCountry;

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
                mWeatherIcon == null) {
            throw new AssertionError("Today fragment has invalid layout");
        }

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnTodayInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void updateData(WeatherData weatherData) {
        //TODO pick C or F
        mWeatherTemp.setText(String.format(TEMP_FORMAT_C, weatherData.getCondition().getTempC()));

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

    public interface OnTodayInteractionListener {

    }
}
