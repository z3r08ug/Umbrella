package com.example.chris.umbrella;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chris.umbrella.di.umbrellamain.DaggerUmbrellaMainComponent;
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
    private List<HourlyForecast> hourlyForecasts;
    private String zip;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        
        DaggerUmbrellaMainComponent.create().inject(this);
    
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
        Log.d(TAG, "setCurrentWeather: "+weatherResponse);
    }
    
    @Override
    public void showProgress(String progress)
    {
    
    }
}
