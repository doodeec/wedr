package com.doodeec.weather.android.activity;

import android.os.Bundle;

import com.doodeec.weather.android.R;
import com.doodeec.weather.android.fragment.TodayFragment;

public class TodayActivity extends BaseDrawerActivity implements TodayFragment.OnTodayInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, TodayFragment.newInstance())
                    .commit();
        }
    }
}
