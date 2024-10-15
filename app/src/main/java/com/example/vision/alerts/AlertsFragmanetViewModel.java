package com.example.vision.alerts;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.vision.alerts.repository.RemoteRepository;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AlertsFragmanetViewModel extends ViewModel {

    MutableLiveData<ArrayList<Alert>> alertsMutableLiveData = new MutableLiveData<>();

    RemoteRepository remoteRepository = new RemoteRepository();

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    public MutableLiveData<ArrayList<Alert>> getAlertsMutableLiveData() {
        return alertsMutableLiveData;
    }


    void getAlerts(){
           Disposable disposable =  remoteRepository.getAlerts().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                   alertsResult -> {
                       if(alertsResult.response() != null && alertsResult.response().isSuccessful() && alertsResult.response().body() != null){
                           alertsMutableLiveData.postValue(alertsResult.response().body().alerts);
                       }else{
                           alertsMutableLiveData.postValue(new ArrayList<>());
                       }
                   }
           );
           compositeDisposable.add(disposable);
    }
}