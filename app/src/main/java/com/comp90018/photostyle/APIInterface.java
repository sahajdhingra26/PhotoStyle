package com.comp90018.photostyle;





import com.comp90018.photostyle.helpers.WeatherList;

import retrofit2.Call;
import retrofit2.http.GET;

import retrofit2.http.Query;



public interface APIInterface {



    @GET("weather?")
    Call<WeatherList> getCurrentWeather(@Query("lat") double lat, @Query("lon") double lng, @Query("appid") String appid);


}
