package com.example.chris.umbrella.di.main;

import com.example.chris.umbrella.view.main.MainActivity;

import javax.inject.Singleton;

import dagger.Subcomponent;


/**
 * Created by Admin on 11/29/2017.
 */

@Subcomponent(modules = MainModule.class)
@Singleton
public interface MainComponent
{
    void inject(MainActivity mainActivity);
}
