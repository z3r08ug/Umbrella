package com.example.chris.umbrella.view.main;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chris.umbrella.R;
import com.example.chris.umbrella.application.WeatherApplication;
import com.example.chris.umbrella.exceptions.ZipcodeNotRecognizedException;
import com.example.chris.umbrella.model.Current.DisplayLocation;
import com.example.chris.umbrella.model.Current.WeatherResponse;
import com.example.chris.umbrella.model.Hourly.HourlyForecast;
import com.example.chris.umbrella.model.Hourly.HourlyWeatherResponse;
import com.example.chris.umbrella.util.adapters.CardRecycleAdapter;
import com.example.chris.umbrella.view.settings.SettingsActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements MainContract.View
{
    private static final String TAG = MainActivity.class.getSimpleName() + "_TAG";
    
    @Inject
    MainPresenter presenter;
    private String zip;
    private List<HourlyForecast> hourlyForecasts;
    private WeatherResponse currentWeather;
    private TextView tvCurrentCondition;
    private TextView tvCurrentTemp;
    private DisplayLocation displayLocation;
    private AppBarLayout appBarLayout;
    private List<List<HourlyForecast>> cards;
    private List<HourlyForecast> today;
    private List<HourlyForecast> tomorrow;
    private List<HourlyForecast> dayAfter;
    private RecyclerView rvCards;
    private boolean validZip;
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        WeatherApplication.get(this).getMainComponent().inject(this);
        
        tvCurrentTemp = findViewById(R.id.tvCurrentTemp);
        tvCurrentCondition = findViewById(R.id.tvCurrentCondition);
        rvCards = findViewById(R.id.rvCards);
        
        appBarLayout = findViewById(R.id.appbar);
        initWeatherData();
        
        presenter.attachView(this);
        
        
    }
    
    @Override
    protected void onStart()
    {
        super.onStart();
    }
    
    @Override
    protected void onStop()
    {
        super.onStop();
        WeatherApplication.get(this).clearMainComponent();
    }
    
    private void initWeatherData()
    {
        cards = new ArrayList<>();
        today = new ArrayList<>();
        tomorrow = new ArrayList<>();
        dayAfter = new ArrayList<>();
        validZip = false;
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
        sharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        
        zip = sharedPreferences.getString("zip", "");
        initWeatherData();
        requestZip();
    }
    
    private void requestZip()
    {
        if (zip.equals(""))
        {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            final EditText edittext = new EditText(this);
            edittext.setInputType(InputType.TYPE_CLASS_NUMBER);
            alert.setMessage("Enter Zipcode");
            alert.setTitle("Enter Zipcode");
            
            alert.setView(edittext);
            alert.setPositiveButton("Accept", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int whichButton)
                {
                    zip = edittext.getText().toString();
                    try
                    {
                        //check to make sure zip is 5 digits
                        if (zip.length() != 5)
                            throw new ZipcodeNotRecognizedException();
                        //check to make sure zip is only numbers
                        Integer.parseInt(zip);
                        editor.putString("zip", zip);
                        boolean isSaved = editor.commit();
                        if (isSaved)
                        {
                            validZip = true;
                            Toast.makeText(MainActivity.this, "Saved zipcode", Toast.LENGTH_SHORT).show();
                            requestZip();
                        }
                    }
                    catch (ZipcodeNotRecognizedException e)
                    {
                        Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                        requestZip();
                    }
                    catch (NumberFormatException e)
                    {
                        Toast.makeText(MainActivity.this, "Zipcode must only contain numbers.", Toast.LENGTH_SHORT).show();
                        requestZip();
                    }
                }
            });
            
            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int whichButton)
                {
                    Toast.makeText(MainActivity.this, "Valid zipcode must be entered.", Toast.LENGTH_SHORT).show();
                    requestZip();
                }
            });
            
            alert.show();
        }
        else
            validZip = true;
        
        if (validZip)
        {
            presenter.getHourlyWeather(zip);
            presenter.getCurrentWeather(zip);
        }
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
        Log.d(TAG, "setCurrentWeather: "+weatherResponse);
        currentWeather = weatherResponse;
        
        if (sharedPreferences.getString("units", "Fahrenheit").equals("Fahrenheit"))
            tvCurrentTemp.setText(new StringBuilder().append(currentWeather.getCurrentObservation().getTempF()).append("°").toString());
        else
            tvCurrentTemp.setText(currentWeather.getCurrentObservation().getTempC() + "°");
        tvCurrentCondition.setText(currentWeather.getCurrentObservation().getWeather());
        displayLocation = currentWeather.getCurrentObservation().getDisplayLocation();
        getSupportActionBar().setTitle(displayLocation.getCity() + ", " + displayLocation.getState());
        
        if (Double.valueOf(currentWeather.getCurrentObservation().getTempF()) >= 60.0)
        {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.weather_warm)));
            appBarLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.weather_warm));
        }
        else
        {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.weather_cool)));
            appBarLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.weather_cool));
        }
    }
    
    @Override
    public void showProgress(String progress)
    {
        Toast.makeText(this, progress, Toast.LENGTH_SHORT).show();
    }
}
