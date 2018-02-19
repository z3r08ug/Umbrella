package com.example.chris.umbrella.application;

import android.app.Application;
import android.content.Context;

import com.example.chris.umbrella.di.app.AppComponent;
import com.example.chris.umbrella.di.app.AppModule;
import com.example.chris.umbrella.di.app.DaggerAppComponent;
import com.example.chris.umbrella.di.main.MainComponent;
import com.example.chris.umbrella.di.main.MainModule;
import com.example.chris.umbrella.util.Constants;

/**
 * Created by chris on 12/7/2017.
 */

public class WeatherApplication extends Application
{
    private AppComponent appComponent;
    private MainComponent mainComponent;
    
    @Override
    public void onCreate()
    {
        super.onCreate();
        AppModule appModule = new AppModule(Constants.BASE_URL);
        
        appComponent = DaggerAppComponent.builder()
                .appModule(appModule)
                .build();
    }
    
    public static WeatherApplication get(Context context)
    {
        return (WeatherApplication) context.getApplicationContext();
    }
    
    public MainComponent getMainComponent()
    {
        mainComponent = appComponent.add(new MainModule());
        return mainComponent;
    }
    
    public void clearMainComponent()
    {
        mainComponent = null;
    }
}
