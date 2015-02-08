package com.doodeec.weather.android.view;

import android.graphics.Bitmap;
import android.graphics.Outline;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.TextView;

import com.doodeec.weather.android.R;
import com.doodeec.weather.android.util.OvalImageView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * View holder for forecast list
 * Item in the list displays data about daily forecast:
 * - average temperature
 * - weather icon
 * - day in the week
 * - weather description
 *
 * @author Dusan Bartos
 * @see com.doodeec.weather.android.activity.ForecastActivity
 * @see com.doodeec.weather.android.fragment.ForecastFragment
 */
public class ForecastViewHolder extends RecyclerView.ViewHolder {

    @InjectView(R.id.forecast_temperature)
    TextView mTemperature;
    @InjectView(R.id.forecast_description)
    TextView mDescription;
    @InjectView(R.id.weather_icon)
    ImageView mWeatherIcon;
    @InjectView(R.id.forecast_day)
    TextView mDay;

    public ForecastViewHolder(View v) {
        super(v);

        ButterKnife.inject(this, v);

        if (mTemperature == null || mDescription == null || mWeatherIcon == null || mDay == null) {
            throw new AssertionError("Forecast view holder has invalid layout");
        }

        // clip to oval icon
        // for Lollipop use outline provider, for pre-Lollipop devices fallback to OvalImageView
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ViewOutlineProvider viewOutlineProvider = new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    // Or read size directly from the view's width/height
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        int size = mWeatherIcon.getResources().getDimensionPixelSize(R.dimen.forecast_icon_size);
                        outline.setOval(0, 0, size, size);
                    }
                }
            };

            mWeatherIcon.setOutlineProvider(viewOutlineProvider);
            mWeatherIcon.setClipToOutline(true);
        } else if (mWeatherIcon instanceof OvalImageView) {
            ((OvalImageView) mWeatherIcon).setRadius(
                    mWeatherIcon.getResources().getDimensionPixelSize(R.dimen.forecast_icon_size) / 2);
        }
    }

    public void setWeatherDescription(String description) {
        mDescription.setText(description);
    }

    public void setTemperature(String temperatureString) {
        mTemperature.setText(temperatureString);
    }

    public void setDay(String day) {
        mDay.setText(day);
    }

    public void setWeatherIcon(Bitmap icon) {
        mWeatherIcon.setImageBitmap(icon);
    }
}
