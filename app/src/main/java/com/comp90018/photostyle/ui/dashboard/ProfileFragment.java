package com.comp90018.photostyle.ui.dashboard;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.comp90018.photostyle.APIClient;
import com.comp90018.photostyle.APIInterface;
import com.comp90018.photostyle.R;
import com.comp90018.photostyle.helpers.ImageAdapter;
import com.comp90018.photostyle.helpers.ImageList;

import com.pixplicity.easyprefs.library.Prefs;


import java.util.List;



public class ProfileFragment extends Fragment  {

    private ProfileViewModel profileViewModel;
    APIInterface apiInterface;
    GridView gridView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        gridView = (GridView) root.findViewById(R.id.gridView);
        apiInterface = APIClient.getClient2().create(APIInterface.class);

        try {
            Call<ImageList> call2 = apiInterface.getWardrobe(Prefs.getString("email",""));
            call2.enqueue(new Callback<ImageList>() {
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
                                Toast.makeText(getActivity().getApplicationContext(),
                                        ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else if(imageList.getSuccess()==0){


                    }



                }

                @Override
                public void onFailure(Call<ImageList> call, Throwable t) {
                    Log.d("Datum ","error");
                    call.cancel();


                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }



        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}