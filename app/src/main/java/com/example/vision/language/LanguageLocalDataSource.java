package com.example.vision.language;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKeys;
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava3.RxDataStore;

import org.w3c.dom.CDATASection;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class LanguageLocalDataSource {
    private final RxDataStore<Preferences> dataStore;

    void dispose(){
        dataStore.dispose();
    }



    public LanguageLocalDataSource(Activity context){
            dataStore =
                    DataStore.getInstance(context);

    }

    Single<String> getLanguageCode(){
       Preferences.Key<String> LANG_KEY = PreferencesKeys.stringKey("lang");
       return  dataStore.data().map(prefs -> prefs.get(LANG_KEY)).firstOrError();
   }


    Single<Preferences> setLanguageCode(String code){
        Preferences.Key<String> LANG_KEY = PreferencesKeys.stringKey("lang");
        return  dataStore.updateDataAsync(prefsIn -> {
            MutablePreferences mutablePreferences = prefsIn.toMutablePreferences();
            mutablePreferences.set(LANG_KEY, code);
            return Single.just(mutablePreferences);
        });
    }
}
