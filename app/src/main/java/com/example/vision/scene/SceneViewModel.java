package com.example.vision.scene;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SceneViewModel extends ViewModel {
    MutableLiveData<String> alert = new MutableLiveData<>("No Alerts Yet");

    public MutableLiveData<String> getAlert() {
        return alert;
    }
}
