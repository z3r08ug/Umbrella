package com.example.chris.umbrella.remote;

import com.example.chris.umbrella.model.WeatherResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Admin on 11/29/2017.
 */

public class RemoteDataSource
{
        public static final String BASE_URL = "http://de-coding-test.s3.amazonaws.com/";

        public static Retrofit create()
        {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    //add converter to parse the response
                    .addConverterFactory(GsonConverterFactory.create())
                    //add call adapter to convert the response to RxJava observable
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

            return retrofit;
        }

        public static Observable<List<WeatherResponse>> getBookList()
        {
            Retrofit retrofit = create();
            RemoteService remoteService = retrofit.create(RemoteService.class);
            return remoteService.getBooks();
        }
}
