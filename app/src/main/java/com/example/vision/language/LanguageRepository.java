package com.example.vision.language;

import android.util.Log;

import androidx.datastore.preferences.core.Preferences;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class LanguageRepository {
    BehaviorSubject<String> languageCode = BehaviorSubject.create();

    CompositeDisposable compositeDisposable = new CompositeDisposable();



    public LanguageRepository(LanguageLocalDataSource languageLocalDataSource){
        this.languageLocalDataSource = languageLocalDataSource;
        Disposable disposable = languageLocalDataSource.getLanguageCode().subscribe(code->{
            languageCode.onNext(code);
        }, error->{
        });
        compositeDisposable.add(disposable);
    }

    public BehaviorSubject<String> getLanguageCode() {
        return languageCode;
    }



    Single<Preferences> setLocalCode(String code){
       return languageLocalDataSource.setLanguageCode(code);
    }



   private final LanguageLocalDataSource languageLocalDataSource;

    void dispose(){
        compositeDisposable.clear();
        languageLocalDataSource.dispose();
    }
}
