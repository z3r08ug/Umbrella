package com.example.chris.umbrella;

import android.app.Application;
import android.content.Context;

import com.example.chris.umbrella.di.umbrellamain.AppComponent;
import com.example.chris.umbrella.di.umbrellamain.AppModule;
import com.example.chris.umbrella.di.umbrellamain.UmbrellaMainComponent;
import com.example.chris.umbrella.model.Current.WeatherResponse;
import com.example.chris.umbrella.util.Constants;

/**
 * Created by chris on 12/7/2017.
 */

public class WeatherApplication extends Application
{
    String apiKey = "";
    private AppComponent appComponent;
    private UmbrellaMainComponent homeComponent;
    
    @Override
    public void onCreate()
    {
        super.onCreate();
        AppModule appModule = new AppModule(Constants.BASE_URL, apiKey);
       // appComponent = DaggerAppComponent.builder()
       //         .appModule(new AppModule(Constants.BASE_URL, apiKey))
       //         .build();
    }
    
    public static WeatherApplication get(Context context)
    {
        return (WeatherApplication) context.getApplicationContext();
    }
    
    public UmbrellaMainComponent getHomeComponent()
    {
//        homeComponent = appComponent.add(new UmbrellaMainComponent());
        return null;
    }
    
    public void clearHomeComponent()
    {
        homeComponent = null;
    }
}
