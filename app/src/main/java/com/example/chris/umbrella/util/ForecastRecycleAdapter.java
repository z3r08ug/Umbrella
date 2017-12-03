package com.example.chris.umbrella.util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chris.umbrella.R;
import com.example.chris.umbrella.model.Hourly.FCTTIME;
import com.example.chris.umbrella.model.Hourly.HourlyForecast;

import java.util.ArrayList;
import java.util.List;

import static com.example.chris.umbrella.R.*;

/**
 * Created by Admin on 11/27/2017.
 */

public class ForecastRecycleAdapter extends RecyclerView.Adapter<ForecastRecycleAdapter.ViewHolder>
{
    private static final String TAG = ForecastRecycleAdapter.class.getSimpleName() + "_TAG";
    List<HourlyForecast> hourlyForecasts = new ArrayList<>();
    Context context;
    private int high;
    private int low;
    private boolean tint;
    
    public ForecastRecycleAdapter(List<HourlyForecast> hourlyForecasts)
    {
        this.hourlyForecasts = hourlyForecasts;
    
        high = 0;
        low = 0;
        tint = true;
    
        //loop through list of forecasts and determine high and low temps
        for (HourlyForecast forecast : hourlyForecasts)
        {
            if (Integer.parseInt(forecast.getTemp().getEnglish()) > Double.parseDouble(hourlyForecasts.get(high).getTemp().getEnglish()))
            {
                high = hourlyForecasts.lastIndexOf(forecast);
            }
    
            if (Integer.parseInt(forecast.getTemp().getEnglish()) < Double.parseDouble(hourlyForecasts.get(low).getTemp().getEnglish()))
            {
                low = hourlyForecasts.lastIndexOf(forecast);
            }
        }
        Log.d(TAG, "ForecastRecycleAdapter: high " + high);
        Log.d(TAG, "ForecastRecycleAdapter: low " + low);
        if (low == high)
            tint = false;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(layout.weather_item, null);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ForecastRecycleAdapter.ViewHolder holder, int position)
    {
        HourlyForecast forecast = hourlyForecasts.get(position);
        if(forecast != null)
        {
            FCTTIME fcttime = forecast.getFCTTIME();
            int hour = Integer.parseInt(fcttime.getHour());
            String timeOfDay = "";
            if (hour > 12)
            {
                hour -= 12;
                timeOfDay = " PM";
            }
            else if (hour == 12)
            {
                timeOfDay = " PM";
            }
            else if (hour == 0)
            {
                hour = 12;
                timeOfDay = " AM";
            }
            else
            {
                timeOfDay = " AM";
            }
            
            String time = hour + ":00 " + timeOfDay;
            if (tint && position == high)
            {
                Log.d(TAG, "onBindViewHolder: Set warm tint");
                holder.tvTime.setTextColor(context.getResources().getColor(color.weather_warm));
                holder.tvTemp.setTextColor(context.getResources().getColor(color.weather_warm));
                holder.ivCondition.setColorFilter(context.getResources().getColor(color.weather_warm));
            }
            else if (tint && position == low)
            {
                Log.d(TAG, "onBindViewHolder: Set cool tint");
                holder.tvTime.setTextColor(context.getResources().getColor(color.weather_cool));
                holder.tvTemp.setTextColor(context.getResources().getColor(color.weather_cool));
                holder.ivCondition.setColorFilter(context.getResources().getColor(color.weather_cool));
            }
            holder.tvTime.setText(time);
            holder.tvTemp.setText(forecast.getTemp().getEnglish());
            
            setConditionImage(holder, forecast);
    
    
        }
    }
    
    private void setConditionImage(ViewHolder holder, HourlyForecast forecast)
    {
        //set image based on forecast condition
        if (forecast.getIcon().contains("fog"))
        {
            holder.ivCondition.setImageResource(drawable.weather_fog);
        }
        else if (forecast.getIcon().contains("clear"))
        {
            holder.ivCondition.setImageResource(drawable.weather_sunny);
        }
        else if (forecast.getIcon().contains("rain"))
        {
            holder.ivCondition.setImageResource(drawable.weather_rainy);
        }
        else if (forecast.getIcon().contains("lightning"))
        {
            holder.ivCondition.setImageResource(drawable.weather_lightning);
        }
        else if (forecast.getIcon().contains("snow"))
        {
            holder.ivCondition.setImageResource(drawable.weather_snowy);
        }
        else if (forecast.getIcon().contains("wind"))
        {
            holder.ivCondition.setImageResource(drawable.weather_windy);
        }
        else if (forecast.getIcon().equals("cloudy") || forecast.getIcon().equals("mostlycloudy"))
        {
            holder.ivCondition.setImageResource(drawable.weather_cloudy);
        }
        else if (forecast.getIcon().equals("partlycloudy"))
        {
            holder.ivCondition.setImageResource(drawable.weather_partlycloudy);
        }
        else if (forecast.getIcon().contains("hail"))
        {
            holder.ivCondition.setImageResource(drawable.weather_hail);
        }
    }
    
    @Override
    public int getItemCount()
    {
        return hourlyForecasts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private final TextView tvTemp;
        private final TextView tvTime;
        private final ImageView ivCondition;
        public ViewHolder(View itemView)
        {
            super(itemView);
            tvTemp = itemView.findViewById(id.tvTemp);
            tvTime = itemView.findViewById(id.tvTime);
            ivCondition = itemView.findViewById(id.ivHourlyCondition);
        }
    }
}
