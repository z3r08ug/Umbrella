package com.example.chris.umbrella.view.umbrellamain;

import com.example.chris.umbrella.model.Current.WeatherResponse;
import com.example.chris.umbrella.model.Hourly.HourlyWeatherResponse;
import com.example.chris.umbrella.util.BasePresenter;
import com.example.chris.umbrella.util.BaseView;


/**
 * Created by Admin on 11/29/2017.
 */

public interface UmbrellaMainContract
{
    //methods for main activity
    interface View extends BaseView
    {
        void setHourlyWeather(HourlyWeatherResponse weatherResponse);
        void setCurrentWeather(WeatherResponse weatherResponse);
        void showProgress(String progress);
    }

    interface Presenter extends BasePresenter<View>
    {
        void getHourlyWeather(String zip);
        void getCurrentWeather(String zip);
    }
}
