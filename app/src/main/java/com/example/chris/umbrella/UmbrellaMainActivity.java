package com.example.chris.umbrella;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chris.umbrella.di.umbrellamain.DaggerUmbrellaMainComponent;
import com.example.chris.umbrella.model.HourlyWeatherResponse;
import com.example.chris.umbrella.remote.RemoteService;
import com.example.chris.umbrella.util.NetworkUtils;
import com.example.chris.umbrella.view.umbrellamain.UmbrellaMainContract;
import com.example.chris.umbrella.view.umbrellamain.UmbrellaMainPresenter;

import java.util.Random;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UmbrellaMainActivity extends AppCompatActivity implements UmbrellaMainContract.View
{
    private static final String TAG = UmbrellaMainActivity.class.getSimpleName() + "_TAG";
    
    @Inject
    UmbrellaMainPresenter presenter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        DaggerUmbrellaMainComponent.create().inject(this);
        
        EditText etZipcode = findViewById(R.id.etZip);
        presenter.attachView(this);
//        presenter.getWeather();
    
        Retrofit retrofit = NetworkUtils.configureRetrofitClient();
        RandomService remoteService = retrofit.create(RandomService.class);
        
        remoteService.getRandomUser()
                .enqueue(new Callback<HourlyWeatherResponse>() {
                    @Override
                    public void onResponse(Call<HourlyWeatherResponse> call, Response<HourlyWeatherResponse> response)
                    {
//                        pics = response.body().getItems();
                        
                        if (response.isSuccessful())
                            Log.d(TAG, "onResponse: "+response.body().getHourlyForecast());
                        
                        
                    }
                    
                    @Override
                    public void onFailure(Call<HourlyWeatherResponse> call, Throwable t)
                    {
                    
                    }
                });
    }
    
    @Override
    public void showError(String error)
    {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
    
    @Override
    public void setWeatherResponse(HourlyWeatherResponse weatherResponse)
    {
        Log.d(TAG, "setWeatherResponse: " + weatherResponse.getResponse());
    }
    
    @Override
    public void showProgress(String progress)
    {
    
    }
    
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        presenter.detachView();
    }
}
