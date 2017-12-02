package com.example.chris.umbrella.di.umbrellamain;

import com.example.chris.umbrella.UmbrellaMainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Admin on 11/29/2017.
 */

@Component(modules = UmbrellaMainModule.class)
@Singleton
public interface UmbrellaMainComponent
{

    void inject(UmbrellaMainActivity umbrellaMainActivity);
}
