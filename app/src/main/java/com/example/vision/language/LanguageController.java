package com.example.vision.language;


import android.app.Activity;
import android.content.res.Configuration;
import android.util.Log;

import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKeys;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Locale;
import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class LanguageController  extends ViewModel {
    public MutableLiveData<String> code = new MutableLiveData<>("en");
    Preferences.Key<String> LANG_KEY = PreferencesKeys.stringKey("lang");



    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public void setLocalCode(String code){
            compositeDisposable.add(languageRepository.setLocalCode(code).subscribe(res->{
                this.languageRepository.languageCode.onNext(Objects.requireNonNull(res.get(LANG_KEY)));
            }, error->{
                Log.println(Log.ASSERT, "vvv", error.getMessage());
            }));
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        languageRepository.dispose();
        compositeDisposable.clear();
    }

    public LanguageController(LanguageRepository languageRepository){
        this.languageRepository = languageRepository;
        Disposable disposable = getLanguageCode().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                code->{
                    Log.println(Log.ASSERT, "Country", code);
                    this.code.postValue(code);
                }
        );
        compositeDisposable.add(disposable);

    }



    public BehaviorSubject<String> getLanguageCode(){
        return languageRepository.getLanguageCode();
    }






    private final LanguageRepository languageRepository;

}
