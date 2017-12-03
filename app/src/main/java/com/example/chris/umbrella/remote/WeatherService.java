package com.example.chris.umbrella.remote;

import com.example.chris.umbrella.model.Hourly.HourlyWeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by chris on 12/2/2017.
 */

public interface WeatherService
{
    @GET("api/033c3f4b14ddf7fa/hourly/q/CA/San_Francisco.json")
    Call<HourlyWeatherResponse> getRandomUser();
}

