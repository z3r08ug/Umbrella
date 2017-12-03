package com.example.chris.umbrella.util;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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
import java.util.Calendar;
import java.util.List;

/**
 * Created by chris on 12/2/2017.
 */

public class CardRecycleAdapter extends RecyclerView.Adapter<CardRecycleAdapter.ViewHolder>
{
    private static final String TAG = CardRecycleAdapter.class.getSimpleName() + "_TAG";
    List<List<HourlyForecast>> cards = new ArrayList<>();
    Context context;
    private String date;
    
    public CardRecycleAdapter(List<List<HourlyForecast>> cards)
    {
        this.cards = cards;
    }
    
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.forecast_cardview, null);
        context = parent.getContext();
        return new ViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(CardRecycleAdapter.ViewHolder holder, int position)
    {
        List<HourlyForecast> card = cards.get(position);
        if(card != null)
        {
            if (position == 0)
            {
                holder.tvDay.setText("Today");
            }
            else if (position == 1)
            {
                holder.tvDay.setText("Tomorrow");
            }
            else
            {
                int date = Calendar.getInstance().getTime().getDate();
                int month = Calendar.getInstance().getTime().getMonth();
                int year = Calendar.getInstance().getTime().getMonth();
                Log.d(TAG, "onBindViewHolder: " + year);
                if (month == 12 && date + 2 > 31)
                {
                    month = 1;
                    date -= 31;
                    year++;
                    formatDate(month, date, year);
                    holder.tvDay.setText(this.date);
                }
                else if (date + 2 > 30 && (month == 9 || month == 4 || month == 6 || month == 11))
                {
                    month += 1;
                    date -= 30;
                    formatDate(month, date, year);
                    holder.tvDay.setText(this.date);
                }
                else if (date + 2 > 31)
                {
                    month += 1;
                    date -= 31;
                    formatDate(month, date, year);
                    holder.tvDay.setText(this.date);
                }
                else
                {
                    formatDate(month, date, year);
                    holder.tvDay.setText(this.date);
                }
            }
    
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 4, LinearLayoutManager.VERTICAL, false);
            RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
    
            holder.recyclerView.setLayoutManager(layoutManager);
            holder.recyclerView.setItemAnimator(itemAnimator);
    
            ForecastRecycleAdapter recyclerAdapter = new ForecastRecycleAdapter(card);
            holder.recyclerView.setAdapter(recyclerAdapter);
        }
    }
    
    private void formatDate(int m, int d, int y)
    {
        date = "";
        switch (m)
        {
            case 1:
                date += "January";
                break;
            case 2:
                date += "February";
                break;
            case 3:
                date += "March";
                break;
            case 4:
                date += "April";
                break;
            case 5:
                date += "May";
                break;
            case 6:
                date += "June";
                break;
            case 7:
                date += "July";
                break;
            case 8:
                date += "August";
                break;
            case 9:
                date += "September";
                break;
            case 10:
                date += "October";
                break;
            case 11:
                date += "November";
                break;
            case 12:
                date += "December";
                break;
        }
        date += d + ", " + y;
    }
    
    @Override
    public int getItemCount()
    {
        return cards.size();
    }
    
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private final TextView tvDay;
        private final RecyclerView recyclerView;
        public ViewHolder(View itemView)
        {
            super(itemView);
            tvDay = itemView.findViewById(R.id.tvDay);
            recyclerView = itemView.findViewById(R.id.rvForecast);
        }
    }
}