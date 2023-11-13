package com.example.metactivity;

import retrofit2.Call;
import retrofit2.http.GET;

public interface OpenWeatherServices {
    @GET("q=Annecy&appid=e075a0b59517e88cc46940bb262add13&units=metric")
    Call<Forecast> getForcast();
}
