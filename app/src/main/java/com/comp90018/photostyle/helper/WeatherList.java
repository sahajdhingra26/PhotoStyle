package com.comp90018.photostyle.helpers;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;



public class WeatherList {

    public List<DatumCoordinates> coordinates = new ArrayList<>();

    public class DatumCoordinates {

        @SerializedName("lon")
        public double lon;
        @SerializedName("lat")
        public double lat;
    }
}
