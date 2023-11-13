package com.example.metactivity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherServices {
    @GET("forecast?")
    Call<ForecastData> getForcast(@Query("q") String city, @Query("appid") String appid, @Query("units") String metric);
}
