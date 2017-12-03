package com.example.chris.umbrella.util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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

/**
 * Created by Admin on 11/27/2017.
 */

public class ForecastRecycleAdapter extends RecyclerView.Adapter<ForecastRecycleAdapter.ViewHolder>
{
    List<HourlyForecast> hourlyForecasts = new ArrayList<>();
    Context context;

    public ForecastRecycleAdapter(List<HourlyForecast> hourlyForecasts)
    {
        this.hourlyForecasts = hourlyForecasts;
//        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.weather_item, null);
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
            String timeOfDay;
            if (hour > 12)
            {
                hour -= 12;
                timeOfDay = " PM";
            }
            else if (hour == 12)
            {
                timeOfDay = " PM";
            }
            else
            {
                timeOfDay = " AM";
            }
            
            String time = String.format("hh:mm",hour + fcttime.getMin());
            time += timeOfDay;
            holder.tvTime.setText(time);
            holder.tvTemp.setText(forecast.getTemp().getEnglish());
            
            //set image based on forecast condition
            if (forecast.getIcon().contains("fog"))
                holder.ivCondtion.setImageResource(R.drawable.weather_fog);
            else if (forecast.getIcon().contains("clear"))
                holder.ivCondtion.setImageResource(R.drawable.weather_sunny);
            else if (forecast.getIcon().contains("rain"))
                holder.ivCondtion.setImageResource(R.drawable.weather_rainy);
            else if (forecast.getIcon().contains("lightning"))
                holder.ivCondtion.setImageResource(R.drawable.weather_lightning);
            else if (forecast.getIcon().contains("snow"))
                holder.ivCondtion.setImageResource(R.drawable.weather_snowy);
            else if (forecast.getIcon().contains("wind"))
                holder.ivCondtion.setImageResource(R.drawable.weather_windy);
            else if (forecast.getIcon().equals("cloudy") || forecast.getIcon().equals("mostlycloudy"))
                holder.ivCondtion.setImageResource(R.drawable.weather_cloudy);
            else if (forecast.getIcon().equals("partlycloudy"))
                holder.ivCondtion.setImageResource(R.drawable.weather_partlycloudy);
            else if (forecast.getIcon().contains("hail"))
                holder.ivCondtion.setImageResource(R.drawable.weather_hail);
            
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
        private final ImageView ivCondtion;
        public ViewHolder(View itemView)
        {
            super(itemView);
            tvTemp = itemView.findViewById(R.id.tvTemp);
            tvTime = itemView.findViewById(R.id.tvTime);
            ivCondtion = itemView.findViewById(R.id.ivHourlyCondition);
        }
    }
}
