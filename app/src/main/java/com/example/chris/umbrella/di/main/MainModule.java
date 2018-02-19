package com.example.chris.umbrella.di.main;

import com.example.chris.umbrella.view.main.MainPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Admin on 11/29/2017.
 */

@Module
public class MainModule
{
    @Provides
    @Singleton
    MainPresenter providerUmbrellaMainPresenter()
    {
        return new MainPresenter();
    }
}
