package com.example.chris.umbrella.view.umbrellamain;

import android.util.Log;

import com.example.chris.umbrella.model.Current.WeatherResponse;
import com.example.chris.umbrella.model.Hourly.HourlyForecast;
import com.example.chris.umbrella.model.Hourly.HourlyWeatherResponse;
import com.example.chris.umbrella.remote.RemoteDataSource;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Admin on 11/29/2017.
 */

public class UmbrellaMainPresenter implements UmbrellaMainContract.Presenter
{
    UmbrellaMainContract.View view;
    public static final String TAG = UmbrellaMainPresenter.class.getSimpleName() + "_TAG";
    private HourlyWeatherResponse weatherResponse;
    private WeatherResponse currentWeather;
    private List<HourlyForecast> forecasts;
    
    
    @Override
    public void attachView(UmbrellaMainContract.View view)
    {
        this.view = view;
    }

    @Override
    public void detachView()
    {

    }

    @Override
    public void getHourlyWeather(String zip)
    {

        Log.d(TAG, "getWeather: ");
        RemoteDataSource.getHourlyWeather(zip)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<HourlyWeatherResponse>()
                {
                    @Override
                    public void onSubscribe(Disposable d)
                    {
                        view.showProgress("Downloading weather data...");
                    }

                    @Override
                    public void onNext(HourlyWeatherResponse weather)
                    {
                        weatherResponse = weather;
                        Log.d(TAG, "onNext: " + weather.toString());
                    }

                    @Override
                    public void onError(Throwable e)
                    {
                        view.showError(e.toString());
                        Log.d(TAG, "onError: "+e.toString());
                    }

                    @Override
                    public void onComplete()
                    {
                        view.setHourlyWeather(weatherResponse);
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }
    
    @Override
    public void getCurrentWeather(String zip)
    {
        RemoteDataSource.getCurrentWeather(zip)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<WeatherResponse>()
                {
                    @Override
                    public void onSubscribe(Disposable d)
                    {
                        view.showProgress("Downloading weather data...");
                    }
                
                    @Override
                    public void onNext(WeatherResponse weather)
                    {
                        currentWeather = weather;
                        Log.d(TAG, "onNext: " + weather.toString());
                    }
                
                    @Override
                    public void onError(Throwable e)
                    {
                        view.showError(e.toString());
                        Log.d(TAG, "onError: "+e.toString());
                    }
                
                    @Override
                    public void onComplete()
                    {
                        view.setCurrentWeather(currentWeather);
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }
}
