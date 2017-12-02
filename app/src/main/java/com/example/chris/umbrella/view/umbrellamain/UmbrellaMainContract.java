package com.example.chris.umbrella.view.umbrellamain;

import com.example.chris.umbrella.model.HourlyWeatherResponse;
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
        void setWeatherResponse(HourlyWeatherResponse weatherResponses);
        void showProgress(String progress);
    }

    interface Presenter extends BasePresenter<View>
    {
        void getWeather();
    }
}
