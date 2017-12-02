package com.example.chris.umbrella.view.umbrellamain;

import com.example.chris.umbrella.model.WeatherResponse;
import com.example.chris.umbrella.util.BasePresenter;
import com.example.chris.umbrella.util.BaseView;

import java.util.List;


/**
 * Created by Admin on 11/29/2017.
 */

public interface UmbrellaMainContract
{
    //methods for main activity
    interface View extends BaseView
    {
        void setBooks(List<WeatherResponse> weatherResponses);
        void showProgress(String progress);
    }

    interface Presenter extends BasePresenter<View>
    {
        void getBooks();
    }
}
