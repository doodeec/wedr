package com.doodeec.weather.android.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doodeec.weather.android.R;
import com.doodeec.weather.android.adapter.ForecastAdapter;
import com.doodeec.weather.android.client.data.SessionData;
import com.doodeec.weather.android.client.data.model.DailyForecast;
import com.doodeec.weather.android.util.RecyclerViewItemClickListener;

import java.util.Observable;
import java.util.Observer;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ForecastFragment extends Fragment implements Observer, RecyclerViewItemClickListener.OnItemClickListener {

    public static final String FORECAST_FRG_TAG = "forecastFragment";

    private OnForecastInteractionListener mListener;
    private ForecastAdapter mForecastAdapter;
    @InjectView(R.id.forecast_list)
    RecyclerView mForecastList;

    public static ForecastFragment newInstance() {
        ForecastFragment fragment = new ForecastFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public ForecastFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_forecast, container, false);

        ButterKnife.inject(this, rootView);

        if (mForecastList == null) {
            throw new AssertionError("Forecast fragment has invalid layout");
        }

        // initialize recycler list
        mForecastAdapter = new ForecastAdapter(getActivity());
        mForecastList.setHasFixedSize(true);
        mForecastList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mForecastList.setAdapter(mForecastAdapter);
        mForecastList.addOnItemTouchListener(new RecyclerViewItemClickListener(getActivity(), this));

        // initialize forecast data for the first time
        mForecastAdapter.updateForecastData(SessionData.getInstance().getWeatherData().getForecast());

        return rootView;
    }

    @Override
    public void update(Observable observable, Object data) {
        mForecastAdapter.updateForecastData(SessionData.getInstance().getWeatherData().getForecast());
    }

    @Override
    public void onItemClick(View view, int position) {
        mListener.onDailyForecastClicked(mForecastAdapter.getItem(position));
    }

    @Override
    public void onItemLongClick(View view, int position) {
        // do nothing
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnForecastInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnForecastInteractionListener");
        }

        SessionData.getInstance().getWeatherData().addObserver(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        SessionData.getInstance().getWeatherData().deleteObserver(this);
        mListener = null;
    }

    public interface OnForecastInteractionListener {
        /**
         * Fired when item in forecast list is clicked
         *
         * @param dailyForecast daily forecast which was clicked
         */
        void onDailyForecastClicked(DailyForecast dailyForecast);
    }
}
