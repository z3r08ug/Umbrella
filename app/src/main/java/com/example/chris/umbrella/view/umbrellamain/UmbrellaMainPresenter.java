package com.example.chris.umbrella.view.umbrellamain;

import android.util.Log;

import com.example.chris.umbrella.model.HourlyWeatherResponse;
import com.example.chris.umbrella.remote.RemoteDataSource;

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
    public void getWeather()
    {
    
        Log.d(TAG, "getWeather: ");
        RemoteDataSource.getWeather()
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
                        Log.d(TAG, "onNext: "+weather.getResponse().getFeatures().getHourly());
                        weatherResponse = weather;
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
                        view.setWeatherResponse(weatherResponse);
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }
}
