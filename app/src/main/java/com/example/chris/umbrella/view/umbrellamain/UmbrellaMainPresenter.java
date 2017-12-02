package com.example.chris.umbrella.view.umbrellamain;

import com.example.chris.umbrella.model.WeatherResponse;
import com.example.chris.umbrella.remote.RemoteDataSource;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Admin on 11/29/2017.
 */

public class UmbrellaMainPresenter implements UmbrellaMainContract.Presenter
{
    UmbrellaMainContract.View view;
    public static final String TAG = UmbrellaMainPresenter.class.getSimpleName() + "_TAG";
    List<WeatherResponse> weatherResponses = new ArrayList<>();

    @Override
    public void attachView(UmbrellaMainContract.View view)
    {
        this.view = view;
    }

    @Override
    public void detachView()
    {

    }

    @Override
    public void getBooks()
    {

        RemoteDataSource.getBookList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<WeatherResponse>>()
                {
                    @Override
                    public void onSubscribe(Disposable d)
                    {
                        view.showProgress("Downloading books...");
                    }

                    @Override
                    public void onNext(List<WeatherResponse> weatherResponses)
                    {
                        weatherResponses.addAll(weatherResponses);
                    }

                    @Override
                    public void onError(Throwable e)
                    {
                        view.showError(e.toString());
                    }

                    @Override
                    public void onComplete()
                    {
                        view.setBooks(weatherResponses);
                        view.showProgress("Downloaded all books");
                    }
                });
    }


}
