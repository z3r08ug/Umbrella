package com.example.chris.umbrella.remote;

import com.example.chris.umbrella.model.Current.WeatherResponse;
import com.example.chris.umbrella.model.Hourly.HourlyWeatherResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by chris on 12/2/2017.
 */

public interface RemoteService
{
    @GET("api/033c3f4b14ddf7fa/hourly/q/{zip}.json")
    Observable<HourlyWeatherResponse> getWeather(@Path("zip") String zipCode);
    @GET("api/033c3f4b14ddf7fa/conditions/q/{zip}.json")
    Observable<WeatherResponse> getCurrentWeather(@Path("zip") String zipCode);
}
