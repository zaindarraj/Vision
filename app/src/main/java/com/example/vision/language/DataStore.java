package com.example.vision.language;

import android.app.Activity;

import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava3.RxDataStore;

public class DataStore {

    private DataStore(){

    }
    static private  RxDataStore<Preferences> dataStore;

    static RxDataStore<Preferences> getInstance(Activity context){
        if(dataStore ==null){
            dataStore =
                    new RxPreferenceDataStoreBuilder(context, /*name=*/ "settings").build();
        }
        return  dataStore;
    }
}
