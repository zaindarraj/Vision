package com.example.vision.alerts.repository;

import com.example.vision.alerts.Alerts;
import com.example.vision.alerts.data_source.RemoteDataSource;

import io.reactivex.rxjava3.core.Single;
import retrofit2.adapter.rxjava3.Result;

public class RemoteRepository {


    final private RemoteDataSource remoteDataSource =new RemoteDataSource();


    public Single<Result<Alerts>> getAlerts(){
        return remoteDataSource.getAlerts();
    }

}
