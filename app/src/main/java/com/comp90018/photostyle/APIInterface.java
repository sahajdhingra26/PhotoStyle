package com.comp90018.photostyle;

import com.comp90018.photostyle.helpers.ImageList;
import com.comp90018.photostyle.helpers.UserList;
import com.comp90018.photostyle.helpers.WeatherList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;

import retrofit2.http.POST;
import retrofit2.http.Query;



public interface APIInterface {



    @GET("weather?")
    Call<WeatherList> getCurrentWeather(@Query("lat") double lat, @Query("lon") double lng, @Query("appid") String appid);


    @FormUrlEncoded
    @POST("/sahaj/photo-style/services/register-submit.php?")
    Call<UserList> registerUser(@Field("name") String name, @Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("/sahaj/photo-style/services/login-submit.php?")
    Call<UserList> loginUser(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("/sahaj/photo-style/services/saveImage.php?")
    Call<UserList> saveWardrobe(@Field("email") String email, @Field("src") String src, @Field("season") String season, @Field("type") String type, @Field("category") String category, @Field("image_label") String image_label);


    @FormUrlEncoded
    @POST("/sahaj/photo-style/services/getWardrobe.php?")
    Call<ImageList> getWardrobe(@Field("email") String email);


    @FormUrlEncoded
    @POST("/sahaj/photo-style/services/getWeatherWardrobe.php?")
    Call<ImageList> getWeatherWardrobe(@Field("email") String email,@Field("season") String season);

    @FormUrlEncoded
    @POST("/sahaj/photo-style/services/setEvent.php?")
    Call<UserList> setEvent(@Field("email") String email,@Field("date") String date,@Field("event") String type);

    @FormUrlEncoded
    @POST("/sahaj/photo-style/services/getEvent.php?")
    Call<ImageList> getEvent(@Field("email") String email);


}


