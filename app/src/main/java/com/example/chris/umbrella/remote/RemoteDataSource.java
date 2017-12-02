package com.example.chris.umbrella.remote;

import com.example.chris.umbrella.model.HourlyWeatherResponse;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Admin on 11/29/2017.
 */

public class RemoteDataSource
{
        public static final String BASE_URL = "http://www.wunderground.com/";

        public static Retrofit create()
        {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
            
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    //add interceptor
                    .client(client)
                    //add converter to parse the response
                    .addConverterFactory(GsonConverterFactory.create())
                    //add call adapter to convert the response to RxJava observable
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

            return retrofit;
        }

        public static Observable<HourlyWeatherResponse> getWeather()
        {
            Retrofit retrofit = create();
            RemoteService remoteService = retrofit.create(RemoteService.class);
            return remoteService.getWeather();
        }
}
