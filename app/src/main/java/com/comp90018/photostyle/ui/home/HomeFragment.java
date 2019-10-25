package com.comp90018.photostyle.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;

import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;



import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.comp90018.photostyle.APIClient;
import com.comp90018.photostyle.APIInterface;
import com.comp90018.photostyle.R;
import com.comp90018.photostyle.helpers.ImageAdapter;
import com.comp90018.photostyle.helpers.ImageList;

import com.comp90018.photostyle.helpers.WeatherList;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.List;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    APIInterface apiInterface,apiInterface2;
    @BindView(R.id.location)
    TextView location;
    @BindView(R.id.season_bg)
    ImageView season_bg;
    @BindView(R.id.weather_details)
    TextView weather_details;
    @BindView(R.id.minmax)
    TextView minmax;
    @BindView(R.id.humidity)
    TextView humidity;

    @BindView(R.id.temp)
    TextView temp;

    @BindView(R.id.cloth_item)
    GridView gridView;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, root);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        apiInterface2 = APIClient.getClient2().create(APIInterface.class);
        double lat = Prefs.getDouble("lat",-37.806327);
        double lng = Prefs.getDouble("lng",144.976511);
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        gridView.setColumnWidth(metrics.widthPixels / 3);



        Call<WeatherList> call2 = apiInterface.getCurrentWeather(lat,lng,"3bc96ea77cbbadba4032713823a05bb1");
        call2.enqueue(new Callback<WeatherList>() {
            @Override
            public void onResponse(Call<WeatherList> call, Response<WeatherList> response) {

                WeatherList weatherList = response.body();

                location.setText(weatherList.getName());
                temp.setText(getCelcius(weatherList.getMain().getTemp())+"\u00B0"+"C");
                minmax.setText(getCelcius(weatherList.getMain().getTempMin())+"\u00B0"+"C - "+getCelcius(weatherList.getMain().getTempMax())+"\u00B0"+"C");
                weather_details.setText(weatherList.getWeather().get(0).getMain());
                humidity.setText("Humidity "+weatherList.getMain().getHumidity()+"%");
                int season = weatherList.getWeather().get(0).getId();
                int temp = Integer.parseInt(getCelcius(weatherList.getMain().getTemp()));
                int seasonId=R.drawable.clearsky;
                String seasonName="Summer(Sunny)";

                if((season>=200 && season<=531) || (season>=801 && season<=804) ){
                    seasonId = R.drawable.rain2;
                    seasonName="Rainy(Monsoon)";
                }
                else if(season==800 || temp>20) {
                    seasonId = R.drawable.clearsky;
                    seasonName="Summer(Sunny)";

                }
                else if((season>=600 && season<=600)  || temp < 10) {
                    seasonId = R.drawable.winter;
                    seasonName="Winter(Cold)";
                }
                else if((season>=701 && season<=781)){
                    seasonId = R.drawable.fog;
                    seasonName="Winter(Cold)";
                }
                season_bg.setImageResource(seasonId);


                try {
                    Call<ImageList> call3 = apiInterface2.getWeatherWardrobe(Prefs.getString("email",""), seasonName);
                    call3.enqueue(new Callback<ImageList>() {
                        @Override
                        public void onResponse(Call<ImageList> call2, Response<ImageList> response) {

                            ImageList imageList = response.body();
                            if(imageList.getSuccess()==1){

                                List<String> images =  imageList.getSrc();
                                List<String> imageLabels =  imageList.getImageLabel();





                                gridView.setAdapter(new ImageAdapter(getActivity(), images,imageLabels));

                                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    public void onItemClick(AdapterView<?> parent, View v,
                                                            int position, long id) {

                                    }
                                });
                            }
                            else if(imageList.getSuccess()==0){


                            }




                        }

                        @Override
                        public void onFailure(Call<ImageList> call, Throwable t) {
                            call.cancel();


                        }
                    });
                }
                catch (Exception e){
                    e.printStackTrace();
                }












            }

            @Override
            public void onFailure(Call<WeatherList> call, Throwable t) {
                call.cancel();
            }
        });









        return root;
    }

    public String getCelcius(double a){
        int b=(int)(a-273.15);
        String r=String.valueOf(b);
        return r;
    }
}