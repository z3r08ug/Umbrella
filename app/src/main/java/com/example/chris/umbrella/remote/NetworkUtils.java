package com.example.chris.umbrella.remote;

import com.example.chris.umbrella.util.Constants;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by chris on 12/2/2017.
 */

public class NetworkUtils
{
    
    public static Retrofit configureRetrofitClient()
    {
        //set custom okhttpclient to retrofit instance
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(configureHttpClient(configureInterceptor()))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    
    private static OkHttpClient configureHttpClient(HttpLoggingInterceptor interceptor)
    {
        //set interceptor to custom okHttpClient
        OkHttpClient clientWithInterceptor = new OkHttpClient.Builder()
                .addInterceptor(interceptor).build();
        return clientWithInterceptor;
    }
    
    private static HttpLoggingInterceptor configureInterceptor()
    {
        //create amd configure network interceptor
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }
}
