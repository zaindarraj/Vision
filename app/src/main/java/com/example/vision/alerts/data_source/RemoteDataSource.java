package com.example.vision.alerts.data_source;

import com.example.vision.alerts.Alert;
import com.example.vision.alerts.Alerts;
import com.example.vision.retrofit.API;
import com.example.vision.retrofit.RetrofitInstance;

import io.reactivex.rxjava3.core.Single;
import retrofit2.adapter.rxjava3.Result;

public class RemoteDataSource {
    final private API api = RetrofitInstance.getInstance();



    public Single<Result<Alerts>> getAlerts(){
       return api.alerts();
    }


}
