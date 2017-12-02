package com.example.chris.umbrella;

import com.example.chris.umbrella.model.HourlyWeatherResponse;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by Admin on 11/16/2017.
 */

public class RetrofitHelper1
{
    public static final String BASE_URL = "http://www.wunderground.com/";

    public static Retrofit create()
    {
    
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

    //call interface to get response
    public static Call<HourlyWeatherResponse> getWeather()
    {
        Retrofit retrofit = create();
        RetrofitService service = retrofit.create(RetrofitService.class);
        return service.getWeather();
    }

    //create an interface for http verbs
    public interface RetrofitService
    {
        @GET("api/033c3f4b14ddf7fa/hourly/q/CA/San_Francisco.json")
        Call<HourlyWeatherResponse> getWeather();
    }
}