package com.example.chris.umbrella;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.chris.umbrella.di.umbrellamain.DaggerUmbrellaMainComponent;
import com.example.chris.umbrella.model.Current.DisplayLocation;
import com.example.chris.umbrella.model.Current.WeatherResponse;
import com.example.chris.umbrella.model.Hourly.HourlyForecast;
import com.example.chris.umbrella.model.Hourly.HourlyWeatherResponse;
import com.example.chris.umbrella.view.umbrellamain.UmbrellaMainContract;
import com.example.chris.umbrella.view.umbrellamain.UmbrellaMainPresenter;

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
    private android.support.v7.widget.Toolbar toolbar;
    private DisplayLocation displayLocation;
    private AppBarLayout appBarLayout;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        DaggerUmbrellaMainComponent.create().inject(this);
        
        tvCurrentTemp = findViewById(R.id.tvCurrentTemp);
        tvCurrentCondition = findViewById(R.id.tvCurrentCondition);
        toolbar = findViewById(R.id.toolbar);
    
        appBarLayout = findViewById(R.id.appbar);
        
        
        zip = "07065";
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
