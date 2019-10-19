package com.comp90018.photostyle.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {
  //Initialise mtext as string
  private MutableLiveData<String> mText;
    //constructor
    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }
    
    public LiveData<String> getText() {
        return mText;
    }


}
