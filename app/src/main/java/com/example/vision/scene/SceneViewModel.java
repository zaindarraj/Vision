package com.example.vision.scene;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.File;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SceneViewModel extends ViewModel {
    MutableLiveData<String> alert = new MutableLiveData<>("No Alerts Yet");
    CompositeDisposable compositeDisposable  = new CompositeDisposable();

    public MutableLiveData<String> getAlert() {
        return alert;
    }



    void predict(File file){
        Disposable disposable =  remoteRepository.getPrediction(file).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                    (listResult, throwable) -> {
                            if (listResult.response() != null && listResult.response().isSuccessful()) {
                                //Good->Handle Data
                                DetectedObjects objects = listResult.response().body();
                                Log.println(Log.ASSERT, "I am in Scene ViewModel, repo error :", objects.detectedObjects.toString());

                                if(objects.detectedObjects.isEmpty()){
                                    alert.setValue("No Alerts Yet");
                                }else{
                                    String localAlert = "There are ";
                                   for (final String object : objects.detectedObjects) {
                                            localAlert = localAlert + object + ",";
                                   }
                                    alert.setValue(localAlert);
                                }
                            }else{
                                alert.setValue("No Alerts Yet");

                            }
                    }
            );
        compositeDisposable.add(disposable);

    }

    private  final RemoteRepository remoteRepository = new RemoteRepository();

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
