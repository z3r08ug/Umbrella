package com.example.chris.umbrella.di.app;

import com.example.chris.umbrella.remote.RemoteDataSource;

import dagger.Module;
import dagger.Provides;

/**
 * Created by chris on 12/7/2017.
 */
@Module
public class AppModule
{
    String BaseURL;
    
    String apiKey;
    
    public AppModule(String baseURL)
    {
        this.BaseURL = baseURL;
    }
    
    @Provides
    RemoteDataSource providesRemoteDataSource()
    {
        return new RemoteDataSource();
    }
}
