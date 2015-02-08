package com.doodeec.weather.android.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doodeec.weather.android.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * View for showing weather info
 * i.e. humidity icon with the value underneath
 */
public class WeatherInfoView extends RelativeLayout {

    @InjectView(R.id.info_icon)
    ImageView mIcon;
    @InjectView(R.id.info_text)
    TextView mText;

    public WeatherInfoView(Context context) {
        this(context, null);
    }

    public WeatherInfoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeatherInfoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.view_weather_info, this, true);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WeatherInfoView, defStyleAttr, 0);

        // get xml attributes
        Drawable icon = a.getDrawable(R.styleable.WeatherInfoView_wiv_icon);
        String defaultText = a.getString(R.styleable.WeatherInfoView_wiv_text);

        ButterKnife.inject(this);

        if (icon != null) {
            mIcon.setImageDrawable(icon);
        }
        mText.setText(defaultText);
    }

    public void setText(String text) {
        mText.setText(text);
    }

    public void setText(@StringRes int textResource) {
        mText.setText(textResource);
    }
}
