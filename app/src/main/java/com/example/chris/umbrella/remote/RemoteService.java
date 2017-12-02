package com.example.chris.umbrella.remote;

import com.example.chris.umbrella.model.HourlyWeatherResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Admin on 11/29/2017.
 */

public interface RemoteService
{
    @GET("api/033c3f4b14ddf7fa/hourly/q/CA/San_Francisco.json")
    Observable<HourlyWeatherResponse> getWeather();
}
