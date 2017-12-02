package com.example.chris.umbrella.remote;

import com.example.chris.umbrella.model.WeatherResponse;

import java.util.List;
import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by Admin on 11/29/2017.
 */

public interface RemoteService
{
    @GET("books.json")
    Observable<List<WeatherResponse>> getBooks();
}
