package com.doodeec.weather.android.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.doodeec.weather.android.R;
import com.doodeec.weather.android.client.data.WeatherData;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TodayFragment extends Fragment {

    public static final String TODAY_FRG_TAG = "todayFragment";

    private static final String TEMP_FORMAT_C = "%d °C";
    private static final String TEMP_FORMAT_F = "%d F";

    private OnTodayInteractionListener mListener;
    @InjectView(R.id.weather_temperature)
    TextView mWeatherTemp;
    @InjectView(R.id.weather_description)
    TextView mWeatherDesc;
    @InjectView(R.id.location_region)
    TextView mLocRegion;
    @InjectView(R.id.location_country)
    TextView mLocCountry;

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

        if (mWeatherTemp == null || mWeatherDesc == null || mLocRegion == null || mLocCountry == null) {
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
        mWeatherTemp.setText(String.format("%d °C", weatherData.getCondition().getTempC()));

        mWeatherDesc.setText(weatherData.getCondition().getWeatherDescription());
        if (weatherData.getNearestLocation() != null) {
            mLocRegion.setText(weatherData.getNearestLocation().getRegion());
            mLocCountry.setText(weatherData.getNearestLocation().getCountry());
        }
    }

    public interface OnTodayInteractionListener {

    }
}
