package com.example.chris.umbrella.di.umbrellamain;

import com.example.chris.umbrella.view.umbrellamain.UmbrellaMainPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Admin on 11/29/2017.
 */

@Module
public class UmbrellaMainModule
{
    @Provides
    @Singleton
    UmbrellaMainPresenter providerUmbrellaMainPresenter()
    {
        return new UmbrellaMainPresenter();
    }
}
