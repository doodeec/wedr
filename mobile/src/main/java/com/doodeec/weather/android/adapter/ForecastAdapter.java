package com.doodeec.weather.android.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.doodeec.scom.RequestError;
import com.doodeec.scom.listener.BaseRequestListener;
import com.doodeec.weather.android.R;
import com.doodeec.weather.android.cache.ImageCache;
import com.doodeec.weather.android.client.APIService;
import com.doodeec.weather.android.client.data.model.DailyForecast;
import com.doodeec.weather.android.view.ForecastViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dusan Bartos
 */
public class ForecastAdapter extends RecyclerView.Adapter<ForecastViewHolder> {

    private LayoutInflater mInflater;
    private List<DailyForecast> mForecastData = new ArrayList<>();

    public ForecastAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public void updateForecastData(List<DailyForecast> forecasts) {
        if (forecasts != null) {
            mForecastData = forecasts;
        } else {
            mForecastData.clear();
        }
        notifyDataSetChanged();
    }

    @Override
    public ForecastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ForecastViewHolder(mInflater.inflate(R.layout.forecast_view_holder, parent, false));
    }

    @Override
    public void onBindViewHolder(ForecastViewHolder holder, final int position) {
        DailyForecast forecast = mForecastData.get(position);

        holder.setDay(forecast.getDayOfWeek());
        holder.setTemperature(String.format("%d°C", forecast.getHourlyForecast().get(0).getTempC()));
        holder.setWeatherDescription(forecast.getHourlyForecast().get(0).getWeatherDescription());

        String iconUrl = forecast.getHourlyForecast().get(0).getWeatherIconUrl();
        Bitmap cachedIcon = ImageCache.getBitmapFromCache(iconUrl);
        if (cachedIcon != null) {
            holder.setWeatherIcon(cachedIcon);
        } else {
            APIService.loadWeatherIcon(iconUrl, new BaseRequestListener<Bitmap>() {
                @Override
                public void onError(RequestError error) {
                }

                @Override
                public void onSuccess(Bitmap response) {
                    notifyItemChanged(position);
                }

                @Override
                public void onCancelled() {
                }

                @Override
                public void onProgress(Integer progress) {
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mForecastData.size();
    }
}
