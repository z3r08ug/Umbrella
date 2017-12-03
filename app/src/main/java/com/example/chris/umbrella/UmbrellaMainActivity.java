package com.example.chris.umbrella;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.chris.umbrella.di.umbrellamain.DaggerUmbrellaMainComponent;
import com.example.chris.umbrella.model.Current.DisplayLocation;
import com.example.chris.umbrella.model.Current.WeatherResponse;
import com.example.chris.umbrella.model.Hourly.FCTTIME;
import com.example.chris.umbrella.model.Hourly.HourlyForecast;
import com.example.chris.umbrella.model.Hourly.HourlyWeatherResponse;
import com.example.chris.umbrella.util.CardRecycleAdapter;
import com.example.chris.umbrella.util.ForecastRecycleAdapter;
import com.example.chris.umbrella.view.umbrellamain.UmbrellaMainContract;
import com.example.chris.umbrella.view.umbrellamain.UmbrellaMainPresenter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

public class UmbrellaMainActivity extends AppCompatActivity implements UmbrellaMainContract.View
{
    private static final String TAG = UmbrellaMainActivity.class.getSimpleName() + "_TAG";
    
    @Inject
    UmbrellaMainPresenter presenter;
    private String zip;
    private List<HourlyForecast> hourlyForecasts;
    private WeatherResponse currentWeather;
    private TextView tvCurrentCondition;
    private TextView tvCurrentTemp;
    private SharedPreferences mSettings;
    private DisplayLocation displayLocation;
    private AppBarLayout appBarLayout;
    private List<List<HourlyForecast>> cards;
    private List<HourlyForecast> today;
    private List<HourlyForecast> tomorrow;
    private List<HourlyForecast> dayAfter;
    private RecyclerView rvCards;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        DaggerUmbrellaMainComponent.create().inject(this);
        
        tvCurrentTemp = findViewById(R.id.tvCurrentTemp);
        tvCurrentCondition = findViewById(R.id.tvCurrentCondition);
        rvCards = findViewById(R.id.rvCards);
        
        appBarLayout = findViewById(R.id.appbar);
        cards = new ArrayList<>();
        today = new ArrayList<>();
        tomorrow = new ArrayList<>();
        dayAfter = new ArrayList<>();
        
        zip = "30080";
        presenter.attachView(this);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    
    @Override
    protected void onResume()
    {
        super.onResume();
        
        presenter.getHourlyWeather(zip);
        presenter.getCurrentWeather(zip);
    }
    
    @Override
    public void showError(String error)
    {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
    
    
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        presenter.detachView();
    }
    
    @Override
    public void setHourlyWeather(HourlyWeatherResponse weatherResponse)
    {
        hourlyForecasts = weatherResponse.getHourlyForecast();
        
        divideIntoCards();
        
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        
        rvCards.setLayoutManager(layoutManager);
        rvCards.setItemAnimator(itemAnimator);
        
        CardRecycleAdapter recyclerAdapter = new CardRecycleAdapter(cards);
        rvCards.setAdapter(recyclerAdapter);
    }
    
    private void divideIntoCards()
    {
        int date = Calendar.getInstance().getTime().getDate();
        for (HourlyForecast forecast : hourlyForecasts)
        {
            int fDate = Integer.parseInt(forecast.getFCTTIME().getMday());
            if (date == fDate)
                today.add(forecast);
            else if (date + 1 == fDate)
            {
                tomorrow.add(forecast);
            }
            else
                dayAfter.add(forecast);
        }
        cards.add(today);
        cards.add(tomorrow);
        if (dayAfter.size() != 0)
        {
            cards.add(dayAfter);
            Log.d(TAG, "divideIntoCards: Day After" +cards.get(2));
        }
        Log.d(TAG, "divideIntoCards: Today" +cards.get(0));
        Log.d(TAG, "divideIntoCards: Tomorrow" +cards.get(1));
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        return false;
    }
    
    @Override
    public void setCurrentWeather(WeatherResponse weatherResponse)
    {
        currentWeather = weatherResponse;
        
        tvCurrentTemp.setText(currentWeather.getCurrentObservation().getTempF() + "°");
        tvCurrentCondition.setText(currentWeather.getCurrentObservation().getWeather());
        displayLocation = currentWeather.getCurrentObservation().getDisplayLocation();
        getSupportActionBar().setTitle(displayLocation.getCity() + ", " + displayLocation.getState());
        
        if (Double.valueOf(currentWeather.getCurrentObservation().getTempF()) >= 60.0)
        {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this,R.color.weather_warm)));
            appBarLayout.setBackgroundColor(ContextCompat.getColor(this,R.color.weather_warm));
        }
        else
        {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this,R.color.weather_cool)));
            appBarLayout.setBackgroundColor(ContextCompat.getColor(this,R.color.weather_cool));
        }
    }
    
    @Override
    public void showProgress(String progress)
    {
    
    }
}
