package com.comp90018.photostyle.ui.calender;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;

import com.comp90018.photostyle.APIClient;
import com.comp90018.photostyle.APIInterface;
import com.comp90018.photostyle.R;

import com.comp90018.photostyle.helpers.ImageList;
import com.comp90018.photostyle.helpers.UserList;
import com.pixplicity.easyprefs.library.Prefs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.List;




public class CalenderFragment extends Fragment {



    @BindView(R.id.lvCalender)
    ListView lvCalender;


    @BindView(R.id.calendarView)
    CalendarView calendarView;


    ArrayList<String> listItems=new ArrayList<String>();


    ArrayAdapter<String> adapter;

    APIInterface apiInterface;


    private CalenderViewModel calenderViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        calenderViewModel =
                ViewModelProviders.of(this).get(CalenderViewModel.class);

        View root = inflater.inflate(R.layout.fragment_calender, container, false);

        ButterKnife.bind(this, root);
        apiInterface = APIClient.getClient2().create(APIInterface.class);


        List<EventDay> events = new ArrayList<>();

        Calendar min = Calendar.getInstance();
        min.add(Calendar.DAY_OF_MONTH, -2);

        Calendar max = Calendar.getInstance();
        max.add(Calendar.DAY_OF_MONTH, 2);

        calendarView.setMinimumDate(min);
        calendarView.setMaximumDate(max);


        calendarView.setOnDayClickListener(eventDay ->
                Toast.makeText(getActivity().getApplicationContext(),
                        eventDay.getCalendar().getTime().toString() + " "
                                + eventDay.isEnabled(),
                        Toast.LENGTH_SHORT).show());

        adapter=new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1,
                listItems);
        lvCalender.setAdapter(adapter);

        addItems();



        String[] eventOption = {"Business Meeting", "Party"};
        final int[] selectedItem = new int[1];
        Button getDateButton = (Button) root.findViewById(R.id.getDateButton);
        getDateButton.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Choose Event Type");
            final int[] checkedItem = {0};

            builder.setSingleChoiceItems(eventOption, checkedItem[0], new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    selectedItem[0] = which;

                }
            });

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String item = eventOption[selectedItem[0]];
                    System.out.println(item);

                    for (Calendar calendar : calendarView.getSelectedDates()) {
                        System.out.println(calendar.getTime().toString());

                        SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy");
                        String time = s.format(calendar.getTime());
                        String event="";

                        if(item=="Business Meeting")
                            event="Business formals";
                        else if(item=="Party")
                            event = "Party Wear";



                        try {
                            Call<UserList> call2 = apiInterface.setEvent(Prefs.getString("email",""),time,event);
                            call2.enqueue(new Callback<UserList>() {
                                @Override
                                public void onResponse(Call<UserList> call2, Response<UserList> response) {

                                    UserList imageList = response.body();

                                    if(imageList.getSuccess()==1){

                                        listItems.add(item+"\n"+time);
                                        adapter.notifyDataSetChanged();
                                    }
                                    else if(imageList.getSuccess()==0){


                                    }

                                }

                                @Override
                                public void onFailure(Call<UserList> call, Throwable t) {
                                    Log.d("Datum ","error");
                                    call.cancel();


                                }
                            });
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }


                    }

                }
            });
            builder.setNegativeButton("Cancel", null);


            AlertDialog dialog = builder.create();
            dialog.show();

        });






        return root;
    }

    public void addItems() {


        try {
            Call<ImageList> call2 = apiInterface.getEvent(Prefs.getString("email",""));
            call2.enqueue(new Callback<ImageList>() {
                @Override
                public void onResponse(Call<ImageList> call2, Response<ImageList> response) {

                    ImageList imageList = response.body();

                    if(imageList.getSuccess()==1){
                        for(String item:imageList.getSrc()){
                            listItems.add(item);
                            adapter.notifyDataSetChanged();
                        }



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


    }
}


















