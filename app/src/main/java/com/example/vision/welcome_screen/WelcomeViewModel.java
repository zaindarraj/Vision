package com.example.vision.welcome_screen;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WelcomeViewModel extends ViewModel {
    public String[] languages = {"English", "العربية"}; // Data source
    MutableLiveData<Integer> chosenLocal =new MutableLiveData<>(0) ; //default
    boolean firstEvent = true;


}